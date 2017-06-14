package com.yuzhai.yuzhaiwork_2.base.util;

import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FileUtil {
    public final static int HEAD_IMAGE = 1;
    public final static int NEED_IMAGE = 2;

    public static boolean isStorageExist() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            Log.i("错误", "请确认已经插入SD卡");
            return false;
        }
    }

    public static String createFilePath(int flag) {
        if (flag == HEAD_IMAGE) {
            String path = createImagePath("headImage", "hdImg");
            if (path != null) {
                return path;
            }
        } else if (flag == NEED_IMAGE) {
            String path = createImagePath("needImage", "ndImg");
            if (path != null) {
                return path;
            }
        } else {
            throw new IllegalArgumentException("没有这个参数");
        }
        return null;
    }

    public static String createImagePath(String folderName, String prefix) {
        if (isStorageExist()) {
            //保存图片的路径
            String out_file_path = Environment.getExternalStorageDirectory() + "/YuZhai/" + folderName;
            //创建图片路径文件夹
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return out_file_path + "/" + prefix + "_" + DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        } else {
            return null;
        }
    }
}
