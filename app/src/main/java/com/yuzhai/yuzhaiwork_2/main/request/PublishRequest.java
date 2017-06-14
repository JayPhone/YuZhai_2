package com.yuzhai.yuzhaiwork_2.main.request;

import java.util.List;

/**
 * Created by 35429 on 2017/6/10.
 */

public class PublishRequest {
    private String title;
    private String description;
    private String type;
    private String deadline;
    private String tel;
    private String reward;
    private List<?> files;

    public PublishRequest(String title, String description, String type,
                          String deadline, String tel, String reward, List<String> files) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.deadline = deadline;
        this.tel = tel;
        this.reward = reward;
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public List<?> getFiles() {
        return files;
    }

    public void setFiles(List<?> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "PublishRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", deadline='" + deadline + '\'' +
                ", tel='" + tel + '\'' +
                ", reward='" + reward + '\'' +
                ", files=" + files +
                '}';
    }
}
