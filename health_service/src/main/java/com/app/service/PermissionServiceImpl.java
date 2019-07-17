package com.app.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.dao.CheckItemDao;
import com.app.dao.PermissionDao;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.pojo.CheckItem;
import com.app01.pojo.Permission;
import com.app01.service.CheckItemService;
import com.app01.service.PermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Service(interfaceClass = PermissionService.class)
//@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public void add(Permission permission) {

        permissionDao.add(permission);
    }

    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Permission> page = permissionDao.findPageByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }
//
    @Override
    public void deletePermissionById(int id) {
        Long idcount = permissionDao.findCountByPermissionId(id);
        if (idcount > 0){
            throw new RuntimeException("权限已被引用");
        }
       permissionDao.deleteById(id);
    }
//
    @Override
    public Permission findPermissionById(int id) {

        return permissionDao.findPermissionById(id);
    }
//
    @Override
    public void editPermissionById(Permission permission) {
        permissionDao.updatePermission(permission);
    }
}
