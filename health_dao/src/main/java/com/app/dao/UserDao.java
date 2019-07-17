package com.app.dao;

import com.app01.pojo.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User findUserByUsername(String username);

    void add(User user);

    Page<User> findByCondition(String queryString);

    void delete(String username);

    void update(User user);

    User findById(Integer id);

    List<User> findAll();

    Integer[] findRoleIdsByUserId(Integer id);

    void setUserAndRole(Map<String, Integer> map);

    void deleteAssociation(Integer userId);

    Long findCountByUserId(Integer userId);

    User findByUsername(String username);

}
