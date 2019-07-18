package com.app.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.app.dao.SetmealDao;
import com.app01.QiNiuCloud.CloudKeys;
import com.app01.Utils.jedisUtils.JedisUtil;
import com.app01.entity.PageResult;
import com.app01.entity.QueryPageBean;
import com.app01.entity.Result;
import com.app01.msg.MessageConstant;
import com.app01.pojo.CheckGroup;
import com.app01.pojo.Package;
import com.app01.redis.RedisConstant;
import com.app01.service.SetmealService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService{

    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jredisPool;


    @Override
    public PageResult findAllPage(QueryPageBean queryPageBean) {

        //判断是否有查询字符串
        if (queryPageBean.getQueryString() != null){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Package> page = setmealDao.findAllPage(queryPageBean.getQueryString());
        //封装结果集
       PageResult pageResult = new PageResult(page.getTotal(),page.getResult());


          return pageResult;
    }

    @Override
    public List<CheckGroup> getAllCheckGroup() {
        return setmealDao.getAllCheckGroup();
    }

    @Override
    //@Transactional
    public void addPackage(Package packge, Integer[] pickCheckGroups) {
        try {
            if(packge.getImg()!= null){
                String imageUrl = packge.getImg();
                String imageName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
                System.out.println("imageName:"+imageName);
                packge.setImg(imageName);
            }
            setmealDao.addPackge(packge);
            jredisPool.getResource().sadd(RedisConstant.imgDb,packge.getImg());
            System.out.println(packge.getId());
            if(pickCheckGroups.length > 0 && pickCheckGroups != null){
                for (Integer pickCheckGroup : pickCheckGroups) {
                    setmealDao.addPackgeAndCheckGroupByPackgeId(packge.getId(),pickCheckGroup);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Result  findAllPackages() {

        /*
        if redis 里有, 直接返回
        没有,查询数据库 存入redis
        返回查询
         */

        try {
            List<Package> list = setmealDao.findAllPackage();
            for (Package aPackage : list) {
                String imgName = CloudKeys.domain  + aPackage.getImg() ;
                aPackage.setImg( imgName );
            }
            Jedis resource = jredisPool.getResource();
//            StringBuilder sb = new StringBuilder();
//            for (Package aPackage : list) {
//                sb.append(aPackage.toString());
//            }

            resource.set("Setmeallist",JSON.toJSONString(list));


         return   new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
             return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }


        //链式调用测试,不用动
        //list.forEach(packge ->{
        //String imgName = CloudKeys.domain  + packge.getImg() ;
        //packge.setImg( imgName );
        //  });


    }
    @Override
    public List<Package> findAllPackage (){
        List<Package> list = setmealDao.findAllPackage();
        for (Package aPackage : list) {
            String imgName = CloudKeys.domain  + aPackage.getImg() ;
            aPackage.setImg( imgName );
        }
        return list;
    }

    @Override
    public Package findPackgeInfosById(int id) {
        Package pac = setmealDao.findPackgeInfosById(id);
        pac.setImg(CloudKeys.domain + pac.getImg());
        return pac;
    }
    @Override
    //修改
    public Result findPackgeInfosByid(int id) {
        try {
            Package pac = setmealDao.findPackgeInfosById(id);
            pac.setImg(CloudKeys.domain + pac.getImg());
            Jedis resource = jredisPool.getResource();

            resource.set("findPackgeInfosByid",JSON.toJSONString(pac));

            return  new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,pac);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
    @Override
        public Package findPackgeByid(int id) {

            Package pac = setmealDao.findPackgeByid(id);
            pac.setImg(CloudKeys.domain + pac.getImg());


        return pac;
    }


}
