package com.kang.resume.preview.adapter;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.base.ValueConfig;
import com.kang.resume.bean.EducationBean;
import com.kang.resume.bean.ProjectBean;
import com.kang.resume.bean.WorkExperienceBean;
import com.kang.resume.preview.pro.IAdapter;
import com.kang.resume.preview.pro.ITitle;
import com.kang.resume.preview.utils.TextEmptyUtil;
import com.kang.resume.utils.SwitchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class ExperiencesAdapter extends RecyclerView.Adapter implements IAdapter {
    public List list;
    public int size;
    public int color;
    public ITitle iTitle;

    public ExperiencesAdapter(List list, int color, int size, ITitle iTitle) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.size = size;
        this.color = color;
        this.iTitle = iTitle;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_experiences, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        Object bean = list.get(position);
        TextView tvTime;
        if (iTitle.getTimeWidth() != 0) {

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.tvTime0.getLayoutParams();
            lp.width = iTitle.getTimeWidth();
            vh.tvTime0.setLayoutParams(lp);

            vh.tvTime0.setVisibility(View.VISIBLE);
            vh.tvTime.setVisibility(View.GONE);
            vh.tvCompany.setGravity(Gravity.START);

            tvTime = vh.tvTime0;

            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) vh.tvTime0.getLayoutParams();
            lp2.topMargin = position == 0 ? 0 : size;
            vh.tvTime0.setLayoutParams(lp2);
        } else {
            switch (iTitle.getType()) {
                case 1:
                    vh.tvTime0.setVisibility(View.VISIBLE);
                    vh.tvTime.setVisibility(View.GONE);
                    vh.tvCompany.setGravity(Gravity.START);
                    tvTime = vh.tvTime;
                    break;
                default:
                    vh.tvTime.setVisibility(View.VISIBLE);
                    vh.tvTime0.setVisibility(View.GONE);
                    vh.tvCompany.setGravity(Gravity.CENTER);
                    tvTime = vh.tvTime;
                    break;
            }
        }

        String time = "";
        String company = "";
        String role = "";
        String detail = "";

        if (bean instanceof WorkExperienceBean) {
            time = ((WorkExperienceBean) bean).getStartTime() + ValueConfig.space + ((WorkExperienceBean) bean).getEndTime();
            company = ((WorkExperienceBean) bean).getCompany();
            role = ((WorkExperienceBean) bean).getPosition();
            detail = ((WorkExperienceBean) bean).getWorkContent();
        } else if (bean instanceof ProjectBean) {
            time = ((ProjectBean) bean).getStartTime() + ValueConfig.space + ((ProjectBean) bean).getEndTime();
            company = ((ProjectBean) bean).getCompany();
            role = ((ProjectBean) bean).getRole();
            detail = ((ProjectBean) bean).getDescription();

        } else if (bean instanceof EducationBean) {
            time = ((EducationBean) bean).getStartTime() + ValueConfig.space + ((EducationBean) bean).getEndTime();
            company = ((EducationBean) bean).getSchool();
            role = ((EducationBean) bean).getMajor();
            detail = ((EducationBean) bean).getExperience();
        }

        time = SwitchUtils.Companion.switchTime(time);

        switch (iTitle.getType()) {
            case 1:
                vh.tvCompany.setText(company);
                vh.tvPosition.setText(time);
                break;
            default:
                tvTime.setText(time);
                vh.tvCompany.setText(company);
                vh.tvPosition.setText(role);
                break;
        }
        vh.tvDetail.setText(detail);

        vh.tvTime0.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvCompany.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvPosition.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

//        vh.tvTime0.setTextColor(color);
//        vh.tvTime.setTextColor(color);
//        vh.tvCompany.setTextColor(color);
//        vh.tvPosition.setTextColor(color);
//        vh.tvDetail.setTextColor(Config.bodyColor);

        if (TextEmptyUtil.isEmpty(vh.tvDetail.getText().toString())) {
            vh.tvDetail.setVisibility(View.GONE);
        } else vh.tvDetail.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.tvDetail.getLayoutParams();
        lp.topMargin = size / 4;
        vh.tvDetail.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) vh.llItem.getLayoutParams();
        lp1.topMargin = position == 0 ? 0 : size;
        vh.llItem.setLayoutParams(lp1);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llItem;
        private TextView tvTime0;
        private TextView tvTime;
        private TextView tvCompany;
        private TextView tvPosition;
        private TextView tvDetail;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);

            llItem = rootView.findViewById(R.id.ll_item);
            tvTime0 = rootView.findViewById(R.id.tv_time0);
            tvTime = rootView.findViewById(R.id.tv_time);
            tvCompany = rootView.findViewById(R.id.tv_company);
            tvPosition = rootView.findViewById(R.id.tv_position);
            tvDetail = rootView.findViewById(R.id.tv_detail);
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
//        notifyDataSetChanged();
    }
}
