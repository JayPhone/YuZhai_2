package com.yuzhai.yuzhaiwork_2.personal_order.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.CategoryTypeUtil;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by 35429 on 2017/6/1.
 */

public class PublishedRVAdapter extends RecyclerView.Adapter<PublishedRVAdapter.PublishedRVHolder> {
    private static final String TAG = "PublishedRVAdapter";
    private OrderPublishedDatas mOrderPublishedDatas;
    private OnPublishedItemClickListener mOnPublishedItemClickListener;

    public void setOnPublishedItemClickListener(OnPublishedItemClickListener onPublishedItemClickListener) {
        this.mOnPublishedItemClickListener = onPublishedItemClickListener;
    }

    public PublishedRVAdapter(OrderPublishedDatas mOrderPublishedDatas) {
        this.mOrderPublishedDatas = mOrderPublishedDatas;
    }

    @Override
    public PublishedRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublishedRVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_published_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(PublishedRVHolder holder, final int position) {
        holder.mStatusText.setText(mOrderPublishedDatas.getOrders().get(position).getStatus());
        holder.mOrderIdText.setText(mOrderPublishedDatas.getOrders().get(position).getOrder_id());
        holder.mDateText.setText(mOrderPublishedDatas.getOrders().get(position).getDate());
        holder.mTitleText.setText(mOrderPublishedDatas.getOrders().get(position).getTitle());
        holder.mDeadlineText.setText(mOrderPublishedDatas.getOrders().get(position).getDeadline());
        holder.mRewardText.setText(mOrderPublishedDatas.getOrders().get(position).getReward());
        holder.mTypeImage.setImageResource(CategoryTypeUtil
                .getTypeImageRes(mOrderPublishedDatas.getOrders().get(position).getType()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPublishedItemClickListener != null) {
                    mOnPublishedItemClickListener.onPublishedItemClick(
                            mOrderPublishedDatas.getOrders().get(position).getOrder_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderPublishedDatas.getOrders().size();
    }

    class PublishedRVHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mStatusText;
        TextView mOrderIdText;
        TextView mDateText;
        TextView mTitleText;
        TextView mDeadlineText;
        TextView mRewardText;
        ImageView mTypeImage;

        PublishedRVHolder(View itemView) {
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

    public interface OnPublishedItemClickListener {
        void onPublishedItemClick(String orderId);
    }
}
