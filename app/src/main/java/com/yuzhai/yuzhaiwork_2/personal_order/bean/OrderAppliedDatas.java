package com.yuzhai.yuzhaiwork_2.personal_order.bean;

import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderAppliedDatas {
    private String code;
    private List<OrderData> orders;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<OrderData> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderData> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderAppliedDatas{" +
                "code='" + code + '\'' +
                ", orders=" + orders +
                '}';
    }

    public static class OrderData {
        private String status;
        private String reward;
        private String date;
        private String order_id;
        private String type;
        private String deadline;
        private String title;

        public OrderData(String status, String reward, String date, String order_id, String type, String deadline, String title) {
            this.status = status;
            this.reward = reward;
            this.date = date;
            this.order_id = order_id;
            this.type = type;
            this.deadline = deadline;
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "OrderData{" +
                    "status='" + status + '\'' +
                    ", reward='" + reward + '\'' +
                    ", date='" + date + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", type='" + type + '\'' +
                    ", deadline='" + deadline + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }


}
