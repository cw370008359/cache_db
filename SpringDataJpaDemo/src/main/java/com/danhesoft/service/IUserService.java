package com.danhesoft.service;

import com.danhesoft.model.Users;
import java.util.List;
import java.util.Optional;

/**
 * Created by caowei on 2018/5/8.
 */
public interface IUserService {

    Users saveUser(Users users);

    Users updateUser(Users users);

    void deleteUser(Long id);

    List getUserByName(String name);

    List getAllUser();

    Optional<Users> getUserById(Long id);

    List findUserByMuiltyKey(String name, String phone);

    List findUserByKey(String name);
}
