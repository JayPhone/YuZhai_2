package com.yuzhai.yuzhaiwork_2.resume.presenter;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RegexUtil;
import com.yuzhai.yuzhaiwork_2.resume.bean.PublishResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.bean.QueryResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.contact.PublishResumeContact;
import com.yuzhai.yuzhaiwork_2.resume.model.IPublishResumeModel;
import com.yuzhai.yuzhaiwork_2.resume.model.PublishResumeRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.resume.request.PublishResumeRequest;
import com.yuzhai.yuzhaiwork_2.resume.view.PublishResumeFragment;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/6/9.
 */

public class PublishResumePresenter implements PublishResumeContact.Presenter {
    private static final String TAG = "PublishResumePresenter";
    private WeakReference<PublishResumeContact.View> mPublishResumeView;
    private IPublishResumeModel mPublishResumeModel = new PublishResumeRemoteRepertory();

    public PublishResumePresenter(PublishResumeFragment publishResumeFragment) {
        this.mPublishResumeView = new WeakReference<PublishResumeContact.View>(publishResumeFragment);
        mPublishResumeView.get().setPresenter(this);
    }

    @Override
    public void start() {
        getSexSpinnerData();
        getTypeSpinnerData();
        getEducationSpinnerData();
        //查询是否已经发布简历
        sendQueryResumeRequest();
    }

    @Override
    public void clear() {
        if (mPublishResumeView.get() != null) {
            mPublishResumeView.clear();
        }
    }

    @Override
    public boolean checkResumeInput(PublishResumeRequest publishResumeRequest) {
        if (publishResumeRequest != null) {
            if (publishResumeRequest.getName().isEmpty()) {
                mPublishResumeView.get().showToast("姓名不能为空");
                return false;
            } else if (publishResumeRequest.getSex().equals("请选择您的性别")) {
                mPublishResumeView.get().showToast("请选择您的性别");
                return false;
            } else if (publishResumeRequest.getType().equals("请选择投放板块")) {
                mPublishResumeView.get().showToast("请选择投放板块");
                return false;
            } else if (publishResumeRequest.getEducation().equals("请选择您的学历")) {
                mPublishResumeView.get().showToast("请选择您的学历");
                return false;
            } else if (!RegexUtil.regexPhone(publishResumeRequest.getTel())) {
                mPublishResumeView.get().showToast("联系电话格式不正确");
                return false;
            } else if (publishResumeRequest.getEducationalExperience().isEmpty()) {
                mPublishResumeView.get().showToast("教育经历不能为空");
                return false;
            } else if (publishResumeRequest.getSkill().isEmpty()) {
                mPublishResumeView.get().showToast("专业技能不能为空");
                return false;
            } else if (publishResumeRequest.getWorkExperience().isEmpty()) {
                mPublishResumeView.get().showToast("工作经验不能为空");
                return false;
            } else if (publishResumeRequest.getSelfEvaluation().isEmpty()) {
                mPublishResumeView.get().showToast("自我评价不能为空");
                return false;
            } else {
                //发送请求
                sendPublishResumeRequest(publishResumeRequest);
                Log.d(TAG, publishResumeRequest.toString());
                return true;
            }
        }
        return false;
    }

    @Override
    public void getSexSpinnerData() {
        mPublishResumeModel.getSexSpinnerData(new BaseModel.OnRequestResponse<List<Map<String, String>>>() {
            @Override
            public void onSuccess(List<Map<String, String>> maps) {
                if (mPublishResumeView.get().isActive()) {
                    mPublishResumeView.get().setSexSpinnerData(maps);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void getTypeSpinnerData() {
        mPublishResumeModel.getTypeSpinnerData(new BaseModel.OnRequestResponse<List<Map<String, String>>>() {
            @Override
            public void onSuccess(List<Map<String, String>> maps) {
                if (mPublishResumeView.get().isActive()) {
                    mPublishResumeView.get().setTypeSpinnerData(maps);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void getEducationSpinnerData() {
        mPublishResumeModel.getEducationSpinnerData(new BaseModel.OnRequestResponse<List<Map<String, String>>>() {
            @Override
            public void onSuccess(List<Map<String, String>> maps) {
                if (mPublishResumeView.get().isActive()) {
                    mPublishResumeView.get().setEducationSpinnerData(maps);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void sendPublishResumeRequest(PublishResumeRequest publishResumeRequest) {
        mPublishResumeModel.sendPublishResumeRequest(publishResumeRequest, new BaseModel.OnRequestResponse<PublishResumeResponse>() {
            @Override
            public void onSuccess(PublishResumeResponse publishResumeResponse) {
                if (mPublishResumeView.get().isActive()) {
                    if (publishResumeResponse.getCode().equals("1")) {
                        mPublishResumeView.get().showToast("简历投递成功");
                    } else if (publishResumeResponse.getCode().equals("-1")) {
                        mPublishResumeView.get().showToast("简历投递失败，请稍后重试");
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mPublishResumeView.get().isActive()) {
                    mPublishResumeView.get().showToast("服务器异常");
                }
            }
        });
    }

    @Override
    public void sendQueryResumeRequest() {
        mPublishResumeModel.sendQueryResumeRequest(new BaseModel.OnRequestResponse<QueryResumeResponse>() {
            @Override
            public void onSuccess(QueryResumeResponse queryResumeResponse) {
                if (mPublishResumeView.get().isActive()) {
                    mPublishResumeView.get().setResumeData(queryResumeResponse);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mPublishResumeView.get().isActive()) {
                    mPublishResumeView.get().showToast("服务器异常");
                }
            }
        });
    }
}
