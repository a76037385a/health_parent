package com.app01.jobs;


import com.alibaba.dubbo.config.annotation.Reference;
import com.app01.service.OrderSettingService;

import java.util.Date;


public class CleanOrderSetting {
   @Reference
   private OrderSettingService orderSettingService;

    public void cleanOrderSetting(){
        System.out.println("cleanOrderSetting begin......");
        Date date = new Date();
        orderSettingService.delete(date);
        System.out.println("cleanOrderSetting end.....");
    }
}
