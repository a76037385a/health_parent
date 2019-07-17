package com.app.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.entity.Result;
import com.app01.msg.MessageConstant;
import com.app01.pojo.CheckItem;
import com.app01.pojo.Permission;
import com.app01.service.CheckItemService;
import com.app01.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    PermissionService permissionService;

    @PostMapping("/add")
    public Result addCheckItem(@RequestBody Permission permission) {
        try {
            //do service
            permissionService.add(permission);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_PERMISSION_FAIL);
        }

        return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = permissionService.findPageByCondition(queryPageBean);
        return pageResult;
    }
//
    @GetMapping("/deleteRow")
    public Result deleteRow(int id) {
        try {
           permissionService.deletePermissionById(id);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e){
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
//
    @GetMapping("/findById")
    public Result findById(int id) {
        Permission permission = permissionService.findPermissionById(id);
        return new Result(true,MessageConstant.QUERY_Permission_SUCCESS,permission);
    }
//
    @PostMapping("/editPermissionById")
    public Result editCheckItemById(@RequestBody Permission permission) {
        try {
           permissionService.editPermissionById(permission);

        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_Permission_FAIL);
        }

        return new Result(true,MessageConstant.EDIT_Permission_SUCCESS);
    }

}
