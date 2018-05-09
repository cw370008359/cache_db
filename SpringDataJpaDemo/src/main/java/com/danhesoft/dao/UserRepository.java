package com.danhesoft.dao;

import com.danhesoft.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by caowei on 2018/5/8.
 */
public interface UserRepository extends JpaRepository<Users,Long>{

    List findByName(String name);

    Optional<Users> findById(Long id);

    List findByNameAndPhone(String name, String phone);

    List findByNameLike(String name);

}
