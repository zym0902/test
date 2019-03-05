package com.telchina.springboot.test.springboot.controller;

import com.telchina.springboot.test.springboot.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    //处理 "/users/"的GET请求，用来获取用户列表
    //还可以通过@RequestParam从页面中传递参数来进行条件查询
    @RequestMapping(value="/",method= RequestMethod.GET)
    public List<User> getUserList(){
        List<User> r = new ArrayList<>(users.values());
        return r;
    }

    //处理"/users/"请求的post请求，用来创建User
    @RequestMapping(value="/",method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user){
        users.put(user.getId(),user);
        return "success";
    }

    //处理"/users/{id}"的PUT请求，用来更新User信息
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return users.get(id);
    }

    //处理"/users/{id}"的delete请求，用来删除User
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    public  String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }












}
