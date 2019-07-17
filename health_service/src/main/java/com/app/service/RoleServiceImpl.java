package com.app.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app01.service.RoleService;
import com.app.dao.RoleDao;
import com.app01.entity.PageResult;
import com.app01.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: health_parent
 * @description:
 * @author: Freeze Wu
 * @create: 2019-07-16 20:44
 **/

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public void add(Role role) {

    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public Role findById(Integer id) {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
