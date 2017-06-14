package com.yuzhai.yuzhaiwork_2.category.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.base.view.CircleImageView;
import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;

/**
 * Created by 35429 on 2017/6/3.
 */

public class ResumeRVAdapter extends RecyclerView.Adapter<ResumeRVAdapter.ResumeRVHolder> {
    private static final String TAG = "ResumeRVAdapter";
    private ResumeDatas mResumeDatas;
    private OnResumeClickListener mOnResumeClickListener;

    public ResumeRVAdapter(ResumeDatas mResumeDatas) {
        this.mResumeDatas = mResumeDatas;
    }

    public void setOnResumeClickListener(OnResumeClickListener onResumeClickListener) {
        this.mOnResumeClickListener = onResumeClickListener;
    }

    @Override
    public ResumeRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResumeRVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_resume_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ResumeRVHolder holder, final int position) {
        holder.mNameText.setText(mResumeDatas.getResumes().get(position).getName());
        holder.mSexText.setText(mResumeDatas.getResumes().get(position).getSex());
        holder.mEducationText.setText(mResumeDatas.getResumes().get(position).getEducation());
        holder.mTelText.setText(mResumeDatas.getResumes().get(position).getContact_number());
        Log.d(TAG, IPConfig.IMAGE_PREFIX + mResumeDatas.getResumes().get(position).getAvatar());
        Glide.with(CustomApplication.getInstance().getApplicationContext())
                .load(IPConfig.IMAGE_PREFIX + mResumeDatas.getResumes().get(position).getAvatar())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.mHeaderImage);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnResumeClickListener != null) {
                    mOnResumeClickListener.onResumeClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResumeDatas.getResumes().size();
    }

    class ResumeRVHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        CircleImageView mHeaderImage;
        TextView mNameText;
        TextView mSexText;
        TextView mEducationText;
        TextView mTelText;

        ResumeRVHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mHeaderImage = (CircleImageView) itemView.findViewById(R.id.header_image);
            mNameText = (TextView) itemView.findViewById(R.id.name);
            mSexText = (TextView) itemView.findViewById(R.id.sex);
            mEducationText = (TextView) itemView.findViewById(R.id.education);
            mTelText = (TextView) itemView.findViewById(R.id.status);
        }
    }

    public interface OnResumeClickListener {
        void onResumeClick(int position);
    }
}
