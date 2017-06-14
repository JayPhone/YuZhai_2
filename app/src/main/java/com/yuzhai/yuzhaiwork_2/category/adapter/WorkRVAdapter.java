package com.yuzhai.yuzhaiwork_2.category.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;

/**
 * Created by 35429 on 2017/6/3.
 */

public class WorkRVAdapter extends RecyclerView.Adapter<WorkRVAdapter.WorkRVHolder> {
    private static final String TAG = "WorkRVAdapter";
    private WorkDatas mWorkDatas;
    private OnWorkItemClickListener mOnWorkItemClickListener;

    public WorkRVAdapter(WorkDatas mWorkDatas) {
        this.mWorkDatas = mWorkDatas;
    }

    @Override
    public WorkRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorkRVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_work_item_layout, parent, false));
    }

    public void setOnWorkItemClickListener(OnWorkItemClickListener onWorkItemClickListener) {
        this.mOnWorkItemClickListener = onWorkItemClickListener;
    }

    @Override
    public void onBindViewHolder(WorkRVHolder holder, final int position) {
        holder.mTitle.setText(mWorkDatas.getOrders().get(position).getTitle());
        holder.mDate.setText(mWorkDatas.getOrders().get(position).getDate());
        holder.mDeadline.setText(mWorkDatas.getOrders().get(position).getDeadline());
        holder.mReward.setText(mWorkDatas.getOrders().get(position).getReward());
        if (mWorkDatas.getOrders().get(position).getPicture() != null
                && mWorkDatas.getOrders().get(position).getPicture().size() > 0) {
            Glide.with(CustomApplication.getInstance().getApplicationContext())
                    .load(IPConfig.IMAGE_PREFIX + mWorkDatas.getOrders().get(position)
                            .getPicture().get(0).getImage())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(holder.mImage);
        } else {
            holder.mImage.setImageResource(R.drawable.default_image);
        }
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnWorkItemClickListener != null) {
                    mOnWorkItemClickListener.onWorkItemClick(mWorkDatas.getOrders().get(position).getOrder_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWorkDatas.getOrders().size();
    }

    class WorkRVHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        RelativeLayout mWrapLayout;
        ImageView mImage;
        TextView mTitle;
        TextView mDate;
        TextView mDeadline;
        TextView mReward;

        public WorkRVHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mWrapLayout = (RelativeLayout) itemView.findViewById(R.id.wrap_layout);
            mTitle = (TextView) itemView.findViewById(R.id.title_text);
            mDate = (TextView) itemView.findViewById(R.id.date_content);
            mDeadline = (TextView) itemView.findViewById(R.id.limit_content);
            mReward = (TextView) itemView.findViewById(R.id.price_content);
            mImage = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public interface OnWorkItemClickListener {
        void onWorkItemClick(String orderId);
    }
}
