package com.danhesoft.controller;

import com.danhesoft.config.ClRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sun.javafx.fxml.expression.Expression.get;

/**
 * Created by caowei on 2018/4/20.
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private ClRedisTemplate clRedisTemplate;

    @RequestMapping("/setName")
    public String setUserName(String userName){
        clRedisTemplate.setWithExpireTime("user", "username", userName, 120);
        return "set success";
    }

    @RequestMapping("/getName")
    public String getUserName(){
        String userName = clRedisTemplate.get("user", "username");
        if(!StringUtils.isEmpty(userName)){
            return userName;
        }else
            return "username is null or is expired";
    }

    @RequestMapping("/deleteName")
    public String deleteUserName(){
        clRedisTemplate.deleteWithPrefix("user", "username");
        return "delete success";
    }

}
