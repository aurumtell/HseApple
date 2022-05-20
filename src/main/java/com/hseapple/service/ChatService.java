package com.hseapple.service;

import com.hseapple.app.error.ExceptionMessage;
import com.hseapple.app.error.exception.BusinessException;
import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatDao chatDao;

    @Autowired
    ChatMemberDao chatMemberDao;

    @Autowired
    MessageDao messageDao;

    public ChatService(ChatDao groupDao){
        this.chatDao = groupDao;
    }

    public ChatEntity getGroupForCourse(Long groupID) {
        return chatDao.findById(groupID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
    }

    public void deleteGroup(Long groupID) {
        chatDao.findById(groupID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        chatDao.deleteGroupById(groupID);
    }

    public List<ChatEntity> findAllGroups(Integer courseID) { return chatDao.findAllByCourseID(courseID); }

    public MessageEntity getMessageForChat(Long groupID, Long messageID) {
        return messageDao.findByChatIDAndId(groupID, messageID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
    }

    public void deleteMessage(Long groupID, Long messageID) {
        messageDao.findByChatIDAndId(groupID, messageID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        messageDao.deleteById(messageID);
    }

    public void addMember(Long userID, Integer courseID) {
        long chatid, chatid_end;
        if (courseID == 1) {
            chatid = 1L;
            chatid_end = 4L;
        } else {
            chatid = 5L;
            chatid_end = 8L;
        }
        for (long i = chatid; i <= chatid_end; i++) {
            ChatMemberEntity chatMember = new ChatMemberEntity();
            chatMember.setChatID(i);
            chatMember.setCreatedAt(LocalDateTime.now());
            chatMember.setUserID(userID);
            chatMemberDao.save(chatMember);
        }
    }

    public Iterable<MessageEntity> findMessages(Long groupID, Long start) {
        return messageDao.findAllByChatIDAndIdGreaterThanEqual(groupID, start);
    }

    public MessageEntity createMessage(MessageEntity messageEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageEntity.setCreatedAt(LocalDateTime.now());
        messageEntity.setUserID(user.getId());
        return messageDao.save(messageEntity);
    }

    public void deleteMember(Long groupID, Long userID) {
        chatMemberDao.findByChatIDAndUserID(groupID, userID)
                .orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        chatMemberDao.deleteByChatIDAndUserID(groupID, userID);
    }

    public Iterable<UserEntity> getMembers(Long groupID) {
        return chatMemberDao.findAllMembers(groupID);
    }

    public UserEntity getMember(Long groupID, Long userID) {
        return chatMemberDao.findMember(groupID, userID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
    }
}