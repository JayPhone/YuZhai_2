package com.yuzhai.yuzhaiwork_2.resume.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.resume.presenter.PublishResumePresenter;

/**
 * Created by 35429 on 2017/6/9.
 */

public class PublishResumeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_resume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.resume_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PublishResumeFragment publishResumeFragment = PublishResumeFragment.newInstance();
        ActivityUtil.addFragment(getSupportFragmentManager(), publishResumeFragment, R.id.publish_resume_content);

        new PublishResumePresenter(publishResumeFragment);
    }
}
