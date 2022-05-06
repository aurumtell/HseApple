package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemberDao extends JpaRepository<ChatMemberEntity, Long> {

    Optional<ChatMemberEntity> findByChatIDAndUserID(Long chatID, Long UserID);

    void deleteByChatIDAndUserID(Long groupID, Long userID);

    @Query("SELECT u FROM ChatMemberEntity AS c " +
            "JOIN UserEntity AS u ON c.userID = u.id " +
            "WHERE c.chatID=?1")
    Iterable<UserEntity> findAllMembers(Long groupID);

    @Query("SELECT u FROM ChatMemberEntity AS c " +
            "JOIN UserEntity AS u ON c.userID = u.id " +
            "WHERE c.chatID=?1 AND c.userID=?2")
    Optional<UserEntity> findMember(Long groupID, Long userID);
}