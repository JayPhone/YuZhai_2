package com.yuzhai.yuzhaiwork_2.base.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/17.
 */
public class BitmapUtil {
    /**
     * 计算bitmap缩放值
     *
     * @param options   bitmap的option
     * @param reqWidth  需求的宽度
     * @param reqHeight 需求的高度
     * @return 缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth,
                                            int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.i("inSampleSize", inSampleSize + "");
        return inSampleSize;
    }

    /**
     * 从文件解析出需求尺寸的bitmap
     *
     * @param path      文件路径
     * @param reqWidth  需求宽度
     * @param reqHeight 需求高度
     * @return 解析完成的bitmap
     */
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Log.i("bitmap_width", bitmap.getWidth() + "");
        Log.i("bitmap_height", bitmap.getHeight() + "");
        return bitmap;
    }

    /**
     * 从URI解析出需求尺寸的bitmap
     *
     * @param uri       uri地址
     * @param reqWidth  需求宽度
     * @param reqHeight 需求高度
     * @return 解析完成的bitmap
     */
    public static Bitmap decodeSampledBitmapFromFile(Uri uri, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri.getPath(), options);

        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
        Log.i("bitmap_width", bitmap.getWidth() + "");
        Log.i("bitmap_height", bitmap.getHeight() + "");
        return bitmap;
    }

    /**
     * 从资源文件解析出需求尺寸的bitmap
     *
     * @param res       资源实例
     * @param resId     资源Id
     * @param reqWidth  需求宽度
     * @param reqHeight 需求高度
     * @return 解析完成的bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        Log.i("bitmap_width", bitmap.getWidth() + "");
        Log.i("bitmap_height", bitmap.getHeight() + "");
        return bitmap;
    }
}
