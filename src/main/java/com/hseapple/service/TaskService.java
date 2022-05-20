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
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserTaskDao userTaskDao;

    public TaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public void deleteTask(Long taskID) {
        taskDao.findById(taskID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        taskDao.deleteTaskById(taskID);
    }

    public List<TaskEntity> findTasks(Integer courseID, Long start) {
        return taskDao.findAllByCourseIDAndIdGreaterThanEqual(courseID, start);
    }

    public TaskEntity getTaskForCourse(Long taskID) {
        return taskDao.findById(taskID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
    }

    public TaskEntity createTask(TaskEntity taskEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setCreatedBy(user.getId());
        return taskDao.save(taskEntity);
    }

    public Iterable<UserTaskEntity> findAnswerTasks(Long taskID, Boolean state_answer, String form) {
        return userTaskDao.getListOfAnswers(taskID, state_answer, form);
    }

    public UserTaskEntity createAnswer(Long taskID, UserTaskEntity userTaskEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userTaskEntity.setUserID(user.getId());
        userTaskEntity.setTaskID(taskID);
        userTaskEntity.setCreatedAt(LocalDateTime.now());
        userTaskEntity.setCreatedBy(user.getId());
        userTaskEntity.setStatus(true);
        return userTaskDao.save(userTaskEntity);
    }

    public TaskEntity updateTask(TaskEntity newTask, Long taskID) {
        TaskEntity task = taskDao.findById(taskID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setCourseID(newTask.getCourseID());
        task.setForm(newTask.getForm());
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setTask_content(newTask.getTask_content());
        task.setDeadline(newTask.getDeadline());
        task.setStatus(newTask.isStatus());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(user.getId());
        taskDao.save(task);
        return task;
    }

    public UserTaskEntity updateUserTask(UserTaskEntity newUserTask) {
        UserTaskEntity userTask = userTaskDao.findByTaskIDAndUserID(newUserTask.getTaskID(), newUserTask.getUserID())
                .orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userTask.setScore(newUserTask.getScore());
        userTask.setUpdatedBy(user.getId());
        userTask.setUpdatedAt(LocalDateTime.now());
        return userTask;
    }
}