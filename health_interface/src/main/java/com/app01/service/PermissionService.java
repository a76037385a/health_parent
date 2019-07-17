package com.app01.service;

import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.pojo.CheckItem;
import com.app01.pojo.Permission;

public interface PermissionService {

    //新增检查项
    void add(Permission permission);

//    //检查项分页查询
    PageResult findPageByCondition(QueryPageBean queryPageBean);
//
//    //删除检查项
    void deletePermissionById(int id);
//
    Permission findPermissionById(int id);
//
    void editPermissionById(Permission permission);


}
