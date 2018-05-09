package com.danhesoft.service.impl;

import com.danhesoft.dao.CourseRepository;
import com.danhesoft.model.Courses;
import com.danhesoft.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by caowei on 2018/5/9.
 */
@Service("courseService")
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public boolean addCourse(Courses courses) {
        Object saveCourse = courseRepository.save(courses);
        if(saveCourse!=null && saveCourse instanceof Courses)
            return true;
        return false;
    }
}
