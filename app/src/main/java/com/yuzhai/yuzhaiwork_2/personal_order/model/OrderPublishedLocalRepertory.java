package com.yuzhai.yuzhaiwork_2.personal_order.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderPublishedLocalRepertory implements IOrderPublishedModel {
    private static final String TAG = "PublishedLocalRepertory";
    private List<String> types;
    private List<String> status;
    private List<String> dates;
    private List<String> titles;
    private List<String> prices;
    private List<String> limits;
    private List<String> orderIds;
    private OrderPublishedDatas orderPublishedDatas;

    @Override
    public void getPublishedOrderData(String isFirst, OnRequestResponse<OrderPublishedDatas> onRequestResponse) {
        if (types == null) {
            types = new ArrayList<>();
            types.add("软件IT");
            types.add("音乐制作");
            types.add("平面设计");
            types.add("视频拍摄");
            types.add("游戏研发");
            types.add("文案撰写");
            types.add("金融会计");
        }

        if (status == null) {
            status = new ArrayList<>();
            status.add("已接");
            status.add("待接");
            status.add("完成");
            status.add("已接");
            status.add("待接");
            status.add("完成");
            status.add("已接");
        }

        if (dates == null) {
            dates = new ArrayList<>();
            dates.add("2016-07-14");
            dates.add("2016-07-14");
            dates.add("2016-07-14");
            dates.add("2016-07-14");
            dates.add("2016-07-14");
            dates.add("2016-07-14");
            dates.add("2016-07-14");
        }

        if (titles == null) {
            titles = new ArrayList<>();
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            titles.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
        }

        if (prices == null) {
            prices = new ArrayList<>();
            prices.add("100");
            prices.add("150");
            prices.add("200");
            prices.add("250");
            prices.add("150");
            prices.add("200");
            prices.add("250");
        }

        if (limits == null) {
            limits = new ArrayList<>();
            limits.add("5天");
            limits.add("10天");
            limits.add("15天");
            limits.add("20天");
            limits.add("10天");
            limits.add("15天");
            limits.add("20天");
        }

        if (orderIds == null) {
            orderIds = new ArrayList<>();
            orderIds.add("1954656112");
            orderIds.add("1954656112");
            orderIds.add("1954656112");
            orderIds.add("1954656112");
            orderIds.add("1954656112");
            orderIds.add("1954656112");
            orderIds.add("1954656112");
        }

        if (orderPublishedDatas == null) {
            orderPublishedDatas = new OrderPublishedDatas();
            List<OrderPublishedDatas.OrderData> order = new ArrayList<>();
            for (int i = 0; i < titles.size(); i++) {
                order.add(new OrderPublishedDatas.OrderData(status.get(i), prices.get(i), dates.get(i),
                        orderIds.get(i), types.get(i), limits.get(i), titles.get(i)));
            }
            orderPublishedDatas.setOrders(order);
        }
        Log.d(TAG, String.valueOf(orderPublishedDatas.getOrders().size()));
        Log.d(TAG, String.valueOf(orderPublishedDatas.getOrders().toString()));
        onRequestResponse.onSuccess(orderPublishedDatas);
    }
}
