package com.yuzhai.yuzhaiwork_2.resume.bean;

/**
 * Created by 35429 on 2017/6/10.
 */

public class QueryResumeResponse {
    private ResumeDetail detail_resume;

    public ResumeDetail getDetail_resume() {
        return detail_resume;
    }

    public void setDetail_resume(ResumeDetail detail_resume) {
        this.detail_resume = detail_resume;
    }

    @Override
    public String toString() {
        return "QueryResumeResponse{" +
                "detail_resume=" + detail_resume +
                '}';
    }

    public static class ResumeDetail {
        private String avatar;
        private String user_name;
        private String contact_number;
        private String name;
        private String sex;
        private String module;
        private String education;
        private String education_experience;
        private String skill;
        private String work_experience;
        private String self_evaluation;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getEducation_experience() {
            return education_experience;
        }

        public void setEducation_experience(String education_experience) {
            this.education_experience = education_experience;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getWork_experience() {
            return work_experience;
        }

        public void setWork_experience(String work_experience) {
            this.work_experience = work_experience;
        }

        public String getSelf_evaluation() {
            return self_evaluation;
        }

        public void setSelf_evaluation(String self_evaluation) {
            this.self_evaluation = self_evaluation;
        }

        @Override
        public String toString() {
            return "ResumeDetail{" +
                    "avatar='" + avatar + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", contact_number='" + contact_number + '\'' +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", module='" + module + '\'' +
                    ", education='" + education + '\'' +
                    ", education_experience='" + education_experience + '\'' +
                    ", skill='" + skill + '\'' +
                    ", work_experience='" + work_experience + '\'' +
                    ", self_evaluation='" + self_evaluation + '\'' +
                    '}';
        }
    }
}
