package com.hseapple.service;

import com.hseapple.app.error.ExceptionMessage;
import com.hseapple.app.error.exception.BusinessException;
import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.PostDao;
import com.hseapple.dao.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    public PostService(PostDao postDao){
        this.postDao = postDao;
    }
    public List<PostEntity> findAllPosts(Integer courseID, Long start) {
        return postDao.findAllByCourseIDAndIdGreaterThanEqual(courseID, start);
    }

    public PostEntity createPost(PostEntity postEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postEntity.setCreatedBy(user.getId());
        postEntity.setCreatedAt(LocalDateTime.now());
        return postDao.save(postEntity);
    }

    public PostEntity updatePost(PostEntity newPost, Long postID) {
        PostEntity post = postDao.findById(postID).orElseThrow(() -> new BusinessException(ExceptionMessage.object_not_found));
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setMediaLink(newPost.getMediaLink());
        post.setDocLink(newPost.getDocLink());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUpdatedBy(user.getId());
        postDao.save(post);
        return post;
    }

    public void deletePost(Long postID) {
        postDao.findById(postID).orElseThrow(() -> new BusinessException(ExceptionMessage.object_already_deleted));
        postDao.deleteTaskById(postID);
    }

}