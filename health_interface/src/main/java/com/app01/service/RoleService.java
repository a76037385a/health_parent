package com.app01.service;

import com.app01.entity.PageResult;
import com.app01.pojo.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    void update(Role role);

    Role findById(Integer id);

    List<Role> findAll();
}
