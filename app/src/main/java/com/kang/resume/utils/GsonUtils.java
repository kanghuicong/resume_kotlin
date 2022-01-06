package com.kang.resume.utils;

import android.content.Context;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kang.resume.preview.bean.TemplateBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class GsonUtils {

    public static ArrayList<TemplateBean> getGsonList(Context context){
        Gson gson = new Gson();
        try {
            InputStream in = context.getResources().getAssets().open("template.json");
            int available = in.available();
            byte[] b = new byte[available];
            in.read(b);
            String json = new String(b, "UTF-8");
            ArrayList<TemplateBean> stringList = gson.fromJson(json, new TypeToken<ArrayList<TemplateBean>>(){}.getType());
            return stringList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  static  String toJson(List list){
        Gson gson = new Gson();
        return  gson.toJson(list);
    }
}
