package com.danhesoft.controller;

import com.danhesoft.model.FailureBean;
import com.danhesoft.model.ResultBean;
import com.danhesoft.model.SuccessBean;
import com.danhesoft.model.Users;
import com.danhesoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;

/**
 * Created by caowei on 2018/5/8.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 保存
     * @param name
     * @param address
     * @param phone
     * @return
     */
    @PostMapping("/add")
    public Users saveUser(@RequestParam("name")String name, @RequestParam("address")String address, @RequestParam("phone")String phone){
        Users users = new Users();
        users.setName(name);
        users.setAddress(address);
        users.setPhone(phone);
        Users user = null;
       try{
            user = userService.saveUser(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更新用户信息
     * @param id
     * @param name
     * @param address
     * @param phone
     * @return
     * localhost:8080/user/update/9?name=小胖子&address=淞虹公寓&phone=13569878956
     */
    @PutMapping("/update/{id}")
    public Users updateUserById(@PathVariable Long id, @RequestParam("name")String name, @RequestParam("address")String address, @RequestParam("phone")String phone){
        Users user = new Users();
        user.setId(id);
        user.setName(name);
        user.setAddress(address);
        user.setPhone(phone);
        return userService.updateUser(user);
    }

    /**
     * 删除
     * @param id
     * @return
     * localhost:8080/user/delete/9
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
        }
        catch(Exception e){
            return new FailureBean(e.getMessage());
        }
        return new SuccessBean();
    }

    /**
     * 根据姓名获取用户
     * @param name
     * @return
     * localhost:8080/user/name/小王
     */
    @GetMapping("/name/{name}")
    public List findUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    /**
     * 查询所有用户
     * @return
     * localhost:8080/user/all
     */
    @GetMapping("/all")
    public List findAllUser(){
        return userService.getAllUser();
    }

    /**
     * 根据id返回用户对象
     * @param id
     * @return
     * localhost:8080/user/id/8
     */
    @GetMapping("/id/{id}")
    public Users findUserById(@PathVariable Long id){
        Optional<Users> usersOptional = userService.getUserById(id);
        if(usersOptional.isPresent()){
            return usersOptional.get();
        }else
            return null;
    }

    /**
     * 根据多个关键字精确查询
     * @param name
     * @param phone
     * @return
     * localhost:8080/user/keys?name=小王&phone=13462532125
     */
    @GetMapping("/keys")
    public List findUserByMuiltyKey(@RequestParam("name")String name, @RequestParam("phone")String phone){
        return userService.findUserByMuiltyKey(name, phone);
    }

    /**
     * 根据姓名模糊查询
     * @param name
     * @return
     * localhost:8080/user/key/小
     */
    @GetMapping("/key/{name}")
    public List findUsersBykey(@PathVariable String name){
        return userService.findUserByKey(name+"%");
    }


}