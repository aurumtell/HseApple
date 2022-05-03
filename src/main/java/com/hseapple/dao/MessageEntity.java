package com.hseapple.dao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_message", schema = "public")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatid")
    private Long chatID;

    @Column(name = "userid")
    private Long userID;

    @Column(name = "replyto")
    private Long replyTo;

    @Column(name = "message")
    private String message;

    @Column(name = "media_link")
    private String media_link;

    @Column(name = "doc_link")
    private String doc_link;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Timestamp createdAt;

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Timestamp updatedAt;



    //getters and setters
}
