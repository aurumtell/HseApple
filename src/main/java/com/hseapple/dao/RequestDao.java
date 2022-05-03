package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestDao extends JpaRepository<RequestEntity, Long> {

    Iterable<RequestEntity> findAllByCourseID(Long courseID);

    Optional<RequestEntity> findByUserIDAndCourseID(Long userID, Long courseID);

    Optional<RequestEntity> findAllByUserIDAndCourseID(Long userID, Long courseID);

    Iterable<RequestEntity> findAllByCourseIDAndApproved(Long courseID, Boolean approved);
}