package com.hseapple.controller;

import com.hseapple.dao.*;
import com.hseapple.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import liquibase.pro.packaged.P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

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
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public ChatEntity getGroupForCourse(@PathVariable("groupID") Long groupID){
        return chatService.getGroupForCourse(groupID);
    }

    @Operation(summary = "Delete group for course",
            description = "Delete the desired group for course. Access role - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "/group/{groupID}")
    public void deleteGroup(@PathVariable Long groupID) {
        chatService.deleteGroup(groupID);
    }

    @Operation(summary = "Get groups for course",
            description = "Provides all available groups list for course")
    @RequestMapping(value = "/course/{courseID}/group", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public Iterable<ChatEntity> getGroups(@PathVariable Integer courseID){
        return chatService.findAllGroups(courseID);
    }

    @Operation(summary = "Get message for chat",
            description = "Provides message for chat")
    @RequestMapping(value = "/group/{groupID}/message/{messageID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public MessageEntity getMessageForChat(@PathVariable("groupID") Long groupID,
                                                     @PathVariable("messageID") Long messageID){
        return chatService.getMessageForChat(groupID, messageID);
    }

    @Operation(summary = "Delete message",
            description = "Delete message for chat")
    @DeleteMapping(value = "/group/{groupID}/message/{messageID}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public void deleteMessage(@PathVariable Long groupID, @PathVariable Long messageID) {
        chatService.deleteMessage(groupID, messageID);
    }

    @Operation(summary = "Get messages for chat",
            description = "Provides all available messages for chat")
    @RequestMapping(value = "/group/{groupID}/message", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public Iterable<MessageEntity> getMessages(@PathVariable Long groupID, @RequestParam("start") Long start){
        return chatService.findMessages(groupID, start);
    }

    @Operation(summary = "Create message",
            description = "Creates new message")
    @RequestMapping(value = "/group/message", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public MessageEntity createMessage(@Valid @RequestBody MessageEntity messageEntity) {
        return chatService.createMessage(messageEntity);
    }

    @Operation(summary = "Delete member from Chat",
            description = "Delete member from chat")
    @DeleteMapping(value = "/group/{groupID}/member/{userID}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public void deleteMember(@PathVariable Long groupID, @PathVariable Long userID) {
        chatService.deleteMember(groupID, userID);
    }

    @Operation(summary = "Get chat members",
            description = "Provides all available chat members, that match id of chat")
    @RequestMapping(value = "/group/{groupID}/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public Iterable<UserEntity> getListMembers(@PathVariable Long groupID){
        return chatService.getMembers(groupID);
    }

    @Operation(summary = "Get chat member",
            description = "Provide chat member, that match id of chat and user")
    @RequestMapping(value = "/group/{groupID}/member/{userID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public UserEntity getMember(@PathVariable Long groupID, @PathVariable Long userID){
        return chatService.getMember(groupID, userID);
    }
}