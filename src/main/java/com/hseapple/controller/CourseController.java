package com.hseapple.controller;

import com.hseapple.dao.CourseEntity;
import com.hseapple.dao.RequestEntity;
import com.hseapple.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "Authorization")
@Tag(description = "Api to manage courses",
        name = "Course Resource")
public class CourseController {

    @Autowired
    CourseService courseService;

    Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Operation(summary = "Get courses",
            description = "Provides all available courses. Access roles - TEACHER, ASSIST, STUDENT")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<CourseEntity> getCourses(){
        return courseService.findAllCourse();
    }

    @Operation(summary = "Get course requests",
            description = "Provides all available course requests. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/course/{courseID}/application/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<RequestEntity> getListRequests(@PathVariable(name = "courseID") Long courseID, @RequestParam("approved") Boolean approved){
        return courseService.findAllRequests(courseID, approved);
    }


}