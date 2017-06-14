package com.yuzhai.yuzhaiwork_2.category.request;

/**
 * Created by 35429 on 2017/6/8.
 */

public class WorkRequest {
    private String categoryType;
    private String isFirst;

    public WorkRequest(String categoryType, String isFirst) {
        this.categoryType = categoryType;
        this.isFirst = isFirst;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public String toString() {
        return "WorkRequest{" +
                "categoryType='" + categoryType + '\'' +
                ", isFirst='" + isFirst + '\'' +
                '}';
    }
}
