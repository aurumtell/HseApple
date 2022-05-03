package com.hseapple.controller;

import com.hseapple.dao.*;
import com.hseapple.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@Tag(description = "Api to manage chats",
        name = "Chat Resource")
@SecurityRequirement(name = "Authorization")
public class ChatController {

    @Autowired
    ChatService chatService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Operation(summary = "Get group for course",
            description = "Provides group for course")
    @RequestMapping(value = "/group/{groupID}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<ChatEntity> getGroupForCourse(@PathVariable("groupID") Long groupID){
        return chatService.getGroupForCourse(groupID);
    }

    @Operation(summary = "Delete group for course",
            description = "Delete the desired group for course. Access role - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "/group/{groupID}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupID) {
        return chatService.deleteGroup(groupID);
    }

    @Operation(summary = "Get groups for course",
            description = "Provides all available groups list for course")
    @RequestMapping(value = "/course/{courseID}/group", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<ChatEntity> getGroups(@PathVariable Long courseID){
        return chatService.findAllGroups(courseID);
    }

    @Operation(summary = "Get message for chat",
            description = "Provides message for chat")
    @RequestMapping(value = "/group/{groupID}/message/{messageID}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<MessageEntity> getMessageForChat(@PathVariable("groupID") Long groupID,
                                                     @PathVariable("messageID") Long messageID){
        return chatService.getMessageForChat(groupID, messageID);
    }

    @Operation(summary = "Delete message",
            description = "Delete message for chat")
    @DeleteMapping(value = "/group/{groupID}/message/{messageID}")
    public void deleteMessage(@PathVariable Long groupID, @PathVariable Long messageID) {
        chatService.deleteMessage(groupID, messageID);
    }

}