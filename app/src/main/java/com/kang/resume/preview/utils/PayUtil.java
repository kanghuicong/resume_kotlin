package com.kang.resume.preview.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kang.resume.R;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PayUtil {

    public static int amount = 0;


    public static String outTradeNo = "";


    public static int i = 0;

    public static void payWx(Activity context, int mAmount,String mOutTradeNo) {
        //订单号唯一，最简单的做法：自增的随机数+时间戳
//        outTradeNo = mOutTradeNo;
//        if (isWeChatInstalled(context)) {
//            amount = mAmount;
//            WechatUtil wechatUtil = new WechatUtil(context);
//            wechatUtil.getOrderId(new WechatUtil.IHttp() {
//                @Override
//                public void success(Map map) {
//                    try {
//                        IWXAPI mWxApi = WXAPIFactory.createWXAPI(context, context.getString(R.string.wechat_id), true);
//                        mWxApi.registerApp(context.getString(R.string.wechat_id));
//                        mWxApi.sendReq(buildWechatPayReq(context, wechatUtil, (String) map.get("prepay_id")));
//                    } catch (Exception e) {
//                        amount = 0;
//                    }
//                }
//
//                @Override
//                public void fail() {
//
//                }
//            });
//
//        }
    }

    //主动检查订单支付情况
//    public static void checkWx(Context context,int mAmount,String mOutTradeNo) {
//        WechatUtil wechatUtil = new WechatUtil(context);
//        outTradeNo = mOutTradeNo;
//        amount = mAmount;
//        wechatUtil.checkOrder();
//    }

    // 构建微信支付请求对象
//    private static PayReq buildWechatPayReq(Context context, WechatUtil wechatUtil, String preOrderId) {
//        PayReq payReq = new PayReq();
//        payReq.appId = context.getString(R.string.wechat_id);
//        payReq.partnerId = context.getString(R.string.partner_id);
//        payReq.prepayId = preOrderId;
//        payReq.packageValue = "Sign=WXPay";
//        payReq.nonceStr = UUID.randomUUID().toString().replace("-", "");
//        payReq.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
//
//        // 构建基本的参数列表
//        List<TwoTuple<String, String>> paramList = new ArrayList<>();
//        paramList.add(new TwoTuple<>("appid", payReq.appId));
//        paramList.add(new TwoTuple<>("noncestr", payReq.nonceStr));
//        paramList.add(new TwoTuple<>("package", payReq.packageValue));
//        paramList.add(new TwoTuple<>("partnerid", payReq.partnerId));
//        paramList.add(new TwoTuple<>("prepayid", payReq.prepayId));
//        paramList.add(new TwoTuple<>("timestamp", payReq.timeStamp));
//
//        payReq.sign = wechatUtil.generateWechatMD5Signature(paramList);
//
//        return payReq;
//    }


    public static boolean isWeChatInstalled(Activity context) {

        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        RxToast.normal("请先安装微信");
        return false;
    }


    public static class TwoTuple<A, B> {

        public final A first;
        public final B second;

        public TwoTuple(A a, B b) {
            first = a;
            second = b;
        }

        /**
         * 创建一个二元组, 用所给参数初始化 * @param a 第一个参数 * @param b 第二个参数 * @param <A> 第一个参数的类型 * @param <B> 第二参数的类型 * @return 包含所给参数的二元组
         */
        public static <A, B> TwoTuple<A, B> tuple(A a, B b) {
            return new TwoTuple<>(a, b);
        }
    }
}


