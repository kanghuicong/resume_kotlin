package com.kang.resume.preview.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kang.resume.BuildConfig;

/**
 * 类描述：基础fragment
 */
public abstract class BaseFragment extends Fragment {

    protected Activity activity;
    private View view;


    protected abstract int initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initView(View view);

    protected abstract void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(activity, initLayout(inflater, container, savedInstanceState), null);
            initView(view);


            if(BuildConfig.DEBUG){
                init(inflater, container, savedInstanceState);
            }else {
                try {
                    init(inflater, container, savedInstanceState);
                } catch (Exception e) {

                }
            }
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }
}
