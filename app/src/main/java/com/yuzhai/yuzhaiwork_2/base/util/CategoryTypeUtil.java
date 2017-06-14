package com.yuzhai.yuzhaiwork_2.base.util;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.R;

/**
 * Created by 35429 on 2017/6/2.
 */

public class CategoryTypeUtil {
    private static final String TAG = "CategoryTypeUtil";

    private static final String IT = "软件IT";
    private static final String MUSIC = "音乐制作";
    private static final String DESIGN = "平面设计";
    private static final String MOVIE = "视频拍摄";
    private static final String GAME = "游戏研发";
    private static final String WRITE = "文案撰写";
    private static final String CALCULATE = "金融会计";
    private static final String PLUS = "添加类别";

    private static final int IT_IMAGE = R.drawable.it;
    private static final int MUSIC_IMAGE = R.drawable.music;
    private static final int DESIGN_IMAGE = R.drawable.design;
    private static final int MOVIE_IMAGE = R.drawable.movie;
    private static final int GAME_IMAGE = R.drawable.game;
    private static final int WRITE_IMAGE = R.drawable.write;
    private static final int CALCULATE_IMAGE = R.drawable.calculate;
    private static final int PLUS_IMAGE = R.drawable.plus;


    public static int getTypeImageRes(String type) {
        int imageId = 0;
        switch (type) {
            case IT:
                imageId = IT_IMAGE;
                break;
            case MUSIC:
                imageId = MUSIC_IMAGE;
                break;
            case DESIGN:
                imageId = DESIGN_IMAGE;
                break;
            case MOVIE:
                imageId = MOVIE_IMAGE;
                break;
            case GAME:
                imageId = GAME_IMAGE;
                break;
            case WRITE:
                imageId = WRITE_IMAGE;
                break;
            case CALCULATE:
                imageId = CALCULATE_IMAGE;
                break;
            case PLUS:
                imageId = PLUS_IMAGE;
                break;
        }
        return imageId;
    }
}
