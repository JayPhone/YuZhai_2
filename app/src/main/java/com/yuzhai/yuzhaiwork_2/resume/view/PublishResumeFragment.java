package com.yuzhai.yuzhaiwork_2.resume.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.resume.bean.QueryResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.contact.PublishResumeContact;
import com.yuzhai.yuzhaiwork_2.resume.model.PublishResumeRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.resume.request.PublishResumeRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/6/9.
 */

public class PublishResumeFragment extends Fragment implements PublishResumeContact.View,
        View.OnClickListener {
    private static final String TAG = "PublishResumeFragment";

    public static PublishResumeFragment newInstance() {
        Bundle args = new Bundle();
        PublishResumeFragment fragment = new PublishResumeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private PublishResumeContact.Presenter mPresenter;
    private TextView mIsSendResumeText;
    private TextInputEditText mNameEdit;
    private Spinner mSexSpinner;
    private Spinner mTypeSpinner;
    private Spinner mEducationSpinner;
    private TextInputEditText mTelEdit;
    private TextInputEditText mEducationEdit;
    private TextInputEditText mSkillEdit;
    private TextInputEditText mWorkExperienceEdit;
    private TextInputEditText mSelfEvaluationEdit;
    private Button mSendResumeBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_publish_resume, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mIsSendResumeText = (TextView) root.findViewById(R.id.send_resume_finish);
            mNameEdit = (TextInputEditText) root.findViewById(R.id.name);
            mSexSpinner = (Spinner) root.findViewById(R.id.sex_spinner);
            mTypeSpinner = (Spinner) root.findViewById(R.id.type_spinner);
            mEducationSpinner = (Spinner) root.findViewById(R.id.education_spinner);
            mTelEdit = (TextInputEditText) root.findViewById(R.id.tel);
            mEducationEdit = (TextInputEditText) root.findViewById(R.id.educational);
            mSkillEdit = (TextInputEditText) root.findViewById(R.id.skill);
            mWorkExperienceEdit = (TextInputEditText) root.findViewById(R.id.work);
            mSelfEvaluationEdit = (TextInputEditText) root.findViewById(R.id.evaluation);
            mSendResumeBtn = (Button) root.findViewById(R.id.send_resume_btn);
            mSendResumeBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_resume_btn:
                String sex = mSexSpinner.getSelectedItem().toString();
                String type = mTypeSpinner.getSelectedItem().toString();
                String education = mEducationSpinner.getSelectedItem().toString();
                Log.d(TAG, sex);
                Log.d(TAG, type);
                Log.d(TAG, education);

                //校验输入的数据，校验成功后发布
                mPresenter.checkResumeInput(new PublishResumeRequest(
                        mNameEdit.getText().toString(),
                        sex.substring(6, sex.length() - 1),
                        type.substring(6, type.length() - 1),
                        education.substring(6, education.length() - 1),
                        mTelEdit.getText().toString(),
                        mEducationEdit.getText().toString(),
                        mSkillEdit.getText().toString(),
                        mWorkExperienceEdit.getText().toString(),
                        mSelfEvaluationEdit.getText().toString()
                ));
                break;
        }
    }

    @Override
    public void setPresenter(PublishResumeContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setSexSpinnerData(List<Map<String, String>> sexSpinnerData) {
        mSexSpinner.setAdapter(new SimpleAdapter(
                getActivity().getApplicationContext(),
                sexSpinnerData, R.layout.resume_spinner_item_layout,
                new String[]{PublishResumeRemoteRepertory.DATA},
                new int[]{R.id.spinner_item}));
    }

    @Override
    public void setTypeSpinnerData(List<Map<String, String>> typeSpinnerData) {
        mTypeSpinner.setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),
                typeSpinnerData, R.layout.resume_spinner_item_layout,
                new String[]{PublishResumeRemoteRepertory.DATA},
                new int[]{R.id.spinner_item}));
    }

    @Override
    public void setEducationSpinnerData(List<Map<String, String>> educationSpinnerData) {
        mEducationSpinner.setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),
                educationSpinnerData, R.layout.resume_spinner_item_layout,
                new String[]{PublishResumeRemoteRepertory.DATA},
                new int[]{R.id.spinner_item}));
    }

    @Override
    public void setResumeData(QueryResumeResponse queryResumeResponse) {
        //判断是否有数据
        if (queryResumeResponse.getDetail_resume().getAvatar() != null) {
            mNameEdit.setText(queryResumeResponse.getDetail_resume().getName());
            for (int i = 0; i < mSexSpinner.getAdapter().getCount(); i++) {
                String item = mSexSpinner.getAdapter().getItem(i).toString();
                if (item.substring(6, item.length() - 1).equals(queryResumeResponse.getDetail_resume().getSex())) {
                    mSexSpinner.setSelection(i);
                }
            }

            for (int i = 0; i < mTypeSpinner.getAdapter().getCount(); i++) {
                String item = mTypeSpinner.getAdapter().getItem(i).toString();
                if (item.substring(6, item.length() - 1).equals(queryResumeResponse.getDetail_resume().getModule())) {
                    mTypeSpinner.setSelection(i);
                }
            }

            for (int i = 0; i < mEducationSpinner.getAdapter().getCount(); i++) {
                String item = mEducationSpinner.getAdapter().getItem(i).toString();
                if (item.substring(6, item.length() - 1).equals(queryResumeResponse.getDetail_resume().getEducation())) {
                    mEducationSpinner.setSelection(i);
                }
            }
            mTelEdit.setText(queryResumeResponse.getDetail_resume().getContact_number());
            mEducationEdit.setText(queryResumeResponse.getDetail_resume().getEducation_experience());
            mSkillEdit.setText(queryResumeResponse.getDetail_resume().getSkill());
            mWorkExperienceEdit.setText(queryResumeResponse.getDetail_resume().getWork_experience());
            mSelfEvaluationEdit.setText(queryResumeResponse.getDetail_resume().getSelf_evaluation());

            mSendResumeBtn.setText("修改简历");
        } else {
            mIsSendResumeText.setVisibility(View.GONE);
        }
    }
}
