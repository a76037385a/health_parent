package com.app.dao;

import com.app01.pojo.CheckItem;
import com.app01.pojo.Permission;
import com.github.pagehelper.Page;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findByRoleId(int roleId);

    void add(Permission permission);

    Page<Permission> findPageByCondition(String queryString);

    Long findCountByPermissionId(int id);

    void deleteById(int id);

    Permission findPermissionById(int id);

    void updatePermission(Permission permission);
}
