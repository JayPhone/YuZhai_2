package com.yuzhai.yuzhaiwork_2.base.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import com.yuzhai.yuzhaiwork_2.main.adapter.CategoryRVAdapter;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private CategoryRVAdapter mAdapter;

    public ItemTouchHelperCallback(CategoryRVAdapter adapter) {
        this.mAdapter = adapter;
    }

    /**
     * 设置RecyclerView支持拖动和滑动的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, 0);
    }

    /**
     * 当上下拖动的时候调用该方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 当左右滑动删除选项时调用该方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * 选中一项时调用该方法
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.i("change", "change");
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof OnItemStateChangeListener) {
                OnItemStateChangeListener listener = (OnItemStateChangeListener) viewHolder;
                listener.onItemSelected();
            }
        }
    }

    /**
     * 放开选中时调用该方法
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof OnItemStateChangeListener) {
            OnItemStateChangeListener listener = (OnItemStateChangeListener) viewHolder;
            listener.onItemClear();
        }
    }
}
