package com.app.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.dao.MenuDao;
import com.app.dao.PermissionDao;
import com.app.dao.RoleDao;
import com.app.dao.UserDao;
import com.app01.entity.PageResult;
import com.app01.entity.Result;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.pojo.Role;
import com.app01.pojo.User;
import com.app01.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    MenuDao menuDao;

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        if (user == null){
            return null;
        }

        //查找 用户组
        Set<Role> roles = roleDao.findRolesByUserId(user.getId());

        if(roles != null && roles.size() > 0){
            for (Role role : roles) {
                int roleId = role.getId();

                //查找操作权限
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if(permissions != null && permissions.size()>0){
                    role.setPermissions(permissions);
                }
                //查找菜单权限
                LinkedHashSet<Menu> menus = menuDao.findMenusByRoleId(roleId);
                if(menus != null && menus.size()>0){
                    for (Menu menu : menus) {
                        List<Menu> cmenu = menuDao.findMenusByMenuId(menu.getId());
                        menu.setChildren(cmenu);
                    }
                    role.setMenus(menus);
                }

            }
            user.setRoles(roles);
        }



        return user;
    }

    public Boolean isNullOrEmpty(Set set) {

        if(set == null){
            return false;
        }

        if(set.size() == 0){
            return false;
        }

        return null;
    }

    @Override
    @Transactional
    public void add(User user, Integer[] roleIds) {
        //添加前先对密码进行加密
        String userPassword = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(userPassword);
        user.setPassword(password);

        userDao.add(user);

        addAssociation(user, roleIds);
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
        Page<User> page = userDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String username) {
        try {
            userDao.delete(username);
        } catch (Exception e) {
            throw new RuntimeException("请先删除用户跟角色的对应关系");
        }
    }

    @Override
    @Transactional
    public void update(User user, Integer[] roleIds) {
        String userPassword = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(userPassword);
        user.setPassword(password);

        userDao.update(user);

        //先删除旧关系，再添加新关系
        userDao.deleteAssociation(user.getId());

        addAssociation(user, roleIds);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Integer[] findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addAssociation(User user, Integer[] roleIds){
        //创建对应关系的map
        if(roleIds != null && roleIds.length > 0) {
            Map<String, Integer> map = new HashMap<>();

            //遍历roleIds数组
            for (Integer roleId : roleIds) {
                map.put("user_id", user.getId());
                map.put("role_id", roleId);
                //每次遍历在关系表中创建一条数据
                userDao.setUserAndRole(map);
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
