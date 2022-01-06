package com.kang.resume.preview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kang.resume.R;
import com.kang.resume.preview.adapter.TabAdapter;
import com.vondear.rxtool.RxDeviceTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class ThemeFragment extends BaseFragment {
    private ViewPager viewPager;
    private TextView tvCover;
    private TextView tvColor;

    @Override
    protected int initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.theme_fragment;
    }

    @Override
    protected void initView(View view) {
        tvCover = view.findViewById(R.id.tv_cover);
        tvColor = view.findViewById(R.id.tv_color);
        viewPager = view.findViewById(R.id.view_pager);
    }

    @Override
    protected void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CoverFragment());
        fragmentList.add(new ColorFragment());


        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)viewPager.getLayoutParams();
        lp.height = RxDeviceTool.getScreenWidth(activity) / 4;
        viewPager.setLayoutParams(lp);

        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), fragmentList, null);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    tvCover.setTextColor(ContextCompat.getColor(activity,R.color.primary));
                    tvColor.setTextColor(ContextCompat.getColor(activity,R.color.black66));
                }else {
                    tvColor.setTextColor(ContextCompat.getColor(activity,R.color.primary));
                    tvCover.setTextColor(ContextCompat.getColor(activity,R.color.black66));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvCover.setOnClickListener((view) -> {
           viewPager.setCurrentItem(0, true);
        });
        tvColor.setOnClickListener((view) -> {
            viewPager.setCurrentItem(1, true);
        });
    }
}
