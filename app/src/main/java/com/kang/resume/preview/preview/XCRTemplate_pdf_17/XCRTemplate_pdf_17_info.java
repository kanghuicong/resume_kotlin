package com.kang.resume.preview.preview.XCRTemplate_pdf_17;

import android.content.Context;
import android.view.Gravity;

import com.kang.resume.preview.preview.BaseInfoView;
import com.kang.resume.preview.pro.IInfo;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_17_info extends BaseInfoView implements IInfo {

    public XCRTemplate_pdf_17_info(Context context) {
        super(context);
        init();
    }

    @Override
    public int avatarGravity() {
        return Gravity.TOP;
    }

    @Override
    public boolean isCenterInfo() {
        return true;
    }
}
