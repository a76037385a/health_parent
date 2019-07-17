package com.app.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.entity.Result;
import com.app01.pojo.User;
import com.app01.msg.MessageConstant;
import com.app01.pojo.User;
import com.app01.service.UserService;
import com.app01.pojo.Menu;
import com.app01.pojo.Role;
import com.app01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.LinkedHashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @GetMapping("/getUsername")
    public Result getUsername() {
        System.out.println("getUsername");
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());

        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("add")
    public Result add(@RequestBody User user, Integer[] roleIds){
        userService.add(user, roleIds);
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.pageQuery(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;

    }

    @RequestMapping("delete")
    public Result delete(String username){
        try {
            userService.delete(username);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }

    }

    @RequestMapping("update")
    public Result update(@RequestBody User user, Integer[] roleIds){
        userService.update(user, roleIds);
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        User user = userService.findById(id);
        return new Result(true, MessageConstant.GET_USER_SUCCESS, user);
    }

    @RequestMapping("findAll")
    public Result findAll(){
        List<User> users = userService.findAll();
        System.out.println(users);
        return new Result(true, MessageConstant.GET_USER_SUCCESS, users);
    }

    @RequestMapping("findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer id){
        Integer[] roleIds = userService.findRoleIdsByUserId(id);
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, roleIds);
    }

    @RequestMapping("findByUsername")
    public Result findByUsername(String username){
        User user = userService.findByUsername(username);
        return new Result(true, MessageConstant.GET_USER_SUCCESS, user);
    }
    @GetMapping("/getUserMenuByUsername")
    public Result getUserMenuByUsername(String name) {
        System.out.println(name);
        try {
            com.app01.pojo.User user = userService.findUserByUsername(name);
            LinkedHashSet<Menu> menus = null;
            Set<Role> roleSet = user.getRoles();
            if(roleSet != null && roleSet.size()>0){
                for (Role role : roleSet) {
                    menus = role.getMenus();
                }
            }
            return new Result(true,MessageConstant.GET_MENU_SUCCESS,menus);
        }catch (Exception e){
            return new Result(false,MessageConstant.GET_MENU_FAIL);
        }
    }

}
