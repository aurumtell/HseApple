package com.hseapple.service;

import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.PostDao;
import com.hseapple.dao.PostEntity;
import com.hseapple.dao.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    public PostService(PostDao postDao){
        this.postDao = postDao;
    }
    public List<PostEntity> findAllPosts(Long courseID, Long start) {
        return postDao.findAllByCourseIDAndIdGreaterThanEqual(courseID, start);
//        Pageable page = PageRequest.of((start - 1)/limit, limit);
//        return postDao.findAllByCourseid(courseID, page);
    }

    public PostEntity createPost(PostEntity postEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity newpost = new PostEntity();
        newpost.setCourseID(postEntity.getCourseID());
        newpost.setCreatedBy(user.getId());
        newpost.setCreatedAt(LocalDateTime.now());
        newpost.setDocLink(postEntity.getDocLink());
        newpost.setContent(postEntity.getContent());
        newpost.setMediaLink(postEntity.getMediaLink());
        newpost.setTitle(postEntity.getTitle());
        System.out.println(postEntity);
//        postEntity.setUpdatedAt(null);
//        Long id = (long) (postDao.findAll().size() + 1);
//        postEntity.setId(id);
        return postDao.save(newpost);
    }

    public ResponseEntity<?> updatePost(PostEntity newPost, Long postID) {
        Optional<PostEntity> post = postDao.findById(postID);
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (post.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        post.ifPresent(p -> {
            p.setTitle(newPost.getTitle());
            p.setContent(newPost.getContent());
            p.setMediaLink(newPost.getMediaLink());
            p.setDocLink(newPost.getDocLink());
            p.setUpdatedAt(LocalDateTime.now());
            p.setUpdatedBy(user.getId());
            postDao.save(p);
        });
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    public ResponseEntity<?> deletePost(Long postID) {
        Optional<PostEntity> post = postDao.findById(postID);
        if (post.isEmpty()){
            return ResponseEntity.badRequest().body("post not found");
        }
        postDao.deleteTaskById(postID);
        return ResponseEntity.ok("Post deleted");
    }

}