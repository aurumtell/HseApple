package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskDao extends JpaRepository<UserTaskEntity, Long> {
    @Query("SELECT ut FROM UserTaskEntity AS ut JOIN TaskEntity AS t ON ut.taskID = t.id WHERE ut.taskID=?1 and ut.status=?2 and t.form=?3")
    Iterable<UserTaskEntity> getListOfAnswers(Long taskID, Boolean status, String form);

    UserTaskEntity findByTaskIDAndUserID(Long taskID, Long userID);
}
