package com.yuzhai.yuzhaiwork_2.personal_order.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.CategoryTypeUtil;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAcceptedDatas;

import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public class AcceptedRVAdapter extends RecyclerView.Adapter<AcceptedRVAdapter.AcceptedRVHolder> {
    private OrderAcceptedDatas mOrderAppliedDatas;
    private OnAcceptedItemClickListener mOnAcceptedItemClickListener;

    public AcceptedRVAdapter(OrderAcceptedDatas mOrderAppliedDatas) {
        this.mOrderAppliedDatas = mOrderAppliedDatas;
    }

    public void setOnAcceptedItemClickListener(OnAcceptedItemClickListener onAcceptedItemClickListener) {
        this.mOnAcceptedItemClickListener = onAcceptedItemClickListener;
    }

    @Override
    public AcceptedRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AcceptedRVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_accepted_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AcceptedRVHolder holder, final int position) {
        holder.mStatusText.setText(mOrderAppliedDatas.getOrders().get(position).getStatus());
        holder.mOrderIdText.setText(mOrderAppliedDatas.getOrders().get(position).getOrder_id());
        holder.mDateText.setText(mOrderAppliedDatas.getOrders().get(position).getDate());
        holder.mTitleText.setText(mOrderAppliedDatas.getOrders().get(position).getTitle());
        holder.mDeadlineText.setText(mOrderAppliedDatas.getOrders().get(position).getDeadline());
        holder.mRewardText.setText(mOrderAppliedDatas.getOrders().get(position).getReward());
        holder.mTypeImage.setImageResource(CategoryTypeUtil
                .getTypeImageRes(mOrderAppliedDatas.getOrders().get(position).getType()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAcceptedItemClickListener != null) {
                    mOnAcceptedItemClickListener.onAcceptedItemClick(
                            mOrderAppliedDatas.getOrders().get(position).getOrder_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderAppliedDatas.getOrders().size();
    }

    class AcceptedRVHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mStatusText;
        TextView mOrderIdText;
        TextView mDateText;
        TextView mTitleText;
        TextView mDeadlineText;
        TextView mRewardText;
        ImageView mTypeImage;

        AcceptedRVHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mStatusText = (TextView) itemView.findViewById(R.id.status);
            mOrderIdText = (TextView) itemView.findViewById(R.id.order_id);
            mDateText = (TextView) itemView.findViewById(R.id.date);
            mTitleText = (TextView) itemView.findViewById(R.id.title);
            mDeadlineText = (TextView) itemView.findViewById(R.id.deadline);
            mRewardText = (TextView) itemView.findViewById(R.id.price);
            mTypeImage = (ImageView) itemView.findViewById(R.id.type_image);
        }
    }

    public interface OnAcceptedItemClickListener {
        void onAcceptedItemClick(String orderId);
    }
}
