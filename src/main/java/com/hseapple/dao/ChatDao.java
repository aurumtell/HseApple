package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatDao extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findById(Long courseID);

    @Transactional
    void deleteGroupById(Long id);

    List<ChatEntity> findAllByCourseID(Long courseID);
}