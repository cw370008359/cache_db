package com.danhesoft.dao;

import com.danhesoft.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by caowei on 2018/5/9.
 */
public interface CourseRepository extends JpaRepository<Courses, Long>{

}
