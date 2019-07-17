package com.app.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.msg.MessageConstant;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.entity.Result;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.pojo.Role;
import com.app01.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: health_parent
 * @description:
 * @author: Freeze Wu
 * @create: 2019-07-10 22:00
 **/

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;
    

    @RequestMapping("add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){
        roleService.add(role, permissionIds, menuIds);
        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = roleService.pageQuery(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;

    }

    @RequestMapping("delete")
    public Result delete(Integer id){
        try {
            roleService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }

    }

    @RequestMapping("update")
    public Result update(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){
        roleService.update(role, permissionIds, menuIds);
        return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        Role role = roleService.findById(id);
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, role);
    }

    @RequestMapping("findAll")
    public Result findAll(){
        List<Role> roles = roleService.findAll();
        System.out.println(roles);
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, roles);
    }

    @RequestMapping("findAllPermission")
    public Result findAllPermission(){
        List<Permission> permissions = roleService.findAllPermission();
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, permissions);
    }

    @RequestMapping("findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(Integer id){
        Integer[] permissionIds = roleService.findPermissionIdsByRoleId(id);
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, permissionIds);
    }

    @RequestMapping("findAllMenu")
    public Result findAllMenu(){
        List<Menu> menus = roleService.findAllMenu();
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, menus);
    }

    @RequestMapping("findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(Integer id){
        Integer[] menuIds = roleService.findMenuIdsByRoleId(id);
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS, menuIds);
    }
}
