package com.kang.resume.preview.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.bean.SkillBean;
import com.kang.resume.preview.custom.ProgressView;
import com.kang.resume.preview.pro.IAdapter;
import com.kang.resume.preview.pro.ITitle;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.FontsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class AngleAdapter extends RecyclerView.Adapter implements IAdapter {
    List<SkillBean> list;
    int color;
    int size;
    ITitle iTitle;

    public AngleAdapter(List<SkillBean> list, int color, int size, ITitle iTitle) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.color = color;
        this.size = size;
        this.iTitle = iTitle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (iTitle.getSkillType() == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_angle_1, parent, false);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bar, parent, false);
            return new ViewBarHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SkillBean bean = list.get(position);

        switch (iTitle.getSkillType()) {
            case 0:

                ViewHolder vh = (ViewHolder) holder;

                LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(size * 6, size * 6);
                lp0.topMargin = size / 2;
                vh.roundView.setLayoutParams(lp0);
                vh.roundView.setAngle(bean.getProficiencyValue());


                vh.roundView.setColor(color);
                vh.roundView.setSize(size);

                vh.tvTitle.setText(bean.getSkillName());
                vh.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.tvTitle.getLayoutParams();
                lp.topMargin = size / 2;
                vh.tvTitle.setLayoutParams(lp);

                FontsUtils.setRegularFont(vh.tvTitle);
                vh.tvTitle.setTextColor(Config.infoColor);
                break;
            case 1:
                ViewBarHolder barVh = (ViewBarHolder) holder;

                ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) barVh.lineBlew.getLayoutParams();
                lp2.topMargin = size / 2;
                lp2.width = size * 8;
                lp2.height = size / 3 * 2;
                barVh.lineBlew.setLayoutParams(lp2);

                ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams) barVh.line.getLayoutParams();
                lp3.width = (int) (size * 8 * bean.getProficiencyValue());
                lp3.height = size / 3 * 2;
                barVh.line.setLayoutParams(lp3);

                barVh.line.setBackgroundColor(color);
                barVh.tvTitle.setText(bean.getSkillName());
                barVh.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

                FontsUtils.setRegularFont(barVh.tvTitle);
                barVh.tvTitle.setTextColor(Config.infoColor);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ProgressView roundView;
        private TextView tvTitle;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            roundView = rootView.findViewById(R.id.round_view);
            tvTitle = rootView.findViewById(R.id.tv_title);

        }
    }

    public static class ViewBarHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView lineBlew;
        private TextView line;

        public ViewBarHolder(@NonNull View rootView) {
            super(rootView);
            tvTitle = rootView.findViewById(R.id.tv_title);
            lineBlew = rootView.findViewById(R.id.line_blew);
            line = rootView.findViewById(R.id.line);

        }
    }


}
