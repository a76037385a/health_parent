package com.app.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.dao.MenuDao;
import com.app.dao.PermissionDao;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;
import com.app01.service.MenuService;
import com.app01.service.PermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MenuService.class)
//@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    @Transactional
    public void add(Menu menu, String menuLevel) {
        menuDao.add(menu);

        Map<String, Object> map = new HashMap<>();
        map.put("menuId", menu.getId());
        map.put("menuLevel", Integer.parseInt(menuLevel));

        menuDao.updateChildrenMenu(map);
    }


    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Menu> page = menuDao.findPageByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    ////
    @Override
    public void deleteMenuById(int id) {
        Long idcount = menuDao.findCountByMenuId(id);
        if (idcount > 0) {
            throw new RuntimeException("菜单已被引用");
        }

        menuDao.deleteById(id);
    }

    ////
    @Override
    public Menu findMenuById(int id) {


        Menu menu = menuDao.findMenuById(id);
        return menu;
    }

    ////
    @Override
    @Transactional
    public void editMenuById(Menu menu, String menuLevel) {
        menuDao.updateMenu(menu);

        Map<String, Object> map = new HashMap<>();
        map.put("menuId", menu.getId());
        map.put("menuLevel", Integer.parseInt(menuLevel));

        menuDao.updateChildrenMenu(map);
    }
}
