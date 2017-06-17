package com.yuzhai.yuzhaiwork_2.main.presenter;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RegexUtil;
import com.yuzhai.yuzhaiwork_2.main.bean.PublishResponse;
import com.yuzhai.yuzhaiwork_2.main.contact.PublishContact;
import com.yuzhai.yuzhaiwork_2.main.model.IPublishModel;
import com.yuzhai.yuzhaiwork_2.main.model.PublishRemoteModel;
import com.yuzhai.yuzhaiwork_2.main.request.PublishRequest;
import com.yuzhai.yuzhaiwork_2.main.view.PublishFragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.R.attr.path;

/**
 * Created by 35429 on 2017/5/25.
 */

public class PublishPresenter implements PublishContact.Presenter {
    private WeakReference<PublishContact.View> mPublishView;
    private IPublishModel mPublishModel = new PublishRemoteModel();
    private List<File> mImageFiles;

    public PublishPresenter(PublishFragment publishFragment) {
        mPublishView = new WeakReference<PublishContact.View>(publishFragment);
        mPublishView.get().setPresenter(this);
    }

    @Override
    public void start() {
        getTypeSpinnerData();
        getLimitSpinnerData();
    }

    @Override
    public void clear() {
        if (mPublishView.get() != null) {
            mPublishView.clear();
        }
    }

    @Override
    public boolean checkPublishInput(PublishRequest publishRequest) {
        if (publishRequest != null) {
            if (publishRequest.getTitle().isEmpty()) {
                mPublishView.get().showToast("需求标题不能为空");
                return false;
            } else if (publishRequest.getDescription().isEmpty()) {
                mPublishView.get().showToast("需求内容不能为空");
                return false;
            } else if (publishRequest.getType().equals("请选择项目类型")) {
                mPublishView.get().showToast("请选择项目类型");
                return false;
            } else if (publishRequest.getDeadline().equals("请选择预期时长")) {
                mPublishView.get().showToast("请选择预期时长");
                return false;
            } else if (!RegexUtil.regexPhone(publishRequest.getTel())) {
                mPublishView.get().showToast("手机号码格式不正确");
                return false;
            } else if (publishRequest.getReward().isEmpty()) {
                mPublishView.get().showToast("项目金额不能为空");
                return false;
            } else if (publishRequest.getFiles().size() == 0) {
                mPublishView.get().showToast("至少需要上传一张图片");
                return false;
            } else {
                compressImages(publishRequest);
                return true;
            }
        }

        return false;
    }

    @Override
    public void getTypeSpinnerData() {
        mPublishModel.getTypeSpinnerData(new BaseModel.OnRequestResponse<List<Map<String, String>>>() {
            @Override
            public void onSuccess(List<Map<String, String>> maps) {
                if (mPublishView.get().isActive()) {
                    mPublishView.get().setTypeSpinnerData(maps);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void getLimitSpinnerData() {
        mPublishModel.getLimitSpinnerData(new BaseModel.OnRequestResponse<List<Map<String, String>>>() {
            @Override
            public void onSuccess(List<Map<String, String>> maps) {
                if (mPublishView.get().isActive()) {
                    mPublishView.get().setLimitSpinnerData(maps);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void sendPublishOrderRequest(PublishRequest publishRequest) {
        mPublishModel.sendPublishOrderRequest(publishRequest, new BaseModel.OnRequestResponse<PublishResponse>() {
            @Override
            public void onSuccess(PublishResponse publishResponse) {
                if (mPublishView.get().isActive()) {
                    mPublishView.get().hideProgressDialog();
                    mPublishView.get().setPublishResult(publishResponse);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mPublishView.get().isActive()) {
                    mPublishView.get().showToast("服务器异常");
                    mPublishView.get().hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void compressImages(final PublishRequest publishRequest) {
        mImageFiles = new ArrayList<>();
        for (String path : (List<String>) publishRequest.getFiles()) {
            Luban.get(CustomApplication.getInstance().getApplicationContext())
                    .load(new File(path))            //传人要压缩的图片
                    .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            //显示进度对话框
                            mPublishView.get().showProgressDialog("图片正在压缩，请稍后");
                        }

                        @Override
                        public void onSuccess(File file) {
                            mImageFiles.add(file);
                            if (publishRequest.getFiles().size() == mImageFiles.size()) {
                                publishRequest.setFiles(mImageFiles);
                                //发送请求
                                sendPublishOrderRequest(publishRequest);
                                //隐藏进度对话框
                                mPublishView.get().hideProgressDialog();
                                mPublishView.get().showProgressDialog("正在发布,请稍后");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mPublishView.get().showToast("压缩出错，请稍后重试");
                        }
                    }).launch();    //启动压缩
        }
    }
}
