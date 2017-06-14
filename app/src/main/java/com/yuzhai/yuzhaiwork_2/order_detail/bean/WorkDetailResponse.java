package com.yuzhai.yuzhaiwork_2.order_detail.bean;

import java.util.List;

/**
 * Created by 35429 on 2017/6/13.
 */

public class WorkDetailResponse {
    private WorkDetail detailed_order;

    public WorkDetail getDetailed_order() {
        return detailed_order;
    }

    public void setDetailed_order(WorkDetail detailed_order) {
        this.detailed_order = detailed_order;
    }

    @Override
    public String toString() {
        return "WorkDetailResponse{" +
                "detailed_order=" + detailed_order +
                '}';
    }

    public static class WorkDetail {
        private String publisher;
        private String description;
        private String reward;
        private String date;
        private String order_id;
        private String publisher_avatar;
        private String deadline;
        private String title;
        private String status;
        private String tel;
        private List<Pictures> pictures;
        private List<ApplicantAvatars> applicant_avatars;

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getPublisher_avatar() {
            return publisher_avatar;
        }

        public void setPublisher_avatar(String publisher_avatar) {
            this.publisher_avatar = publisher_avatar;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public List<Pictures> getPictures() {
            return pictures;
        }

        public void setPictures(List<Pictures> pictures) {
            this.pictures = pictures;
        }

        public List<ApplicantAvatars> getApplicant_avatars() {
            return applicant_avatars;
        }

        public void setApplicant_avatars(List<ApplicantAvatars> applicant_avatars) {
            this.applicant_avatars = applicant_avatars;
        }

        @Override
        public String toString() {
            return "WorkDetail{" +
                    "publisher='" + publisher + '\'' +
                    ", description='" + description + '\'' +
                    ", reward='" + reward + '\'' +
                    ", date='" + date + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", publisher_avatar='" + publisher_avatar + '\'' +
                    ", deadline='" + deadline + '\'' +
                    ", title='" + title + '\'' +
                    ", status='" + status + '\'' +
                    ", tel='" + tel + '\'' +
                    ", pictures=" + pictures +
                    ", applicant_avatars=" + applicant_avatars +
                    '}';
        }

        //图片信息
        public static class Pictures {
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            @Override
            public String toString() {
                return "Pictures{" +
                        "image='" + image + '\'' +
                        '}';
            }
        }

        //申请人信息
        public static class ApplicantAvatars {
            private String applicant_avatar;

            public String getApplicantAvatar() {
                return applicant_avatar;
            }

            public void setApplicantAvatar(String applicantAvatar) {
                applicant_avatar = applicantAvatar;
            }

            @Override
            public String toString() {
                return "applicant_avatars{" +
                        "applicant_avatar='" + applicant_avatar + '\'' +
                        '}';
            }
        }
    }
}
