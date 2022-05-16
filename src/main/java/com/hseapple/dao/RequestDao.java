package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestDao extends JpaRepository<RequestEntity, Long> {

    Optional<RequestEntity> findByUserIDAndCourseID(Long userID, Integer courseID);

    Iterable<RequestEntity> findAllByCourseIDAndApproved(Integer courseID, Boolean approved);
}