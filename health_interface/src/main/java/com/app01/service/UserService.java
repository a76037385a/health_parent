package com.app01.service;

import com.app01.entity.PageResult;
import com.app01.entity.Result;
import com.app01.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    void add(User user, Integer[] roleIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(String username);

    void update(User user, Integer[] roleIds);

    User findById(Integer id);

    List<User> findAll();

    Integer[] findRoleIdsByUserId(Integer id);

    User findByUsername(String username);
}
