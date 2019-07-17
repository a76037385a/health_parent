package com.app01.service;

import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.pojo.Menu;
import com.app01.pojo.Permission;

public interface MenuService {


    void add(Menu menu);


    PageResult findPageByCondition(QueryPageBean queryPageBean);

    void deleteMenuById(int id);

    Menu findMenuById(int id);

    void editMenuById(Menu menu);
}
