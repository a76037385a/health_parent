package com.app.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.entity.Result;
import com.app01.msg.MessageConstant;
import com.app01.pojo.Menu;
import com.app01.pojo.Role;
import com.app01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    UserService userService;

    @GetMapping("/getUsername")
    public Result getUsername() {
        System.out.println("getUsername");
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());

        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
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
