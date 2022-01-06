package com.kang.resume.preview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class HobbiesAdapter extends RecyclerView.Adapter {
    List<String> lists;
    int size;
    Context context;

    public HobbiesAdapter(Context context, List<String> list, int size) {
        this.context = context;
        this.size = size;
        this.lists = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hobbies, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        TagsAdapter tagsAdapter = new TagsAdapter(context, lists, size, -1);
        vh.tags.setAdapter(tagsAdapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TagFlowLayout tags;

        private ViewHolder(View convertView) {
            super(convertView);

            tags = convertView.findViewById(R.id.tags);

        }

    }
}
