package com.yuzhai.yuzhaiwork_2.category.bean;

import java.util.List;

import static com.yuzhai.yuzhaiwork_2.R.string.orders;

/**
 * Created by 35429 on 2017/6/3.
 */

public class ResumeDatas {
    private String code;
    private List<ResumeData> resumes;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResumeData> getResumes() {
        return resumes;
    }

    public void setResumes(List<ResumeData> resumes) {
        this.resumes = resumes;
    }

    @Override
    public String toString() {
        return "ResumeDatas{" +
                "code='" + code + '\'' +
                ", resumes=" + resumes +
                '}';
    }

    public static class ResumeData {
        private String avatar;
        private String user_phone;
        private String name;
        private String module;
        private String sex;
        private String education;
        private String contact_number;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        @Override
        public String toString() {
            return "ResumeData{" +
                    "avatar='" + avatar + '\'' +
                    ", user_phone='" + user_phone + '\'' +
                    ", name='" + name + '\'' +
                    ", module='" + module + '\'' +
                    ", sex='" + sex + '\'' +
                    ", education='" + education + '\'' +
                    ", contact_number='" + contact_number + '\'' +
                    '}';
        }
    }
}
