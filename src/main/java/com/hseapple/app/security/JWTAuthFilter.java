package com.hseapple.app.security;

import com.hseapple.dao.UserDao;
import com.hseapple.dao.UserEntity;
import com.hseapple.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT filter is working");
        String accessToken = request.getHeader("Authorization");
        System.out.println(accessToken);
        if (Strings.isEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            /*
                String publicKey = "somekey";
                  Algorithm algorithm = Algorithm.RSA256(publicKey, null); // only the public key is used during verification
                  JWTVerifier verifier = JWT.require(algorithm)
                      .withIssuer("auth0")
                      .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(accessToken);
*/
            DecodedJWT jwt = JWT.decode(accessToken);
            String commonname = jwt.getClaims().get("commonname").asString();
            String email = jwt.getClaims().get("email").asString();
            UserAndRole userAndRole = new UserAndRole(commonname, email);
            UserEntity user = userDao.findByEmail(email);
            if (user == null) {
                UserEntity newUser = userService.createUser(commonname, email);
                userAndRole.setId(newUser.getId());
            } else {
                userAndRole.setId(user.getId());
            }
            List<String> roles = userDao.findAllRoleById(userAndRole.getId());
            List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userAndRole, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JWTDecodeException exception) {
            filterChain.doFilter(request, response);
        }
    }
}
