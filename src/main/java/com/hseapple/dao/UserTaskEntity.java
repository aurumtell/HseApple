package com.hseapple.dao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_task", schema = "public")
public class UserTaskEntity {
    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getScore() {
        return score;
    }

    public Boolean getStatus() {
        return status;
    }

    public Long getTaskID() {
        return taskID;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
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

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "userid")
    private Long userID;

    @Column(name = "answer")
    private String answer;

    @Column(name = "score")
    private Integer score;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    @NotNull
    @Column(name = "taskid")
    private Long taskID;

    @NotNull
    @Column(name = "createdby")
    private Long createdBy;

    @Column(name = "updatedby")
    private Long updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
