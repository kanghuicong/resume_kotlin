package com.kang.resume.preview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.resume.R;
import com.kang.resume.preview.bean.ChooseColorEvent;
import com.kang.resume.preview.custom.CircleView;
import com.kang.resume.preview.utils.Config;
import com.vondear.rxtool.RxDeviceTool;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class ColorFragment extends BaseFragment {
    RecyclerView rvColor;

    @Override
    protected int initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.color_fragment;
    }

    @Override
    protected void initView(View view) {
        rvColor = view.findViewById(R.id.rv_color);
    }

    @Override
    protected void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ColorAdapter adapter = new ColorAdapter();

        rvColor.setLayoutManager(new GridLayoutManager(activity, 12));
        rvColor.setAdapter(adapter);


    }

    public class ColorAdapter extends RecyclerView.Adapter {

        private List<Integer> colorList = new ArrayList<>();
        private List<Boolean> selectList = new ArrayList<>();
        private int height;

        public ColorAdapter() {
            height = RxDeviceTool.getScreenWidth(activity) / 12;

            colorList.add(0xFF9A02E8);
            colorList.add(0xFFE8CB02);
            colorList.add(0xFFE8C502);
            colorList.add(0xFFBF0F00);
            colorList.add(0xFFF8C002);
            colorList.add(0xFF91D04F);
            colorList.add(0xFF41B0F1);
            colorList.add(0xFF7030A0);
            colorList.add(0xFFDE5FF5);
            colorList.add(0xFF959595);
            colorList.add(0xFF434343);
            colorList.add(0xFFE39393);
            colorList.add(0xFF023EFF);
            colorList.add(0xFFBCFF02);
            colorList.add(0xFFFFA902);
            colorList.add(0xFFF61700);
            colorList.add(0xFFFAFF00);
            colorList.add(0xFF25AF50);
            colorList.add(0xFF2F70C1);
            colorList.add(0xFF102060);
            colorList.add(0xFF754392);
            colorList.add(0xFF8827B9);
            colorList.add(0xFF86BDFF);
            colorList.add(0xFFB3CFF0);
            colorList.add(0xFF89FFB3);
            colorList.add(0xFFDE89FF);
            colorList.add(0xFFFF89C1);
            colorList.add(0xFFD56666);
            colorList.add(0xFFFFE77E);
            colorList.add(0xFF007324);
            colorList.add(0xFF0052B8);
            colorList.add(0xFF0021A4);
            colorList.add(0xFF3A1E1E);
            colorList.add(0xFF654B4B);
            colorList.add(0xFF000000);
            colorList.add(Config.defaultColor);

            for (int i = 0; i < colorList.size(); i++) {
//                selectList.add(i == colorList.size() - 1);
                selectList.add(false);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;

            vh.circleView.setColor(colorList.get(position));

            if (selectList.get(position)) {
                vh.ivSelected.setVisibility(View.VISIBLE);
            } else {
                vh.ivSelected.setVisibility(View.GONE);
            }

            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) vh.circleView.getLayoutParams();
            lp.height = height;
            lp.width = height;
            vh.circleView.setLayoutParams(lp);
            vh.circleView.setOnClickListener((view -> {
                for (int i = 0; i < selectList.size(); i++) {
                    selectList.set(i, i == position);
                }
                notifyDataSetChanged();
                EventBus.getDefault().post(new ChooseColorEvent(colorList.get(position)));
            }));


            ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) vh.ivSelected.getLayoutParams();
            lp1.height = height / 3;
            lp1.width = height / 3;
            vh.ivSelected.setLayoutParams(lp1);
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private CircleView circleView;
            private ImageView ivSelected;

            public ViewHolder(@NonNull View rootView) {
                super(rootView);
                circleView = rootView.findViewById(R.id.circle_view);
                ivSelected = rootView.findViewById(R.id.iv_selected);
            }
        }
    }
}
