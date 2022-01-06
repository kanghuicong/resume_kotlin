package com.kang.resume.preview.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kang.resume.R;
import com.kang.resume.preview.custom.RadiusCardView;
import com.kang.resume.preview.utils.Config;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class TagsAdapter extends TagAdapter<String> {
    private Context context;
    private List<String> list = new ArrayList<>();

    private RadiusCardView cardView;
    private TextView tvTag;
    private int size;
    private int color;

    public TagsAdapter(Context context, List<String> list, int size, int color) {
        super(list);
        this.context = context;
        this.list = list;
        this.size = size;
        this.color = color;
    }


    @Override
    public View getView(FlowLayout parent, int position, String s) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);

        cardView = v.findViewById(R.id.card);
        tvTag = v.findViewById(R.id.tv_tag);

        tvTag.setText(s);
        tvTag.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        ViewGroup.MarginLayoutParams lp0 = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        lp0.setMarginEnd(size);
        lp0.height = size * 2;
        cardView.setLayoutParams(lp0);

        cardView.setAllRadius((float) size/3);

        tvTag.setPadding(size,size / 4,size,size / 4);

        if (color==-1){
            tvTag.setBackground(ContextCompat.getDrawable(context,R.drawable.hobbies_shape));
            tvTag.setTextColor(Config.titleHighColor);
        }else {
            tvTag.setBackgroundColor(color);
            tvTag.setTextColor(ContextCompat.getColor(context,R.color.white));
        }

        return v;
    }

    public void update(int size, int color) {
        this.size = size;
        this.color = color;
        notifyDataChanged();
    }
}
