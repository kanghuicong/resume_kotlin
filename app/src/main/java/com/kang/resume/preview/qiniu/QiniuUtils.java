package com.kang.resume.preview.qiniu;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 类描述：
 * author:kanghuicong
 */
public class QiniuUtils {

    static final String accessKey = "V3x34a7tGZyNcPJNDsB8VxBqlIYHv1Azrbk15uBS";
    static final String secretKey = "2-ZOL_BhyqQrpbvZY2mwGiWXFXwfJm_D3tMVJUAa";
    static final String bucket = "wuyouresume";
    static final String domainOfBucket = "http://wuyouresume.xiaochengkeji.top";

    public static String downImg(String fileName) {
//        if (fileName != null && fileName != "") {
//            String[] list = fileName.split("/");
//
//            String file;
//            if (list.length > 0) {
//                file = list[list.length - 1];
//            } else {
//                file = fileName;
//            }
//
//            String encodedFileName = null;
//            try {
//                encodedFileName = URLEncoder.encode(file, "utf-8").replace("+", "%20");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                return null;
//            }
//
//            RxLogTool.e("downImg-fileUrl:", file);
//            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
//
//            Auth auth = Auth.create(accessKey, secretKey);
//            long expireInSeconds = 365 * 24 * 3600;//可以自定义链接过期时间
//            String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
//
//            RxLogTool.e("downImg-finalUrl:", finalUrl);
//
//            return finalUrl;
//        } else return null;
        return "";
    }


    public static void uploadImg(String userId, String fileUrl, IUpload iUpload) {
//        Configuration config = new Configuration.Builder()
//                .zone(FixedZone.zone2)
//                .build();
//        UploadManager uploadManager = new UploadManager(config);
//
//        long timeStamp = System.currentTimeMillis() / 1000;
//        String key = "Android_" + userId + "_" + timeStamp + "_ResumeAvatar";
//        RxLogTool.e("key:", key);
//
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//
//        uploadManager.put(fileUrl, key, upToken, new UpCompletionHandler() {
//            @Override
//            public void complete(String key, ResponseInfo info, JSONObject response) {
//                //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
//                RxLogTool.e("uploadManager:", info.toString());
//
//                if (info.isOK()) {
//                    iUpload.success(key);
//                } else {
//                    iUpload.fail();
//                }
//            }
//        }, null);
    }

    public interface IUpload {
        void success(String url);

        void fail();
    }

}
