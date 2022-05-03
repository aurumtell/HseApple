package com.hseapple.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hseapple.dao.*;
import com.hseapple.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;


@RestController
@SecurityRequirement(name = "Authorization")
@Tag(description = "Api to manage users",
        name = "User Resource")
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "Get user for course",
            description = "Provides user for course by id")
    @RequestMapping(value = "user/{userID}/application/approved", method = RequestMethod.GET)
    @ResponseBody
    public Optional<UserEntity> getUser(@PathVariable("userID") Long userID){
        return userService.findUser(userID);
    }


    @Operation(summary = "Update user",
            description = "Provides new updated user.Access role - TEACHER, STUDENT, ASSIST")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping (value = "/user/{userID}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody UserEntity newUser, @PathVariable("userID") Long userID) {
        return userService.changeUser(userID, newUser);
    }

    @Operation(summary = "Get users for course",
            description = "Provides all available users, that match the parameters")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<UserEntity> getListUsers(Long courseID, Long roleID, Boolean approved){
        return userService.getUsers(courseID, roleID, approved);
    }

    @Operation(summary = "Get request for course",
            description = "Provides user request for course")
    @RequestMapping(value = "/request/{courseID}/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<RequestEntity> getRequest(@PathVariable("userID") Long userID,
                                              @PathVariable("courseID") Long courseID){
        return userService.getUserRequest(courseID, userID);
    }

    @Operation(summary = "Update request for course",
            description = "Provides new updated user request.Access role - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/request/{courseID}/{userID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateRequest(@RequestBody RequestEntity requestEntity, @PathVariable("userID") Long userID,
                                              @PathVariable("courseID") Long courseID){
        return userService.updateUserRequest(courseID, userID, requestEntity);
    }

    @Operation(summary = "Get profile",
            description = "Provides user profile.Access role - TEACHER, STUDENT, ASSIST")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProfile() throws JsonProcessingException {
        return userService.getProfile();
    }

    @Operation(summary = "Update profile",
            description = "Provides new updated user profile. Access role - TEACHER, STUDENT, ASSIST")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    @ResponseBody
    public ProfileEntity updateProfile(@RequestBody ProfileEntity profileEntity){
        return userService.updateProfile(profileEntity);
    }

    @Operation(summary = "Create request",
            description = "Provides new created request. Access role - STUDENT, ASSIST")
    @RequestMapping(value = "/user/request/{courseID}/{roleID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<RequestEntity> createRequest(@PathVariable("courseID") Long courseID, @PathVariable("roleID") Long roleID){
        return userService.createRequest(courseID, roleID);
    }

    @Operation(summary = "Authorization",
            description = "Provides user entity")
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseBody
    public Optional<UserEntity> auth(){
        return userService.RegisterUser();
    }


}
