package com.yuzhai.yuzhaiwork_2.main.bean;

/**
 * Created by 35429 on 2017/5/31.
 */

public class CategoryData {
    private int categoryImageId;
    private String categoryText;

    public CategoryData(int categoryImageId, String categoryText) {
        this.categoryImageId = categoryImageId;
        this.categoryText = categoryText;
    }

    public int getCategoryImageId() {
        return categoryImageId;
    }

    public void setCategoryImageId(int categoryImageId) {
        this.categoryImageId = categoryImageId;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    @Override
    public String toString() {
        return "CategoryData{" +
                "categoryImageId=" + categoryImageId +
                ", categoryText=" + categoryText +
                '}';
    }
}
