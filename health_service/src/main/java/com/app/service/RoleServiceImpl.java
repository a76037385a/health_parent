package com.app.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.service.RoleService;
import com.app.dao.RoleDao;
import com.app01.entity.PageResult;
import com.app01.pojo.Role;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);

        addAssociation(role, permissionIds, menuIds);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {

        //使用pageHelper插件
        PageHelper.startPage(currentPage, pageSize);
        //拼接模糊查询的%
        if(!StringUtils.isEmpty(queryString)){
            queryString = "%" + queryString + "%";
        }

        //调用startPage方法后的下一个select方法会执行分页
        Page<Role> page = roleDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        try {
            roleDao.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("请先删除角色跟权限以及菜单的对应关系");
        }
    }

    @Override
    @Transactional
    public void update(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.update(role);

        roleDao.deleteRoleAndPermission(role.getId());

        roleDao.deleteRoleAndMenu(role.getId());

        addAssociation(role, permissionIds, menuIds);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<Permission> findAllPermission() {
        return roleDao.findAllPermission();
    }

    @Override
    public Integer[] findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    @Override
    public List<Menu> findAllMenu() {
        return roleDao.findAllMenu();
    }

    @Override
    public Integer[] findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    public void addAssociation(Role role, Integer[] permissionIds, Integer[] menuIds){
        //创建对应关系的map
        if(permissionIds != null && permissionIds.length > 0) {
            Map<String, Integer> map = new HashMap<>();

            //遍历roleIds数组
            for (Integer permissionId : permissionIds) {
                map.put("role_id", role.getId());
                map.put("permission_id", permissionId);
                //每次遍历在关系表中创建一条数据
                roleDao.setRoleAndPermission(map);
            }
        }

        if(menuIds != null && menuIds.length > 0) {
            Map<String, Integer> map = new HashMap<>();

            //遍历roleIds数组
            for (Integer menuId : menuIds) {
                map.put("role_id", role.getId());
                map.put("menu_id", menuId);
                //每次遍历在关系表中创建一条数据
                roleDao.setRoleAndMenu(map);
            }
        }
    }
}
