package com.kang.resume.preview.preview.XCRTemplate_pdf_29;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kang.resume.R;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.BaseInfoView;
import com.kang.resume.preview.pro.IInfo;

import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.GlideUtils;

public class XCRTemplate_pdf_29_info extends BaseInfoView  implements IInfo {
    public Context context;

    public int themeColor;

    private LinearLayout clInfo;

    private TextView cl_bg_1;
    private TextView cl_bg_2;

    private TextView tvName;
    private TextView tvAbstract;
    private TextView tvInfo;
    private TextView line;
    private ImageView ivAvatar;

    public XCRTemplate_pdf_29_info(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.pager_29_info, this, true);
        init();
    }

    public void init() {
        tvName = findViewById(R.id.tv_name);
        tvAbstract = findViewById(R.id.tv_abstract);
        clInfo = findViewById(R.id.cl_info);
        tvInfo = findViewById(R.id.tv_info);
        ivAvatar = findViewById(R.id.iv_right_avatar);
        line = findViewById(R.id.line);
        cl_bg_1 = findViewById(R.id.cl_bg_1);
        cl_bg_2 = findViewById(R.id.cl_bg_2);
    }

    @Override
    public void setColor(int color){
        themeColor = color;
        cl_bg_1.setBackgroundColor(color);
        cl_bg_2.setBackgroundColor(color);
    }

    @Override
    public void setData(ResumeInfoBean bean, int size) {
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX,size*2);
        tvName.setTextColor(Color.WHITE);

        tvAbstract.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        tvAbstract.setTextColor(Color.WHITE);

        tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        //填入数据
        BaseInfoBean infoBean = bean.getBaseInfo();
        tvName.setText(infoBean.getName());
//        tvAbstract.setText(infoBean.intro);

        tvInfo.setText(getInfo(bean));

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) ivAvatar.getLayoutParams();
        lp.width = size * 6;
        lp.height = (int)(size * 6 *  Config.avatarRatio);
        lp.rightMargin = size * 3;
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

        //头像位置
        int textSize = Config.textSize;
        int avatarSize = textSize * 6;
        ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams)clInfo.getLayoutParams();
        lp1.rightMargin = size * 12;
        clInfo.setLayoutParams(lp1);

        //姓名
        LinearLayout.LayoutParams tvNameLp = (LinearLayout.LayoutParams)tvName.getLayoutParams();
        tvNameLp.leftMargin = Config.A4Padding;
        tvNameLp.topMargin = textSize*1;
        tvName.setLayoutParams(tvNameLp);

        //说明
        LinearLayout.LayoutParams abstractLp = (LinearLayout.LayoutParams)tvAbstract.getLayoutParams();
        abstractLp.leftMargin = Config.A4Padding;
        abstractLp.topMargin = tvNameLp.bottomMargin;
        tvAbstract.setLayoutParams(abstractLp);

        //个人信息
        ConstraintLayout.LayoutParams infoLp = (ConstraintLayout.LayoutParams)tvInfo.getLayoutParams();
        infoLp.leftMargin = Config.A4Padding;
        infoLp.rightMargin = Config.A4Padding+avatarSize+textSize*2;
        tvInfo.setLayoutParams(infoLp);


        ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams)cl_bg_2.getLayoutParams();
        lp3.height = size/2;
        cl_bg_2.setLayoutParams(lp3);

        ConstraintLayout.LayoutParams lp4 = (ConstraintLayout.LayoutParams)line.getLayoutParams();
        lp4.height = size*2;
        line.setLayoutParams(lp4);

    }

}
