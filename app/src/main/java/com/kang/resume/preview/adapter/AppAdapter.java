package com.kang.resume.preview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.bean.ChoosePreviewEvent;
import com.kang.resume.preview.utils.TextEmptyUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class AppAdapter extends RecyclerView.Adapter {
    List<ResumeInfoBean> list = new ArrayList();
    Context context;

    public AppAdapter(Context context, List<ResumeInfoBean> list) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        ResumeInfoBean resumeBean = list.get(position);

        if (TextEmptyUtil.isEmpty(resumeBean.getResumeName())){
            vh.tvName.setText("简历"+position);
        }else {
            vh.tvName.setText(resumeBean.getResumeName());
        }


        if (resumeBean.isClick()) {
            vh.ivResume.setBackground(ContextCompat.getDrawable(context,R.mipmap.icon_resume_default_select));
            vh.tvName.setTextColor(ContextCompat.getColor(context,R.color.primary));
            vh.llItem.setBackground(ContextCompat.getDrawable(context,R.color.backgroundColor));
        }else {
            vh.ivResume.setBackground(ContextCompat.getDrawable(context,R.mipmap.icon_resume_default_unselect));
            vh.tvName.setTextColor(ContextCompat.getColor(context,R.color.black66));
            vh.llItem.setBackground(ContextCompat.getDrawable(context,R.color.transparent));
        }

        vh.llItem.setOnClickListener((view -> {
            for (int i=0;i<list.size();i++){
                if (i==position) list.get(i).setClick(true);
                else list.get(i).setClick(false);
            }
            EventBus.getDefault().post(new ChoosePreviewEvent(position));
            notifyDataSetChanged();
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivResume;
        LinearLayout llItem;

        private ViewHolder(View convertView) {
            super(convertView);

            tvName = convertView.findViewById(R.id.tv_name);
            ivResume = convertView.findViewById(R.id.iv_resume);
            llItem = convertView.findViewById(R.id.ll_item);

        }

    }
}
