package com.hseapple.service;

import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    ChatService chatService;

    @Autowired
    UserDao userDao;

    @Autowired
    RequestDao requestDao;

    @Autowired
    UserCourseDao userCourseDao;

    @Autowired
    ProfileDao profileDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }
    public Optional<UserEntity> findUser(Long userID) {
        return userDao.findById(userID);
    }

    public ResponseEntity<?> changeUser(Long userID, UserEntity newUser) {
        Optional<UserEntity> user = userDao.findById(userID);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().body("user not found");
        }
        user.ifPresent(u -> {
            u.setCommonname(newUser.getCommonname());
            u.setEmail(newUser.getEmail());
            userDao.save(u);
        });
        return ResponseEntity.ok("User updated");
    }

    public UserEntity createUser(String commonname, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setCommonname(commonname);
        userEntity.setEmail(email);
        userEntity.setCreatedAt(LocalDateTime.now());
        userDao.save(userEntity);
        return userEntity;
    }

    public List<UserEntity> getUsers(Long courseID, Long roleID, Boolean approved) {
        return userDao.getListOfUsers(courseID, roleID, approved);
    }

    public Optional<RequestEntity> getUserRequest(Long userID, Long courseID) {
        return requestDao.findByUserIDAndCourseID(userID, courseID);
    }

    public ResponseEntity<?> updateUserRequest(Long courseID, Long userID, RequestEntity newRequestEntity) {
        Optional<RequestEntity> request = requestDao.findByUserIDAndCourseID(userID, courseID);
        System.out.println(request);
        Optional<UserCourseEntity> userCourse = userCourseDao.findByUserIDAndCourseID(userID, courseID);
        System.out.println("request userCourse");
        if (request.isEmpty()){
            return ResponseEntity.badRequest().body("request not found");
        }
        request.ifPresent(r -> {
            r.setApproved(newRequestEntity.getApproved());
            System.out.println(newRequestEntity.getApproved());
            if (newRequestEntity.getApproved()) {
                if (userCourse.isEmpty()){
                    createUserCourse(courseID, userID, r.getRoleID());
                } else {
                    userCourse.ifPresent(u -> u.setRoleID(r.getRoleID()));
                }
                if (r.getRoleID() == 1) {
                    chatService.addMember(userID, courseID);
                }
            }
            r.setUpdatedAt(LocalDateTime.now());
            requestDao.save(r);
        });
        return ResponseEntity.ok("Request updated");
    }

    public Map<String, Object> getProfile(){
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileEntity profile = userDao.getProfile(user.getId());
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("userID", user.getId());
        json.put("avatar", profile.getAvatar());
        json.put("commonname", user.getUsername());
        json.put("email", user.getEmail());
        return json;
    }

    public ResponseEntity<RequestEntity> createRequest(Long courseID, Long roleID) {
        if (roleID > 2) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (requestDao.findByUserIDAndCourseID(user.getId(), courseID).isEmpty()) {
            RequestEntity request = new RequestEntity();
            request.setCourseID(courseID);
            request.setUserID(user.getId());
            request.setRoleID(roleID);
            request.setApproved(false);
            request.setCreatedAt(LocalDateTime.now());
            requestDao.save(request);
            createProfile(user.getId());
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    private void createProfile(Long id) {
        ProfileEntity profile = new ProfileEntity();
        profile.setUserID(id);
        profile.setCreatedAt(LocalDateTime.now());
        profileDao.save(profile);
    }

    public void createUserCourse(Long courseID, Long userID, Long roleID) {
        UserCourseEntity userCourse = new UserCourseEntity();
        userCourse.setUserID(userID);
        userCourse.setCourseID(courseID);
        userCourse.setRoleID(roleID);
        userCourseDao.save(userCourse);
    }

    public void updateUserCourse(Long courseID, Long userID, Long roleID) {
        Optional<UserCourseEntity> userCourse = userCourseDao.findByUserIDAndCourseID(userID, courseID);
        userCourse.ifPresent(u ->{
            u.setRoleID(roleID);
            userCourseDao.save(u);
        });

    }

    public Optional<UserEntity> RegisterUser() {
        return userDao.findById(((UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    public ProfileEntity updateProfile(ProfileEntity profileEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileEntity profile = profileDao.getByUserID(user.getId());
        profile.setAvatar(profileEntity.getAvatar());
        profileDao.save(profile);
        return profile;
    }
}
