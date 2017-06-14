package com.yuzhai.yuzhaiwork_2.collection.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.collection.bean.CollectionData;
import com.yuzhai.yuzhaiwork_2.collection.model.CollectionModel;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/5/29.
 */

public class CollectionRVAdapter extends RecyclerView.Adapter<CollectionRVAdapter.CollectionRVHolder> {
    private List<CollectionData> mCollectionData;

    public CollectionRVAdapter(List<CollectionData> mCollectionData) {
        this.mCollectionData = mCollectionData;
    }

    @Override
    public CollectionRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectionRVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item_layout, parent, false));
    }

    public void setCollectionData(List<CollectionData> collectionData) {
        mCollectionData = collectionData;
    }

    @Override
    public void onBindViewHolder(CollectionRVHolder holder, int position) {
        holder.mImage.setImageResource(mCollectionData.get(position).getImage());
        holder.mType.setText(mCollectionData.get(position).getType());
        holder.mTitle.setText(mCollectionData.get(position).getTitle());
        holder.mDate.setText(mCollectionData.get(position).getDate());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCollectionData.size();
    }

    class CollectionRVHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mImage;
        TextView mType;
        TextView mTitle;
        TextView mDate;

        public CollectionRVHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mImage = (ImageView) itemView.findViewById(R.id.image_view);
            mType = (TextView) itemView.findViewById(R.id.type);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
