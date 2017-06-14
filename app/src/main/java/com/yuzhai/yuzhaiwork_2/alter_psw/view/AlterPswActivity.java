package com.yuzhai.yuzhaiwork_2.alter_psw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;

/**
 * Created by 35429 on 2017/6/12.
 */

public class AlterPswActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_psw);

        AlterPswFragment alterPswFragment = AlterPswFragment.newInstance();
        ActivityUtil.addFragment(getSupportFragmentManager(), alterPswFragment, R.id.alter_psw_content);
    }
}
