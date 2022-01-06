package com.kang.resume.preview.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kang.resume.R;

/**
 * 类描述：
 * author:kanghuicong
 */
public class GlideUtils {

    public static void glideAvatar(Context context, String url, ImageView ivAvatar){
        if (url==null||url.equals("")){
            ivAvatar.setVisibility(View.GONE);
        }else {
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.icon_header_default)
                            .error(R.mipmap.icon_header_default)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(ivAvatar);
        }
    }
}
