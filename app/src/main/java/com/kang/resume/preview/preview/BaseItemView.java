package com.kang.resume.preview.preview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kang.resume.R;
import com.kang.resume.base.ValueConfig;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.ResumeInfoBean;

;
import com.kang.resume.preview.bean.TemplateBean;
import com.kang.resume.preview.adapter.ExperiencesAdapter;
import com.kang.resume.preview.adapter.InfoAdapter1;
import com.kang.resume.preview.pro.IAdapter;
import com.kang.resume.preview.pro.IItem;
import com.kang.resume.preview.pro.ITitle;

import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.GlideUtils;
import com.kang.resume.preview.utils.TextEmptyUtil;
import com.kang.resume.utils.SwitchUtils;
import com.vondear.rxtool.view.RxToast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * author:kanghuicong
 */
public class BaseItemView extends LinearLayout implements IItem {
    private ITitle iTitle;
    private TextView tvName;
    private TextView tvIntroduce;
    private RecyclerView rv;
    private Context context;

    public BaseItemView(Context context, TemplateBean templateBean) {
        super(context);
        this.context = context;

        //动态实例化对应的item小标题类
        try {
            Class toolClass = Class.forName(ValueConfig.classPageName + templateBean.getClassName() + "." + templateBean.getClassName() + "_TitleView");
            Constructor constructor = toolClass.getConstructor(Context.class);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(context);
            iTitle = (ITitle) obj;

            constructor.setAccessible(false);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            RxToast.normal("发生错误");
            return;
        }

        rv = new RecyclerView(context);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
        rv.setLayoutManager(new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        this.setOrientation(VERTICAL);
        this.addView((View) iTitle);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(HORIZONTAL);

        LinearLayout leftLayout = new LinearLayout(context);
        leftLayout.setOrientation(VERTICAL);
        leftLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        leftLayout.addView(rv);

        layout.addView(leftLayout);
        this.addView(layout);
    }

    public BaseItemView setData(String title, int size, RecyclerView.Adapter adapter) {
        iTitle.setTitleStr(title);
        iTitle.setTitleSize(size);

        if (iTitle.getTimeWidth() != 0 && adapter instanceof ExperiencesAdapter) {
            rv.setPadding(0, size, iTitle.getRvRightMargin(), size);
        } else {
            rv.setPadding(iTitle.getRvLeftMargin(), size, iTitle.getRvRightMargin(), size);
        }

        rv.setAdapter(adapter);
        return this;
    }

    Map<String, String> map = new HashMap<>();

    public BaseItemView setInfoData(String title, ResumeInfoBean resumeBean, int size) {
        iTitle.setTitleStr(title);
        iTitle.setTitleSize(size);

        rv.setPadding(iTitle.getRvLeftMargin(), size, iTitle.getRvRightMargin(), size);

        InfoAdapter1 infoAdapter = new InfoAdapter1(size);
        rv.setAdapter(infoAdapter);

        map = new HashMap<>();
        BaseInfoBean infoBean = resumeBean.getBaseInfo();
        if (infoBean == null) return this;


        if (iTitle.isNameView()) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(VERTICAL);

            tvName = new TextView(context);
            tvName.setTextColor(Config.bigColor);
            tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            layout.addView(tvName);

            tvIntroduce = new TextView(context);
            tvIntroduce.setTextColor(Config.infoColor);
            layout.addView(tvIntroduce);
            ((LinearLayout) ((LinearLayout) (this.getChildAt(1))).getChildAt(0)).addView(layout, 0);

            LayoutParams lp = (LayoutParams) tvName.getLayoutParams();
            lp.topMargin = size;
            lp.bottomMargin = size / 2;
            lp.leftMargin = iTitle.getRvLeftMargin();
            tvName.setLayoutParams(lp);
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 2);
            tvName.setText(infoBean.getName());

            LayoutParams lp1 = (LayoutParams) tvIntroduce.getLayoutParams();
            lp1.bottomMargin = size / 2;
            lp1.leftMargin = iTitle.getRvLeftMargin();

            tvIntroduce.setLayoutParams(lp1);
            tvIntroduce.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//            tvIntroduce.setText(infoBean.intro);

//            if (TextEmptyUtil.isEmpty(infoBean.intro)) {
                tvIntroduce.setVisibility(GONE);
//            } else tvIntroduce.setVisibility(VISIBLE);
        } else {
            getPersonalInfo("姓\u3000\u3000名：", infoBean.getName());
        }
        getPersonalInfo("手机号码：", infoBean.getPhone());
        getPersonalInfo("性\u3000\u3000别：", infoBean.getGender());
        getPersonalInfo("邮\u3000\u3000箱：", infoBean.getEmail());
        getPersonalInfo("出生日期：", infoBean.getBirthday());
        getPersonalInfo("工作经验：", SwitchUtils.switchYear(infoBean.getStartWorkTime())+"年");
        getPersonalInfo("住\u3000\u3000址：", infoBean.getAddress());
        getPersonalInfo("婚姻状况：", infoBean.getMarryStatus());
        getPersonalInfo("政治面貌：", infoBean.getPoliticalStatus());
        getPersonalInfo("民\u3000\u3000族：", infoBean.getNation());
        getPersonalInfo("籍\u3000\u3000贯：", infoBean.getProvince());
        getPersonalInfo("身\u3000\u3000高：", infoBean.getHeight()+"cm");
        getPersonalInfo("体\u3000\u3000重：", infoBean.getWeight()+"kg");

        infoAdapter.setData(map);

        ImageView ivAvatar = new ImageView(context);
        ((LinearLayout) (this.getChildAt(1))).addView(ivAvatar);
        LayoutParams lp = (LayoutParams) ivAvatar.getLayoutParams();
        lp.width = size * 6;
        lp.height = (int)(size * 6 *  Config.avatarRatio);
        lp.topMargin = size;
        lp.leftMargin = size*2;
        ivAvatar.setLayoutParams(lp);

        if (infoBean.getAvatar()!=null && infoBean.getAvatar()!="") {
            ivAvatar.setVisibility(VISIBLE);
            Glide.with(context)
                    .load(infoBean.getAvatar())
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.icon_header_default)
                            .error(R.mipmap.icon_header_default)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(ivAvatar);
        }else{
            ivAvatar.setVisibility(GONE);
        }
        return this;
    }

    public void getPersonalInfo(String str, String data) {
        if (!TextEmptyUtil.isEmpty(data)) {
            map.put(str, data);
        }
    }

    public BaseItemView setGridManager(int count) {
        rv.post(new Runnable() {
            @Override
            public void run() {
                rv.setLayoutManager(new GridLayoutManager(context, count) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
            }
        });
        return this;
    }

    @Override
    public void setColor(int color) {
        iTitle.setColor(color);
        if (rv.getAdapter() instanceof IAdapter) {
            ((IAdapter) rv.getAdapter()).setColor(color);
        }
    }


}