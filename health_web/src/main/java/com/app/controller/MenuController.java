package com.app.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.entity.Result;
import com.app01.msg.MessageConstant;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.service.MenuService;
import com.app01.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    MenuService menuService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MENU_ADD')")
    public Result addMenu(@RequestBody Menu menu) {
        try {
            //do service
            menuService.add(menu);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_Menu_FAIL);
        }

        return new Result(true,MessageConstant.ADD_Menu_SUCCESS);
    }

    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('MENU_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = menuService.findPageByCondition(queryPageBean);
        return pageResult;
    }
////
    @GetMapping("/deleteRow")
    @PreAuthorize("hasAuthority('MENU_DELETE')")
    public Result deleteRow(int id) {
        try {
           menuService.deleteMenuById(id);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e){
            return new Result(false,MessageConstant.DELETE_Menu_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_Menu_SUCCESS);
    }
////
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('MENU_QUERY')")
    public Result findById(int id) {
        Menu menu = menuService.findMenuById(id);
        return new Result(true,MessageConstant.QUERY_Menu_SUCCESS,menu);
    }
////
    @PostMapping("/editMenuById")
    @PreAuthorize("hasAuthority('MENU_EDIT')")
    public Result editMenuById(@RequestBody Menu menu) {
        try {
           menuService.editMenuById(menu);

        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_Menu_FAIL);
        }

        return new Result(true,MessageConstant.EDIT_Menu_SUCCESS);
    }

}
