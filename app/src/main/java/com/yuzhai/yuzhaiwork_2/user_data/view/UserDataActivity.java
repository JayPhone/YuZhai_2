package com.yuzhai.yuzhaiwork_2.user_data.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.user_data.presenter.UserDataPresenter;

/**
 * Created by 35429 on 2017/6/14.
 */

public class UserDataActivity extends AppCompatActivity {
    private static final String TAG = "UserDataActivity";
    public static final String ORDER = "order";
    public static final String AVATAR = "avatar";
    public static final String PRICE = "price";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        UserDataFragment userDataFragment = UserDataFragment.newInstance();
        ActivityUtil.addFragment(getSupportFragmentManager(), userDataFragment, R.id.user_data_content);

        new UserDataPresenter(userDataFragment);
    }
}
