package com.kang.resume.preview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kang.resume.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExportTagAdapter extends TagAdapter<String>{
    private Context context;
    private List<String> list = new ArrayList<>();
    private List<String> selectedList = new ArrayList<>();

    private LinearLayout cardView;
    private TextView tagTV;


    public ExportTagAdapter(Context context,List<String> list) {
        super(list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exp_tag,parent,false);

        tagTV = v.findViewById(R.id.tagTV);
        tagTV.setText(s);
        tagTV.setPadding(12,3,12,3);
        if(this.selectedList.contains(s)){
            v.setSelected(true);
        }else{
            v.setSelected(false);
        }
        return v;
    }

    public void update(){
        notifyDataChanged();
    }

    public void setSelectedList(List<String> list){
        this.selectedList = list;
        update();
    }
}















