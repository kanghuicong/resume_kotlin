package com.kang.resume.preview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.preview.bean.AddCoverEvent;
import com.kang.resume.preview.custom.CenterLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class CoverFragment extends BaseFragment {
    private RecyclerView rvCover;

    @Override
    protected int initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.cover_fragment;
    }

    @Override
    protected void initView(View view) {
        rvCover = view.findViewById(R.id.rv_cover);
    }

    @Override
    protected void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rvCover.setLayoutManager(new CenterLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        CoverAdapter coverAdapter = new CoverAdapter(activity, 11);
        rvCover.setAdapter(coverAdapter);

    }

    public class CoverAdapter extends RecyclerView.Adapter {
        List<Boolean> clickList = new ArrayList();
        Context context;
        int maxNum;

        public CoverAdapter(Context context, int maxNum) {
            this.context = context;
            this.maxNum = maxNum;
            for (int i = 0; i < maxNum; i++) {
                clickList.add(i==0);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cover, parent, false);

            LinearLayout llItem = v.findViewById(R.id.ll_item);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llItem.getLayoutParams();
            lp.width = lp.height * 210 / 297;
            llItem.setLayoutParams(lp);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;

            int mipmapId = getResources().getIdentifier("cover_preimage_" + position, "mipmap", context.getPackageName());
            int coverId = getResources().getIdentifier("cover_element_" + position, "mipmap", context.getPackageName());

            vh.ivResume.setBackground(ContextCompat.getDrawable(context, mipmapId));

            if (clickList.get(position)) {
                vh.llItem.setBackground(ContextCompat.getDrawable(context, R.color.primary));
            } else {
                vh.llItem.setBackground(ContextCompat.getDrawable(context, R.color.transparent));
            }

            vh.llItem.setOnClickListener((view -> {
                for (int i = 0; i < maxNum; i++) {
                    clickList.set(i,i==position);
                }

                rvCover.smoothScrollToPosition(position);
                notifyDataSetChanged();
                EventBus.getDefault().post(new AddCoverEvent(position,coverId));

            }));
        }

        @Override
        public int getItemCount() {
            return maxNum;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivResume;
            LinearLayout llItem;

            private ViewHolder(View convertView) {
                super(convertView);

                ivResume = convertView.findViewById(R.id.iv_resume);
                llItem = convertView.findViewById(R.id.ll_item);

            }

        }
    }



}
