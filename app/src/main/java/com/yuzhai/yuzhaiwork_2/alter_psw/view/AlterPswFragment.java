package com.yuzhai.yuzhaiwork_2.alter_psw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.alter_psw.contact.AlterPswContact;

/**
 * Created by 35429 on 2017/6/12.
 */

public class AlterPswFragment extends Fragment implements AlterPswContact.View {
    public static AlterPswFragment newInstance() {
        Bundle args = new Bundle();
        AlterPswFragment fragment = new AlterPswFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alter_psw, container, false);
    }

    @Override
    public void setPresenter(AlterPswContact.Presenter presenter) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
