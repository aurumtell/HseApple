package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageDao extends JpaRepository<MessageEntity, Long> {
    Optional<MessageEntity> findByChatIDAndId(Long groupID, Long messageID);

    Iterable<MessageEntity> findAllByChatIDAndIdGreaterThanEqual(Long groupID, Long start);
}