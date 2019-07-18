package com.app01.service;

import com.app01.entity.Result;
import com.app01.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void addOrderSetting(List<OrderSetting> list);

    List<Map> getOrderSettingByMonth(String date);

    void setOrderByNumber(OrderSetting orderByNumber);

    void delete(Date date);
}
