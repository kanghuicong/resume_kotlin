package com.kang.resume.preview.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.FontsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 * author:kanghuicong
 */
public class InfoAdapter1 extends RecyclerView.Adapter {
    public Map<String, String> map = new HashMap<>();
    List<String> keyList = new ArrayList<>();
    List<String> valueList = new ArrayList<>();
    int size;

    public InfoAdapter1(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        vh.tvTitle.setText(keyList.get(position));
        vh.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        LinearLayout.LayoutParams lp0 = (LinearLayout.LayoutParams) vh.tvTitle.getLayoutParams();
        lp0.topMargin = size / 2;
        vh.tvTitle.setLayoutParams(lp0);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.tvContent.getLayoutParams();
        lp.topMargin = size / 2;
        vh.tvContent.setText(valueList.get(position));
        vh.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        vh.tvContent.setLayoutParams(lp);

        FontsUtils.setRegularFont(vh.tvTitle);
        FontsUtils.setRegularFont(vh.tvContent);

        vh.tvTitle.setTextColor(Config.infoColor);
        vh.tvContent.setTextColor(Config.infoColor);
    }

    public void setData(Map<String, String> map) {
        this.map = map;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return map.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            tvTitle = rootView.findViewById(R.id.tv_title);
            tvContent = rootView.findViewById(R.id.tv_content);

        }
    }


}
