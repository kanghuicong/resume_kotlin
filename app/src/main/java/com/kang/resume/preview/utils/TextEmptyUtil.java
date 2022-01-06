package com.kang.resume.preview.utils;

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class TextEmptyUtil {

    public static boolean isEmpty(String content) {
        if (content == null || content.equals("")) {
            return true;
        } else return false;
    }


    public static boolean isEmpty(List list) {
        if (list == null || list.size()==0) {
            return true;
        } else return false;
    }
}
