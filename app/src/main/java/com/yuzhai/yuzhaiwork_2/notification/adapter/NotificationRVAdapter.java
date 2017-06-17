package com.yuzhai.yuzhaiwork_2.notification.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;

import java.util.List;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.NotificationRVHolder> {
    public final static String PUBLISH = "publish";
    public final static String RECEIVE = "receive";
    public final static String APPLY = "apply";
    private List<NotificationDB> mNotificationDBs;
    private OnNotificationClickListener mOnNotificationClickListener;

    public void setOnNotificationClickListener(OnNotificationClickListener onNotificationClickListener) {
        mOnNotificationClickListener = onNotificationClickListener;
    }

    public NotificationRVAdapter(List<NotificationDB> notificationDBs) {
        this.mNotificationDBs = notificationDBs;
    }

    @Override
    public NotificationRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationRVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(NotificationRVHolder holder, final int position) {
        if (mNotificationDBs.get(position).getType().equals(PUBLISH)) {
            holder.notificationImage.setImageResource(R.drawable.published);
        } else if (mNotificationDBs.get(position).getType().equals(RECEIVE)) {
            holder.notificationImage.setImageResource(R.drawable.accepted);
        } else if (mNotificationDBs.get(position).getType().equals(APPLY)) {
            holder.notificationImage.setImageResource(R.drawable.applied);
        }
        holder.notificationTitle.setText(mNotificationDBs.get(position).getTitle());
        holder.timeText.setText(mNotificationDBs.get(position).getDate());
        holder.notificationMessage.setText(mNotificationDBs.get(position).getDescription());
        holder.wrapCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNotificationClickListener != null) {
                    mOnNotificationClickListener.onNotificationClick(
                            mNotificationDBs.get(position).getType(),
                            mNotificationDBs.get(position).getOrder_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotificationDBs.size();
    }

    class NotificationRVHolder extends RecyclerView.ViewHolder {
        TextView timeText;
        TextView notificationTitle;
        ImageView notificationImage;
        TextView notificationMessage;
        CardView wrapCardView;

        public NotificationRVHolder(View itemView) {
            super(itemView);
            timeText = (TextView) itemView.findViewById(R.id.time_text);
            notificationTitle = (TextView) itemView.findViewById(R.id.notification_title);
            notificationImage = (ImageView) itemView.findViewById(R.id.notification_image);
            notificationMessage = (TextView) itemView.findViewById(R.id.notification_text);
            wrapCardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public interface OnNotificationClickListener {
        void onNotificationClick(String type, String orderId);
    }
}
