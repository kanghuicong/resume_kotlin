package com.kang.resume.preview.preview.XCRTemplate_pdf_21;

import android.content.Context;
import android.view.Gravity;

import com.kang.resume.preview.preview.BaseInfoView;
import com.kang.resume.preview.pro.IInfo;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_21_info extends BaseInfoView implements IInfo {


    public XCRTemplate_pdf_21_info(Context context) {
        super(context);
        init();
    }

    @Override
    public int avatarGravity() {
        return Gravity.END;
    }

}
