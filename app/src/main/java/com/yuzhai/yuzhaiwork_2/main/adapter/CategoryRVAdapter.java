package com.yuzhai.yuzhaiwork_2.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.view.recycler.OnItemTouchListener;
import com.yuzhai.yuzhaiwork_2.main.bean.CategoryData;

import java.util.Collections;
import java.util.List;

/**
 * Created by 35429 on 2017/5/29.
 */

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.CategoryVH> implements OnItemTouchListener {
    private List<CategoryData> mCategoryData;
    private OnCategoryClickListener mOnCategoryClickListener;

    public CategoryRVAdapter(List<CategoryData> mCategoryData) {
        this.mCategoryData = mCategoryData;
    }

    public void setOnCategoryClickListenter(OnCategoryClickListener onCategoryClickListenter) {
        this.mOnCategoryClickListener = onCategoryClickListenter;
    }

    @Override
    public CategoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryVH(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.home_category_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final CategoryVH holder, int position) {
        holder.mImageView.setBackgroundResource(mCategoryData.get(position).getCategoryImageId());
        holder.mTextView.setText(mCategoryData.get(position).getCategoryText());
        holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCategoryClickListener != null) {
                    mOnCategoryClickListener.onCategoryClick(holder.mTextView.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryData.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCategoryData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    class CategoryVH extends RecyclerView.ViewHolder {
        LinearLayout mFrameLayout;
        ImageView mImageView;
        TextView mTextView;

        CategoryVH(View itemView) {
            super(itemView);
            mFrameLayout = (LinearLayout) itemView.findViewById(R.id.warp_layout);
            mImageView = (ImageView) itemView.findViewById(R.id.category_image);
            mTextView = (TextView) itemView.findViewById(R.id.category_text);
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(String type);
    }

}
