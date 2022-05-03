package com.hseapple.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM RequestEntity AS ur " +
            "LEFT JOIN UserEntity AS u ON ur.userID = u.id " +
            "WHERE ur.courseID=?1 and ur.roleID=?2 and ur.approved=?3")
    List<UserEntity> getListOfUsers(Long courseID, Long roleID, Boolean approved);

    UserEntity findByEmail(String email);

    @Query("SELECT r.role_user FROM UserCourseEntity AS uc " +
            "JOIN RoleEntity AS r ON uc.roleID = r.id " +
            "WHERE uc.userID=?1")
    List<String> findAllRoleById(Long id);

    @Query("SELECT p FROM ProfileEntity AS p " +
            "JOIN UserEntity AS u ON p.userID = u.id " +
            "WHERE p.userID=?1")
    ProfileEntity getProfile(Long id);
}
