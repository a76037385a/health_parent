package com.app.dao;

import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.pojo.Role;
import com.github.pagehelper.Page;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {

    Set<Role> findRolesByUserId(int userId);

    Role findById(Integer id);

    void add(Role role);

    Page<Role> findByCondition(String queryString);

    void delete(Integer id);

    void update(Role role);

    List<Role> findAll();

    List<Permission> findAllPermission();

    List<Menu> findAllMenu();

    Integer[] findPermissionIdsByRoleId(Integer id);

    Integer[] findMenuIdsByRoleId(Integer id);

    void setRoleAndPermission(Map<String, Integer> map);

    void deleteRoleAndPermission(Integer roleId);

    void setRoleAndMenu(Map<String, Integer> map);

    void deleteRoleAndMenu(Integer roleId);
}
