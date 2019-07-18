package com.app.controller;


import com.alibaba.dubbo.config.annotation.Reference;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.app01.entity.Result;
import com.app01.msg.MessageConstant;
import com.app01.pojo.Package;
import com.app01.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;


    @GetMapping("/getSetmeals")
    public Result getSetmeals() {

        Jedis data = null;
        String setmeallist = null;
        Result result = null;
        try {
            data = jedisPool.getResource();
            setmeallist = data.get("Setmeallist");
            List<Package> list = JSON.parseArray(setmeallist, Package.class);

            if (list == null) {
                result = setmealService.findAllPackages();
            }else {
                result = new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, list);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            result = setmealService.findAllPackages();
            return result;

        }


    }

    @GetMapping("/getSetmeal")
    //原代码
    public Result getSetmeal() {
        try {
            List<Package> list = setmealService.findAllPackage();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //原代码
    @GetMapping("/findPackgeInfosById")
    public Package findPackgeInfosById(int id) {

        System.out.println(id);
        return setmealService.findPackgeInfosById(id);
    }

    //改动代码
    @GetMapping("/findPackgeInfosByid")
    public Result findPackgeInfosByid(int id) {
        Jedis data = null;
        String findPackgeInfosByid = null;
        Result result = null;
        try {
            data = jedisPool.getResource();
            findPackgeInfosByid = data.get("findPackgeInfosByid");
            Object packages = JSON.parse(findPackgeInfosByid);
            //  Package packages = (Package) JSON.parseArray(findPackgeInfosByid, Package.class);
            if (packages == null) {
                result = setmealService.findPackgeInfosByid(id);
            } else {

                result = new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, packages);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = setmealService.findPackgeInfosByid(id);

            return result;

        }


    }

    @GetMapping("/findPackgeByid")
    public Result findPackgeByid(int id) {
        System.out.println(id);
        try {
            Package pac = setmealService.findPackgeByid(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pac);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
