package com.yuzhai.yuzhaiwork_2.base.view;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/18.
 */
public class UnRepeatToast {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
