package com.app01.service;

import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.entity.Result;
import com.app01.pojo.CheckGroup;
import com.app01.pojo.Package;

import java.util.List;
import java.util.Map;

public interface SetmealService {

    PageResult findAllPage(QueryPageBean queryPageBean);

    List<CheckGroup> getAllCheckGroup();

    void addPackage(Package packge, Integer[] pickCheckGroups);

    Result findAllPackages();
    List<Package> findAllPackage();

    Package findPackgeInfosById(int id);
 //修改
    Result findPackgeInfosByid(int id);

    Package findPackgeByid(int id);


    List<Map>  getSetmealReport();
}
