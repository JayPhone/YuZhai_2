package com.yuzhai.yuzhaiwork_2.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzhai.yuzhaiwork_2.R;

/**
 * Created by 35429 on 2017/5/25.
 */

public class ContactFragment extends Fragment {
    public static ContactFragment newInstance() {

        Bundle args = new Bundle();

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer);

        View root = getView();
        if (root != null) {
            Toolbar toolbar = (Toolbar) getView().findViewById(R.id.contact_toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawerLayout.openDrawer(Gravity.START);
                }
            });
        }
    }
}
