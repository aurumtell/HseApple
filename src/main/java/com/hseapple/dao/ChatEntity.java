package com.hseapple.dao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

//    public void setUpdatedBy(Long updatedBy) {
//        this.updatedBy = updatedBy;
//    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "courseid")
    private Integer courseID;

    // убрать ?
    @Column(name = "group_avatar")
    private String group_avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Integer getCourseID() {
        return courseID;
    }

    public String getGroup_avatar() {
        return group_avatar;
    }

    public LocalDateTime getCreatedAt() {
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
