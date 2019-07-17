package com.app01.service;

import com.app01.entity.PageResult;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.pojo.Role;

import java.util.List;

public interface RoleService {
    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    void update(Role role, Integer[] permissionIds, Integer[] menuIds);

    Role findById(Integer id);

    List<Role> findAll();

    List<Permission> findAllPermission();

    Integer[] findPermissionIdsByRoleId(Integer id);

    List<Menu> findAllMenu();

    Integer[] findMenuIdsByRoleId(Integer id);


}
