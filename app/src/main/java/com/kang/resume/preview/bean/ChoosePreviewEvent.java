package com.kang.resume.preview.bean;

/**
 * 类描述：
 * author:kanghuicong
 */
public class ChoosePreviewEvent {

    private int position;

    public ChoosePreviewEvent(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
