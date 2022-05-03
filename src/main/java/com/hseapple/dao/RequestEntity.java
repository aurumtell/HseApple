package com.hseapple.dao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_request_course", schema = "public")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getUserID() {
        return userID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public Long getRoleID() {
        return roleID;
    }

    public Boolean getApproved() {
        return approved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @NotNull
    @Column(name = "userid")
    private Long userID;

    @NotNull
    @Column(name = "courseid")
    private Long courseID;

    @NotNull
    @Column(name = "roleid")
    private Long roleID;

    @NotNull
    @Column(name = "approved")
    private Boolean approved;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }


    //getters and setters
}
