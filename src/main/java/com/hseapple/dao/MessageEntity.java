package com.hseapple.dao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message", schema = "public")
public class MessageEntity {
    public void setId(Long id) {
        this.id = id;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setReplyTo(Long replyTo) {
        this.replyTo = replyTo;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMedia_link(String media_link) {
        this.media_link = media_link;
    }

    public void setDoc_link(String doc_link) {
        this.doc_link = doc_link;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chatid")
    private Long chatID;

    @Column(name = "userid")
    private Long userID;

    @Column(name = "replyto")
    private Long replyTo;

    @NotNull
    @Column(name = "message")
    private String message;

    @Column(name = "media_link")
    private String media_link;

    @Column(name = "doc_link")
    private String doc_link;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public Long getChatID() {
        return chatID;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    public String getMessage() {
        return message;
    }

    public String getMediaLink() {
        return media_link;
    }

    public String getDocLink() {
        return doc_link;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    //getters and setters
}
