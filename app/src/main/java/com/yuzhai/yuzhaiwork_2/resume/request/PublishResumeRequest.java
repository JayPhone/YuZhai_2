package com.yuzhai.yuzhaiwork_2.resume.request;

/**
 * Created by 35429 on 2017/6/9.
 */

public class PublishResumeRequest {
    private String name;
    private String sex;
    private String type;
    private String education;
    private String tel;
    private String educationalExperience;
    private String skill;
    private String workExperience;
    private String selfEvaluation;

    public PublishResumeRequest(String name, String sex,
                                String type, String education,
                                String tel, String educationalExperience,
                                String skill, String workExperience,
                                String selfEvaluation) {
        this.name = name;
        this.sex = sex;
        this.type = type;
        this.education = education;
        this.tel = tel;
        this.educationalExperience = educationalExperience;
        this.skill = skill;
        this.workExperience = workExperience;
        this.selfEvaluation = selfEvaluation;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEducationalExperience() {
        return educationalExperience;
    }

    public void setEducationalExperience(String educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    @Override
    public String toString() {
        return "PublishResumeRequest{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", type='" + type + '\'' +
                ", education='" + education + '\'' +
                ", tel='" + tel + '\'' +
                ", educationalExperience='" + educationalExperience + '\'' +
                ", skill='" + skill + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", selfEvaluation='" + selfEvaluation + '\'' +
                '}';
    }
}
