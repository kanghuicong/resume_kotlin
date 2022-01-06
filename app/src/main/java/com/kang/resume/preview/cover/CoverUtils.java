package com.kang.resume.preview.cover;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.kang.resume.R;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.EducationBean;
import com.kang.resume.bean.JobIntentionBean;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.qiniu.QiniuUtils;
import com.kang.resume.preview.utils.TextEmptyUtil;

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class CoverUtils {
    private Context context;
    private int width;
    private int height;
    private int size;
    private int textSize;

    private String name;
    private String phone;
    private String position;
    private String school;
    private String email;
    private String address;
    private String avatar;

    public CoverUtils(Context context, int width, int height, int size) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.size = size;
        this.textSize = (int) (size * 1.5);
    }

    public View getCover(ResumeInfoBean resumeBean, int coverId, int coverPosition) {
        FrameLayout frameLayout = new FrameLayout(context);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        imageView.setBackground(ContextCompat.getDrawable(context, coverId));

        frameLayout.addView(imageView);

        //重置
        name = "";
        phone = "";
        school = "";
        email = "";
        position = "";
        address = "";
        avatar = "";

        BaseInfoBean infoBean = resumeBean.getBaseInfo();
        if (infoBean != null) {
            name = infoBean.getName();
            phone = infoBean.getPhone();
            email = infoBean.getEmail();
            address = infoBean.getAddress();
            avatar = QiniuUtils.downImg(infoBean.getAvatar());
        }

        List<EducationBean> educationList = resumeBean.getEducations();
        if (educationList != null && educationList.size() > 0) {
            EducationBean educationBean = educationList.get(0);
            school = educationBean.getSchool();

        }

        JobIntentionBean positionBean= resumeBean.getJobIntention();
            if (positionBean != null) {
                position = positionBean.getPosition();
        }


        View view = null;
        switch (coverPosition) {
            case 1:
                view = cover1();
                frameLayout.addView(view);

                FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) view.getLayoutParams();
                lp1.bottomMargin = size * 6;
                view.setLayoutParams(lp1);
                break;

            case 2:
                view = cover2();
                frameLayout.addView(view);

                FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) view.getLayoutParams();
                lp2.bottomMargin = size * 6;
                view.setLayoutParams(lp2);
                break;
            case 3:
                frameLayout.addView(cover3());
                break;
            case 4:
                frameLayout.addView(cover4());
                break;
            case 5:
                frameLayout.addView(cover5());
                break;
            case 6:
                break;
            case 7:
            case 8:
                frameLayout.addView(cover7());
                break;
            case 9:
                frameLayout.addView(cover9());
                break;
            case 10:
                frameLayout.addView(cover10());
                break;
        }


        return frameLayout;
    }

    public View cover1() {
        View view = View.inflate(context, R.layout.cover_1, null);

        LinearLayout layout1 = view.findViewById(R.id.layout1);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvSchool = view.findViewById(R.id.tv_school);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvSchool.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText(name);
        tvSchool.setText(school);
        tvPhone.setText(phone);
        tvEmail.setText(email);

        layout1.setPadding(0, 0, size * 4, 0);
        return view;
    }

    public View cover2() {
        View view = View.inflate(context, R.layout.cover_2, null);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvEmail.setText(email);

        return view;
    }

    public View cover3() {
        View view = View.inflate(context, R.layout.cover_3, null);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvSchool = view.findViewById(R.id.tv_school);
        TextView tvPosition = view.findViewById(R.id.tv_position);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        ImageView ivAvatar = view.findViewById(R.id.iv_avatar);
        LinearLayout layout1 = view.findViewById(R.id.layout1);
        LinearLayout layout = view.findViewById(R.id.layout);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 2);
        tvPosition.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvSchool.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText(name);
        tvPosition.setText("求职意向：" + position);
        tvSchool.setText("毕业院校：" + school);
        tvPhone.setText("联系电话：" + phone);
        tvEmail.setText("邮\u3000\u3000箱：" + email);

        setTopLayoutParams(tvPhone);
        setTopLayoutParams(tvEmail);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layout1.getLayoutParams();
        lp.rightMargin = size * 6;
        layout1.setLayoutParams(lp);

        ConstraintLayout.LayoutParams lp0 = (ConstraintLayout.LayoutParams) layout.getLayoutParams();
        lp0.rightMargin = size * 6;
        layout.setLayoutParams(lp0);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) ivAvatar.getLayoutParams();
        lp1.leftMargin = size * 4;
        lp1.width = size * 12;
        lp1.height = size * 12;
        ivAvatar.setLayoutParams(lp1);

        if (!TextEmptyUtil.isEmpty(avatar)) {
            RequestOptions requestOptions = RequestOptions.bitmapTransform(new CircleCrop());

            Glide.with(context)
                    .load(avatar)
                    .apply(requestOptions)
                    .into(ivAvatar);
        }
        return view;
    }

    public View cover4() {
        View view = View.inflate(context, R.layout.cover_4, null);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvSchool = view.findViewById(R.id.tv_school);
        TextView tvPosition = view.findViewById(R.id.tv_position);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        ImageView ivAvatar = view.findViewById(R.id.iv_avatar);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPosition.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvSchool.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText("姓\u3000\u3000名：" + name);
        tvPosition.setText("求职意向：" + position);
        tvSchool.setText("毕业院校：" + school);
        tvPhone.setText("电\u3000\u3000话：" + phone);
        tvEmail.setText("邮\u3000\u3000箱：" + email);

        setTopLayoutParams(tvPosition);
        setTopLayoutParams(tvSchool);
        setTopLayoutParams(tvPhone);
        setTopLayoutParams(tvEmail);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) ivAvatar.getLayoutParams();
        lp1.width = size * 15;
        lp1.height = size * 15;
        ivAvatar.setLayoutParams(lp1);

        if (!TextEmptyUtil.isEmpty(avatar)) {
            RequestOptions requestOptions = RequestOptions.bitmapTransform(new CircleCrop());
            Glide.with(context)
                    .load(avatar)
                    .apply(requestOptions)
                    .into(ivAvatar);
        }
        return view;
    }

    public View cover5() {
        View view = View.inflate(context, R.layout.cover_5, null);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvAddress = view.findViewById(R.id.tv_address);

        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout layout3 = view.findViewById(R.id.layout3);
        LinearLayout layout4 = view.findViewById(R.id.layout4);
        ImageView ivName = view.findViewById(R.id.iv_name);
        ImageView ivAddress = view.findViewById(R.id.iv_address);
        LinearLayout layout2 = view.findViewById(R.id.layout2);
        ImageView ivPhone = view.findViewById(R.id.iv_phone);
        ImageView ivEmail = view.findViewById(R.id.iv_email);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        ivName.setColorFilter(0xFFFFA902);
        ivAddress.setColorFilter(0xFFFFA902);
        ivPhone.setColorFilter(0xFFFFA902);
        ivEmail.setColorFilter(0xFFFFA902);

        setImgLayoutParams(ivName);
        setImgLayoutParams(ivAddress);
        setImgLayoutParams(ivPhone);
        setImgLayoutParams(ivEmail);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvEmail.setText(email);


        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) layout.getLayoutParams();
        lp1.leftMargin = size * 6;
        layout.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) layout2.getLayoutParams();
        lp2.leftMargin = size * 6;
        layout2.setLayoutParams(lp2);

        setTopLayoutParams(layout3);
        setTopLayoutParams(layout4);
        return view;
    }

    private void setImgLayoutParams(ImageView imageView) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        lp.width = size * 2;
        lp.height = size * 2;
        lp.rightMargin = size / 2;
        imageView.setLayoutParams(lp);
    }

    private void setTopLayoutParams(View view) {
        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp3.topMargin = size;
        view.setLayoutParams(lp3);
    }

    public View cover7() {
        View view = View.inflate(context, R.layout.cover_7, null);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText("姓名：" + name);
        tvPhone.setText("电话：" + phone);
        tvEmail.setText("邮箱：" + email);

        setTopLayoutParams(tvPhone);
        setTopLayoutParams(tvEmail);
        return view;
    }

    public View cover9() {
        View view = View.inflate(context, R.layout.cover_9, null);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        LinearLayout layout = view.findViewById(R.id.layout);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText("姓名：" + name);
        tvPhone.setText("电话：" + phone);
        tvEmail.setText("邮箱：" + email);

        setTopLayoutParams(tvPhone);
        setTopLayoutParams(tvEmail);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layout.getLayoutParams();
        lp.leftMargin = size * 6;
        layout.setLayoutParams(lp);
        return view;
    }

    public View cover10() {
        View view = View.inflate(context, R.layout.cover_10, null);

        LinearLayout layout1 = view.findViewById(R.id.layout1);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvSchool = view.findViewById(R.id.tv_school);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvSchool.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        tvName.setText(name);
        tvSchool.setText(school);
        tvPhone.setText(phone);
        tvEmail.setText(email);
        layout1.setPadding(0, 0, size * 4, 0);

        return view;
    }
}
