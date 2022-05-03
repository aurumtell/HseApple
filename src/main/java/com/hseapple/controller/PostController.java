package com.hseapple.controller;

import com.hseapple.dao.PostEntity;
import com.hseapple.dao.TaskEntity;
import com.hseapple.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@SecurityRequirement(name = "Authorization")
@Tag(description = "Api to manage posts",
        name = "Post Resource")
public class PostController {

    @Autowired
    PostService postService;

    Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Operation(summary = "Get posts for course",
            description = "Provides all available posts for course. Access roles - TEACHER, ASSIST, STUDENT")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "/course/{courseID}/post", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<PostEntity> getPosts(@PathVariable("courseID") Long courseID, @RequestParam("start") Long start){
        return postService.findAllPosts(courseID, start);
    }

    @Operation(summary = "Update post",
            description = "Provides new updated post. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/course/post/{postID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updatePost(@RequestBody PostEntity newPost, @PathVariable Long postID){
        return postService.updatePost(newPost, postID);
    }

    @Operation(summary = "Create post",
            description = "Creates new post. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/course/post", method = RequestMethod.POST)
    @ResponseBody
    public PostEntity createPost(@Valid @RequestBody PostEntity postEntity) {
        return postService.createPost(postEntity);

    }

    @Operation(summary = "Delete post",
            description = "Delete post. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "course/post/{postID}")
    public ResponseEntity<?> deletePost(@PathVariable Long postID) {
        return postService.deletePost(postID);
    }

}