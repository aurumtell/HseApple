package com.hseapple.dao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "chat", schema = "public")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

//    public void setUpdatedBy(Long updatedBy) {
//        this.updatedBy = updatedBy;
//    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "courseid")
    private Long courseID;

//    // убрать
//    @Column(name = "updatedby")
//    private Long updatedBy;

    // убрать ?
    @Column(name = "group_avatar")
    private String group_avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Timestamp createdAt;

    public Long getCourseID() {
        return courseID;
    }

//    public Long getUpdatedBy() {
//        return updatedBy;
//    }

    public String getGroup_avatar() {
        return group_avatar;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
