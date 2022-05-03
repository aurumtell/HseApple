package com.hseapple.service;

import com.hseapple.dao.CourseDao;
import com.hseapple.dao.CourseEntity;
import com.hseapple.dao.RequestDao;
import com.hseapple.dao.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    RequestDao requestDao;

    public CourseService(CourseDao courseDao){
        this.courseDao = courseDao;
    }
    public List<CourseEntity> findAllCourse() { return courseDao.findAll(); };

    public Iterable<RequestEntity> findAllRequests(Long courseID, Boolean approved) {
        return requestDao.findAllByCourseIDAndApproved(courseID, approved);
    }
}