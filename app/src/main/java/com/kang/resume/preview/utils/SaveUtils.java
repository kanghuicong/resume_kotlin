package com.kang.resume.preview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.vondear.rxtool.RxFileTool;
import com.vondear.rxtool.view.RxToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 类描述：
 * author:kanghuicong
 */
public class SaveUtils {


    public static void saveBitmapForPdf(com.kang.resume.preview.PreViewActivity context, List<View> views, String name, int width, int height, ICreatePdf iCreatePdf) {

        if (TextEmptyUtil.isEmpty(name)) {
            name = "简历" + System.currentTimeMillis();
        }
        String finalName = name;

        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
//                        loading(context, true);

                        String appDir = getFilePath(context, "pdf");
                        if (!RxFileTool.isFileExists(appDir)) {
                            RxFileTool.initDirectory(appDir);
                        }

                        PdfDocument doc = new PdfDocument();
                        PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(width, height, 1).create();
                        for (int i = 0; i < views.size(); i++) {
                            PdfDocument.Page page = doc.startPage(newPage);
                            views.get(i).draw(page.getCanvas());
                            doc.finishPage(page);
                        }

                        File file = new File(appDir, finalName + ".pdf");
                        FileOutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(file);
                            doc.writeTo(outputStream);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
//                            loading(context, false);
                            RxToast.error("保存失败");
                        } catch (IOException e) {
                            e.printStackTrace();
//                            loading(context, false);
                            RxToast.error("保存失败");
                        } finally {
                            doc.close();
                            try {
                                if (outputStream != null) {
                                    outputStream.close();

                                    iCreatePdf.createSuccess(appDir + "/" + finalName + ".pdf");
                                    RxToast.success("保存成功");
//                                    loading(context, false);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                RxToast.error("保存失败");
//                                loading(context, false);
                            }
                        }
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                RxToast.normal(context, "为了不影响您的使用体验，建议开启手机存储服务");
            }
        }).start();

    }


    public static void saveBitmapForImg(final Activity context, final View view, final String bitName, ICreatePdf iCreatePdf) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                        //获取指定view的Bitmap
                        Bitmap mBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(mBitmap);
                        view.draw(canvas);

                        String local_path = Environment.getExternalStorageDirectory().getPath() + "/IMG";
                        File appDir = new File(local_path);
                        //判断不存在就创建
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        //创建file对象
                        File f = new File(local_path, bitName + ".png");
                        try {
                            //创建
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FileOutputStream fOut = null;
                        try {
                            fOut = new FileOutputStream(f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        //原封不动的保存在内存卡上
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        try {
                            fOut.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 其次把文件插入到系统图库
                        try {
                            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                    f.getAbsolutePath(), bitName, null);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // 最后通知图库更新
                        String path = Environment.getExternalStorageDirectory().getPath();
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
                        RxToast.success("保存成功,请在相册中查看");

                        iCreatePdf.createSuccess(local_path + "/" + bitName + ".png");
                        //删除最先生成的那张图片
//                        RxFileTool.deleteFile(f);
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                RxToast.normal(context, "为了不影响您的使用体验，建议开启手机存储服务");
            }
        }).start();
    }


    public interface ICreatePdf {
        void createSuccess(String path);
    }

//    private static void loading(com.kang.resume.preview.PreViewActivity activity, boolean isShow) {
//        if (isShow) activity.loading.start();
//        else activity.loading.stop();
//    }


    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
            // directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
        } else {
            //没内存卡就存机身内存
            directoryPath = context.getFilesDir() + File.separator + dir;
            // directoryPath=context.getCacheDir()+File.separator+dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }
}
