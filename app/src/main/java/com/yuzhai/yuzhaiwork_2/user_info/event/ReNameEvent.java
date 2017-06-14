package com.yuzhai.yuzhaiwork_2.user_info.event;

/**
 * Created by 35429 on 2017/6/12.
 */

public class ReNameEvent {
    private String newName;

    public ReNameEvent(String newName) {
        this.newName = newName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public String toString() {
        return "ReNameEvent{" +
                "newName='" + newName + '\'' +
                '}';
    }
}
