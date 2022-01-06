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

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class WrapAdapter extends RecyclerView.Adapter {
    List<String> list;
    int size;

    public WrapAdapter(List<String> list, int size) {
        this.list = list;
        this.size = size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_warp, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        vh.tvContent.setText(list.get(position));
        vh.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.tvContent.getLayoutParams();
        lp.topMargin = size / 2;
        vh.tvContent.setLayoutParams(lp);

        FontsUtils.setRegularFont(vh.tvContent);
        vh.tvContent.setTextColor(Config.bodyColor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;


        private ViewHolder(View convertView) {
            super(convertView);

            tvContent = convertView.findViewById(R.id.tv_content);

        }

    }
}
