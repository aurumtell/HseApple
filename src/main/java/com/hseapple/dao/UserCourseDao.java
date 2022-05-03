package com.hseapple.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseDao extends JpaRepository<UserCourseEntity, Long> {
    Optional<UserCourseEntity> findByUserIDAndCourseID(Long userID, Long courseID);
}