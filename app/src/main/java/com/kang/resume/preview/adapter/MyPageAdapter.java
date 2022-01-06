package com.kang.resume.preview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class MyPageAdapter extends PagerAdapter {

    private List<View> listViews;// content


    public MyPageAdapter(List<View> listViews) {// 构造函数
        // 初始化viewpager的时候给的一个页面
        this.listViews = listViews;
    }

    @Override
    public int getCount() {
        // 返回数量
        return listViews.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把 Object 强转为 View，然后将 view 从 ViewGroup 中清除
        container.removeView((View) object);
    }
    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {// 返回view对象
        try {
            ((ViewPager) arg0).addView(listViews.get(arg1 % listViews.size()), 0);
        } catch (Exception e) { }
        return listViews.get(arg1 % listViews.size());
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}
