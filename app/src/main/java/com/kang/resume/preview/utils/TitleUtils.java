package com.kang.resume.preview.utils;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kang.resume.R;
import com.kang.resume.preview.custom.RadiusCardView;

/**
 * 类描述：
 * author:kanghuicong
 */
public class TitleUtils {

    public static int getIcon( String content) {
        switch (content) {
            case Config.POSITION:
                return R.mipmap.position_1;
            case Config.EDUCATION:
                return R.mipmap.education_1;
            case Config.JOB:
                return R.mipmap.job_1;
            case Config.PROJECT:
                return R.mipmap.project_1;
            case Config.SKILLS:
                return R.mipmap.skill_1;
            case Config.AWARD:
                return R.mipmap.award_1;
            case Config.INTEREST:
                return R.mipmap.interest_1;
            default:
                return R.mipmap.assessment_1;
        }
    }

    public static float iconMultiple = 1.8f;
    public static void setIcon(RadiusCardView cardView,ImageView ivIcon,int size){

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        lp.width = (int) (size * iconMultiple);
        lp.height = (int) (size * iconMultiple);
        cardView.setLayoutParams(lp);
        cardView.setAllRadius((float) (size * iconMultiple));

        ivIcon.setPadding(size / 3, size / 3, size / 3, size / 3);

    }
}
