package com.app.dao;

import com.app01.pojo.Menu;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.LinkedHashSet;

public interface MenuDao {

    LinkedHashSet<Menu> findMenusByRoleId(int roleId);

    List<Menu> findMenusByMenuId(@Param("roleId") int menuid,@Param("parentMenuId") int parentMenuId );

    void add(Menu menu);

    Page<Menu> findPageByCondition(String queryString);

    void deleteById(int id);

    Menu findMenuById(int id);

    void updateMenu(Menu menu);

    Long findCountByMenuId(int id);
}
