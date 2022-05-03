package com.hseapple.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<PostEntity, Long> {
//    List<PostEntity> findAll(Long courseID, PageRequest page);
    List<PostEntity> findAllByCourseID(Long courseid, Pageable pageable);

    List<PostEntity> findAllByCourseIDAndIdGreaterThanEqual(Long courseID, Long start);

    void deleteTaskById(Long postID);
}