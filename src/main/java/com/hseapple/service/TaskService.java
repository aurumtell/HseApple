package com.hseapple.service;

import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserTaskDao userTaskDao;

    public TaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public ResponseEntity<?> deleteTask(Long taskID) {
        Optional<TaskEntity> task = taskDao.findById(taskID);
        if (task.isEmpty()){
            return ResponseEntity.badRequest().body("task not found");
        }
        task.ifPresent(t -> {
            taskDao.deleteTaskById(taskID);
        });
        return ResponseEntity.ok("Task deleted");
    }

    public List<TaskEntity> findTasks(Long courseID, Long start) {
        return taskDao.findAllByCourseIDAndIdGreaterThanEqual(courseID, start);
    }

    public Optional<TaskEntity> getTaskForCourse(Long taskID) {
        return taskDao.findById(taskID);
    }

    public TaskEntity createTask(Long courseID, TaskEntity taskEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskEntity.setCourseID(courseID);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setCreatedBy(user.getId());
        return taskDao.save(taskEntity);
    }

    public Iterable<UserTaskEntity> findAnswerTasks(Long taskID, Boolean state_answer, String form) {
        return userTaskDao.getListOfAnswers(taskID, state_answer, form);
    }

    public UserTaskEntity createAnswer(Long taskID, UserTaskEntity userTaskEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserTaskEntity newUserTask = userTaskDao.findByTaskIDAndUserID(taskID, user.getId());
        newUserTask.setTaskID(taskID);
        newUserTask.setCreatedAt(LocalDateTime.now());
        newUserTask.setCreatedBy(user.getId());
        newUserTask.setAnswer(userTaskEntity.getAnswer());
        newUserTask.setStatus(true);
        return userTaskDao.save(userTaskEntity);
    }

    public ResponseEntity<?> updateTask(TaskEntity newTask, Long taskID) {
        Optional<TaskEntity> task = taskDao.findById(taskID);
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (task.isEmpty()){
            return ResponseEntity.badRequest().body("task not found");
        }
        task.ifPresent(t -> {
            t.setCourseID(newTask.getCourseID());
            t.setForm(newTask.getForm());
            t.setTitle(newTask.getTitle());
            t.setDescription(newTask.getDescription());
            t.setTask_content(newTask.getTask_content());
            t.setDeadline(newTask.getDeadline());
            t.setStatus(newTask.isStatus());
            t.setUpdatedAt(LocalDateTime.now());
            t.setUpdatedBy(user.getId());
            taskDao.save(t);
        });
        return ResponseEntity.ok("Task updated");
    }
}