package com.kang.resume.preview.bean;

/**
 * 类描述：
 * author:kanghuicong
 */
public class AddCoverEvent {
    public int coverId;
    public int position;

    public AddCoverEvent(int position,int coverId){
        this.coverId = coverId;
        this.position = position;
    }

    public int getCoverId() {
        return coverId;
    }

    public int getPosition() {
        return position;
    }
}
