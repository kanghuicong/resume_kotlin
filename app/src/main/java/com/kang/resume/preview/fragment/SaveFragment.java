package com.kang.resume.preview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kang.resume.R;
import com.kang.resume.preview.bean.SaveImgEvent;
import com.kang.resume.preview.bean.SavePdfEvent;
import com.vondear.rxtool.RxDeviceTool;

import org.greenrobot.eventbus.EventBus;

/**
 * 类描述：
 * author:kanghuicong
 */
public class SaveFragment extends BaseFragment{
    private LinearLayout savePdf;
    private LinearLayout saveImg;
    private LinearLayout llSave;

    @Override
    protected int initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.save_fragment;
    }

    @Override
    protected void initView(View view) {
        savePdf = view.findViewById(R.id.save_pdf);
        saveImg = view.findViewById(R.id.save_img);
        llSave = view.findViewById(R.id.ll_save);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)llSave.getLayoutParams();
        lp.height = RxDeviceTool.getScreenWidth(activity) / 4;
        llSave.setLayoutParams(lp);
    }

    @Override
    protected void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        savePdf.setOnClickListener((v -> {
            EventBus.getDefault().post(new SavePdfEvent());
        }));

        saveImg.setOnClickListener((v -> {
            EventBus.getDefault().post(new SaveImgEvent());
        }));
    }
}
