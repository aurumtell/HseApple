package com.hseapple.service;

import com.hseapple.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<ChatEntity> getGroupForCourse(Long groupID) {
        return chatDao.findById(groupID);
    }

    public ResponseEntity<?> deleteGroup(Long groupID) {
        Optional<ChatEntity> chat = chatDao.findById(groupID);
        if (chat.isEmpty()){
            return ResponseEntity.badRequest().body("chat not found");
        }
        chat.ifPresent(u -> {
            chatDao.deleteGroupById(groupID);
        });
        return ResponseEntity.ok("Chat deleted");
    }

    public List<ChatEntity> findAllGroups(Long courseID) { return chatDao.findAllByCourseID(courseID); }

    public ChatEntity createGroup(ChatEntity groupEntity) {
        groupEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return chatDao.save(groupEntity);
    }

    public Optional<MessageEntity> getMessageForChat(Long groupID, Long messageID) {
        return messageDao.findById(messageID);
    }

    public void deleteMessage(Long groupID, Long messageID) {
    }

    public void addMember(Long userID, Long courseID) {
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
}