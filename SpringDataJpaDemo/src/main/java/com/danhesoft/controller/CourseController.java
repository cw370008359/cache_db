package com.danhesoft.controller;

import com.danhesoft.model.Courses;
import com.danhesoft.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caowei on 2018/5/9.
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @RequestMapping("/save")
    public String addCourse(){
        Courses courses = new Courses();
        courses.setCoursename("java");
        if(courseService.addCourse(courses)){
            return "success";
        }else
            return "failure";
    }

}
