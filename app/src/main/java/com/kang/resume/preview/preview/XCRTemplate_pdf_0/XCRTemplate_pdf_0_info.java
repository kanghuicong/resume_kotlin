package com.kang.resume.preview.preview.XCRTemplate_pdf_0;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
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

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_0_info extends BaseInfoView implements IInfo {

    private ImageView ivAvatar;
    private ConstraintLayout clInfo;
    private TextView tvName;
    private TextView hintResume;
    private TextView tvAbstract;
    private TextView tvInfo;
    private TextView line1;
    private TextView line2;
    private XCRTemplate_pdf_0_View_1 view1;

    public XCRTemplate_pdf_0_info(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pager_0_info, this, true);

        ivAvatar = findViewById(R.id.iv_avatar);
        clInfo = findViewById(R.id.cl_info);
        tvName = findViewById(R.id.tv_name);
        hintResume = findViewById(R.id.hint_resume);
        tvAbstract = findViewById(R.id.tv_abstract);
        tvInfo = findViewById(R.id.tv_info);
        view1 = findViewById(R.id.view_1);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);

    }

    @Override
    public void setColor(int color) {
        view1.setColor(color);
        line1.setBackgroundColor(color);
        line2.setBackgroundColor(color);
        hintResume.setBackgroundColor(color);
    }

    @Override
    public void setData(ResumeInfoBean resumeBean, int size) {
        view1.setColor(Config.defaultColor);
        line1.setBackgroundColor(Config.defaultColor);
        line2.setBackgroundColor(Config.defaultColor);


        //设置字体大小
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 2);
        tvName.setTextColor(Config.bigColor);
        hintResume.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 1.6f);
        tvAbstract.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        tvAbstract.setTextColor(Config.titleHighColor);
        tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        tvInfo.setTextColor(Config.titleHighColor);
        //填入数据
        BaseInfoBean infoBean = resumeBean.getBaseInfo();


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

        tvName.setText(infoBean.getName());
//        tvAbstract.setText(infoBean.get);

        tvInfo.setText(getInfo(resumeBean));

        //控制大小
        ConstraintLayout.LayoutParams lp0 = (ConstraintLayout.LayoutParams) view1.getLayoutParams();
        lp0.height = (int) (size * 0.8f);
        lp0.topMargin = size * 2;
        lp0.bottomMargin = size * 2;
        view1.setLayoutParams(lp0);

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) ivAvatar.getLayoutParams();
        lp.width = size * 6;
        lp.height = (int)(size * 6 *  Config.avatarRatio);
        lp.leftMargin = size * 3;
        ivAvatar.setLayoutParams(lp);

        ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) clInfo.getLayoutParams();
        lp1.leftMargin = size * 3;
        lp1.rightMargin = size * 5;
        clInfo.setLayoutParams(lp1);

        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) hintResume.getLayoutParams();
        lp2.rightMargin = size / 2;
        hintResume.setPadding(size * 4, size / 2, size * 4, size / 2);
        hintResume.setLayoutParams(lp2);

        ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams) line2.getLayoutParams();
        lp3.width = size / 3;
        line2.setLayoutParams(lp3);

        ConstraintLayout.LayoutParams lp4 = (ConstraintLayout.LayoutParams) line1.getLayoutParams();
        lp4.width = size / 3;
        lp4.rightMargin = size / 2;
        line1.setLayoutParams(lp4);

        ConstraintLayout.LayoutParams lp5 = (ConstraintLayout.LayoutParams) tvAbstract.getLayoutParams();
        lp5.topMargin = size;
        tvAbstract.setLayoutParams(lp5);

    }

}
