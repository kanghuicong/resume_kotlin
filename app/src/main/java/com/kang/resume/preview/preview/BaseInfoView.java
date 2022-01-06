package com.kang.resume.preview.preview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.R;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.HobbyBean;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.adapter.TagsAdapter;
import com.kang.resume.preview.pro.IInfo;
import com.kang.resume.preview.qiniu.QiniuUtils;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.GlideUtils;
import com.kang.resume.preview.utils.TextEmptyUtil;
import com.kang.resume.utils.SwitchUtils;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public abstract class BaseInfoView extends ConstraintLayout implements IInfo {


    public Context context;
    private ImageView ivLeftAvatar;
    private ImageView ivRightAvatar;
    private ImageView ivTopAvatar;
    private ConstraintLayout layout;
    private LinearLayout llInfo;
    private TextView tvName;
    private TextView tvAbstract;
    private TextView tvInfo;
    public TagFlowLayout tagInterest;
    public String info = "";
    private TagsAdapter tagsAdapter;


    public BaseInfoView(Context context) {
        super(context);
        this.context = context;
    }

    public BaseInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BaseInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    //默认布局
    public void init() {
        LayoutInflater.from(context).inflate(R.layout.pager_6_info, this, true);

        ivLeftAvatar = findViewById(R.id.iv_left_avatar);
        ivRightAvatar = findViewById(R.id.iv_right_avatar);
        ivTopAvatar = findViewById(R.id.iv_top_avatar);
        llInfo = findViewById(R.id.cl_info);
        layout = findViewById(R.id.layout);
        tvName = findViewById(R.id.tv_name);
        tvAbstract = findViewById(R.id.tv_abstract);
        tvInfo = findViewById(R.id.tv_info);
        tagInterest = findViewById(R.id.tag_interest);

        //设置字体大小
        tvName.setTextColor(Config.bigColor);

        tvAbstract.setTextColor(Config.titleHighColor);

        tvInfo.setTextColor(Config.titleHighColor);

    }


    @Override
    public void setData(ResumeInfoBean resumeBean, int size) {
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 2);
        tvAbstract.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        //填入数据
        BaseInfoBean infoBean = resumeBean.getBaseInfo();
        tvName.setText(infoBean.getName());
//        tvAbstract.setText(infoBean.intro);


        ImageView ivAvatar;
        switch (avatarGravity()) {
            case Gravity.END:
                ivLeftAvatar.setVisibility(GONE);
                ivTopAvatar.setVisibility(GONE);
                ivAvatar = ivRightAvatar;
                break;
            case Gravity.TOP:
                ivLeftAvatar.setVisibility(GONE);
                ivRightAvatar.setVisibility(GONE);
                ivAvatar = ivTopAvatar;
                break;
            default:
                ivRightAvatar.setVisibility(GONE);
                ivTopAvatar.setVisibility(GONE);
                ivAvatar = ivLeftAvatar;
                break;
        }

        GlideUtils.glideAvatar(context,QiniuUtils.downImg(infoBean.getAvatar()),ivAvatar);

//        Glide.with(context)
//                .load(QiniuUtils.downImg(infoBean.getAvatar()))
//                .apply(new RequestOptions()
//                        .placeholder(R.mipmap.icon_header_default)
//                        .error(R.mipmap.icon_header_default)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL))
//                .into(ivAvatar);

        tvInfo.setText(getInfo(resumeBean));

        List<String> tagList = new ArrayList<>();
        List<HobbyBean> hobbyBeanList = resumeBean.getHobbies();
        if (hobbyBeanList != null) {
            for (HobbyBean hobby : hobbyBeanList) {
                tagList.add(hobby.getHobby());
            }
            tagsAdapter = new TagsAdapter(context, tagList, size, Config.defaultColor);
            tagInterest.setAdapter(tagsAdapter);
        }
        _setLayout(size, tagList);

    }

    @Override
    public boolean hasInterest() {
        return false;
    }

    @Override
    public int avatarGravity() {
        return Gravity.START;
    }

    private void _setLayout(int size, List<String> tagList) {
        layout.setPadding(0, 0, 0, size * 2);

        LayoutParams lp1 = (LayoutParams) llInfo.getLayoutParams();
        ImageView ivAvatar;
        switch (avatarGravity()) {
            case Gravity.END:
                lp1.rightMargin = size * 3;
                ivAvatar = ivRightAvatar;
                break;
            case Gravity.TOP:
                lp1.bottomMargin = size;
                ivAvatar = ivTopAvatar;
                break;
            default:
                lp1.leftMargin = size * 3;
                ivAvatar = ivLeftAvatar;
                break;
        }

        llInfo.setLayoutParams(lp1);


        int avatarSize;
        //是否包含兴趣爱好
        if (hasInterest()) {
            if (tagList == null || tagList.size() == 0) {
                avatarSize = size * 6;
            } else {
                avatarSize = size * 8;
            }
            LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) tagInterest.getLayoutParams();
            lp5.topMargin = size;
            tagInterest.setLayoutParams(lp5);
        } else {
            avatarSize = size * 6;
        }
        LayoutParams lp = (LayoutParams) ivAvatar.getLayoutParams();
        lp.width = avatarSize;
        lp.height = (int) (avatarSize * Config.avatarRatio);
        ivAvatar.setLayoutParams(lp);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) tvName.getLayoutParams();
        lp2.topMargin = size;
        tvName.setLayoutParams(lp2);

        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) tvAbstract.getLayoutParams();
        lp3.topMargin = size / 2;
        tvAbstract.setLayoutParams(lp3);

        LinearLayout.LayoutParams lp4 = (LinearLayout.LayoutParams) tvInfo.getLayoutParams();
        lp4.topMargin = size / 4;
        tvInfo.setLayoutParams(lp4);

        //内容是否居中
        if (isCenterInfo()) {
            llInfo.setGravity(Gravity.CENTER);
            tvAbstract.setGravity(Gravity.CENTER);
            tvInfo.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) tvInfo.getLayoutParams();
            lp5.leftMargin = size * 6;
            lp5.rightMargin = size * 4;
            tvInfo.setLayoutParams(lp5);

            LinearLayout.LayoutParams lp6 = (LinearLayout.LayoutParams) tvAbstract.getLayoutParams();
            lp6.leftMargin = size * 6;
            lp6.rightMargin = size * 4;
            tvAbstract.setLayoutParams(lp6);

            LinearLayout.LayoutParams lp7 = (LinearLayout.LayoutParams) tvName.getLayoutParams();
            lp6.leftMargin = size * 6;
            lp6.rightMargin = size * 4;
            tvName.setLayoutParams(lp7);
        }


    }

    @Override
    public void setColor(int color) {
        if (hasInterest()) {
            if (tagsAdapter != null) tagsAdapter.update(Config.textSize, color);
        }
    }


    public void getInfo(String data) {
        if (!TextEmptyUtil.isEmpty(data)) {
            info = info + data + " | ";
        }
    }

    public String getInfo(ResumeInfoBean resumeBean) {

        //填入数据
        BaseInfoBean infoBean = resumeBean.getBaseInfo();

        info = "";
        getInfo(infoBean.getGender());
        getInfo(SwitchUtils.Companion.switchYear(infoBean.getBirthday()) + "岁");
        getInfo(infoBean.getNation());
        getInfo(infoBean.getPoliticalStatus());
        if (TextEmptyUtil.isEmpty(infoBean.getHeight()) &&
                !TextEmptyUtil.isEmpty(infoBean.getWeight())) {
            //无身高有体重
            getInfo(infoBean.getWeight() + "kg");
        } else if (!TextEmptyUtil.isEmpty(infoBean.getHeight()) &&
                TextEmptyUtil.isEmpty(infoBean.getWeight())) {
            //有身高无体重
            getInfo(infoBean.getHeight() + "cm");
        } else if (!TextEmptyUtil.isEmpty(infoBean.getHeight()) &&
                !TextEmptyUtil.isEmpty(infoBean.getWeight())) {
            //有身高体重
            getInfo(infoBean.getHeight() + "cm/" + infoBean.getWeight() + "kg");
        }
        getInfo(infoBean.getProvince());
        getInfo(infoBean.getStartWorkTime());
        getInfo(infoBean.getPhone());
        getInfo(infoBean.getEmail());


        info = info.substring(0, info.length() - 2);
        return info;
    }


    @Override
    public boolean isCenterInfo() {
        return false;
    }
}
