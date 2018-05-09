package com.danhesoft.service.impl;

import com.danhesoft.dao.CourseRepository;
import com.danhesoft.dao.UserRepository;
import com.danhesoft.model.Courses;
import com.danhesoft.model.Users;
import com.danhesoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Created by caowei on 2018/5/8.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * 加入了事务，课程名称超过5个汉字事务会回滚，用户信息插入不成功
     * @param users
     * @return
     */
    @Override
    @Transactional
    public Users saveUser(Users users) {
        Users newUser = new Users();
        newUser = new Users();
        newUser = userRepository.save(users);
        Courses courses = new Courses();
        //courses.setCoursename("超过5个汉字了");
        courses.setCoursename("没超过");
        courseRepository.save(courses);
        return newUser;
    }

    @Override
    @Transactional
    public Users updateUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List findUserByMuiltyKey(String name, String phone) {
        return userRepository.findByNameAndPhone(name, phone);
    }

    @Override
    public List findUserByKey(String name) {
        return userRepository.findByNameLike(name);
    }


}
