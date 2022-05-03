package com.hseapple.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<TaskEntity, Long> {
    TaskEntity findByIdAndCourseID(Long taskID, Long courseID);

    TaskEntity getTaskById(Long taskID);

    @Transactional
    void deleteTaskById(Long id);

    List<TaskEntity> findAllByCourseIDAndStatusAndFormAndCreatedBy(Long courseID, Boolean status, String form, Long createdBy);

    List<TaskEntity> findAllByCourseIDAndIdGreaterThanEqual(Long courseID, Long start);
}