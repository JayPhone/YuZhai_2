package com.yuzhai.yuzhaiwork_2.category.bean;

import java.util.List;

/**
 * Created by 35429 on 2017/6/3.
 */

public class WorkDatas {
    private String code;
    private List<WorkData> orders;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<WorkData> getOrders() {
        return orders;
    }

    public void setOrders(List<WorkData> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "WorkDatas{" +
                "code='" + code + '\'' +
                ", orders=" + orders +
                '}';
    }

    public static class WorkData {
        private String publisher;
        private String reward;
        private String date;
        private String order_id;
        private String type;
        private String deadline;
        private String title;
        private List<Images> picture;

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
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

        public List<Images> getPicture() {
            return picture;
        }

        public void setPicture(List<Images> picture) {
            this.picture = picture;
        }

        @Override
        public String toString() {
            return "WorkData{" +
                    "publisher='" + publisher + '\'' +
                    ", reward='" + reward + '\'' +
                    ", date='" + date + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", type='" + type + '\'' +
                    ", deadline='" + deadline + '\'' +
                    ", title='" + title + '\'' +
                    ", picture=" + picture +
                    '}';
        }

        public static class Images {
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            @Override
            public String toString() {
                return "Images{" +
                        "image='" + image + '\'' +
                        '}';
            }
        }

    }
}
