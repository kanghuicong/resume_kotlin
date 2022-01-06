package com.kang.resume.preview.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.bean.JobIntentionBean;
import com.kang.resume.preview.pro.IAdapter;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.FontsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class PositionAdapter extends RecyclerView.Adapter implements IAdapter {
    List<JobIntentionBean> list;
    int color;
    int size;

    public PositionAdapter(List<JobIntentionBean> list, int color, int size) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.color = color;
        this.size = size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_position, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        JobIntentionBean bean = list.get(position);
        vh.tvPosition.setText(bean.getPosition());
        vh.tvAddress.setText(bean.getCity());
        vh.tvTime.setText(bean.getEntryTime());
        vh.tvMoney.setText(bean.getSalary());

        vh.ivPosition.setLayoutParams(getImgLayoutParams(vh.ivPosition));
        vh.ivAddress.setLayoutParams(getImgLayoutParams(vh.ivAddress));
        vh.ivTime.setLayoutParams(getImgLayoutParams(vh.ivTime));
        vh.ivMoney.setLayoutParams(getImgLayoutParams(vh.ivMoney));

        vh.ivPosition.setColorFilter(Config.titleHighColor);
        vh.ivAddress.setColorFilter(Config.titleHighColor);
        vh.ivTime.setColorFilter(Config.titleHighColor);
        vh.ivMoney.setColorFilter(Config.titleHighColor);

        FontsUtils.setRegularFont(vh.tvPosition);
        FontsUtils.setRegularFont(vh.tvTime);
        FontsUtils.setRegularFont(vh.tvAddress);
        FontsUtils.setRegularFont(vh.tvMoney);

        vh.tvTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvPosition.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvMoney.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        vh.tvTime.setTextColor(Config.titleHighColor);
        vh.tvPosition.setTextColor(Config.titleHighColor);
        vh.tvAddress.setTextColor(Config.titleHighColor);
        vh.tvMoney.setTextColor(Config.titleHighColor);


    }

    private LinearLayout.LayoutParams getImgLayoutParams(View view){
        LinearLayout.LayoutParams lp0 = (LinearLayout.LayoutParams)view.getLayoutParams();
        lp0.width = size*2;
        lp0.height = size*2;
        return lp0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void setColor(int color) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPosition;
        private TextView tvAddress;
        private TextView tvTime;
        private TextView tvMoney;
        private ImageView ivPosition;
        private ImageView ivAddress;
        private ImageView ivTime;
        private ImageView ivMoney;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);

            ivPosition = rootView.findViewById(R.id.iv_position);
            ivAddress = rootView.findViewById(R.id.iv_address);
            ivTime = rootView.findViewById(R.id.iv_time);
            ivMoney = rootView.findViewById(R.id.iv_money);

            tvPosition = rootView.findViewById(R.id.tv_position);
            tvAddress = rootView.findViewById(R.id.tv_address);
            tvTime = rootView.findViewById(R.id.tv_time);
            tvMoney = rootView.findViewById(R.id.tv_money);

        }
    }
}
