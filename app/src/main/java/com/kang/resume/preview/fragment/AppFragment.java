package com.kang.resume.preview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.R;
import com.kang.resume.preview.adapter.AppAdapter;
import com.vondear.rxtool.RxDeviceTool;

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class AppFragment extends BaseFragment {
    private RecyclerView rvApp;
    private List<ResumeInfoBean> list;

    @Override
    protected int initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.app_fragment;
    }

    @Override
    protected void initView(View view) {
        rvApp = view.findViewById(R.id.rv_app);
    }

    @Override
    protected void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list = (List<ResumeInfoBean>) getArguments().getSerializable("list");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setClick(i == 0);
        }


        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rvApp.setLayoutManager(mLayoutManager);

        AppAdapter appAdapter = new AppAdapter(activity, list);
        rvApp.setAdapter(appAdapter);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rvApp.getLayoutParams();
        lp.height = RxDeviceTool.getScreenWidth(activity) / 4;
        rvApp.setLayoutParams(lp);
    }


}
