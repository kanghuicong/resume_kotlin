package com.kang.resume.custom.other

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.kang.resume.R
import com.ypx.imagepicker.adapter.PickerItemAdapter
import com.ypx.imagepicker.bean.ImageItem
import com.ypx.imagepicker.bean.selectconfig.BaseSelectConfig
import com.ypx.imagepicker.data.ICameraExecutor
import com.ypx.imagepicker.data.IReloadExecutor
import com.ypx.imagepicker.data.ProgressSceneEnum
import com.ypx.imagepicker.presenter.IPickerPresenter
import com.ypx.imagepicker.utils.PViewSizeUtils
import com.ypx.imagepicker.views.PickerUiConfig
import com.ypx.imagepicker.views.redbook.RedBookUiProvider
import java.util.*




/**
 * 类描述：
 * author:kanghuicong
 */
class RedBookPresenter : IPickerPresenter {
    override fun displayImage(view: View, item: ImageItem, size: Int, isThumbnail: Boolean) {
        val `object`: Any = if (item.uri != null) item.uri else item.path
        Glide.with(view.context).load(`object`).apply(
            RequestOptions()
                .format(if (isThumbnail) DecodeFormat.PREFER_RGB_565 else DecodeFormat.PREFER_ARGB_8888)
        )
            .override(if (isThumbnail) size else Target.SIZE_ORIGINAL)
            .into((view as ImageView))
    }

    /**
     * @param context 上下文
     * @return PickerUiConfig UI配置类
     */
    override fun getUiConfig(context: Context?): PickerUiConfig {
        val uiConfig = PickerUiConfig()
        uiConfig.themeColor = Color.RED
        uiConfig.isShowStatusBar = false
        uiConfig.statusBarColor = Color.BLACK
        uiConfig.pickerBackgroundColor = Color.BLACK
        uiConfig.folderListOpenDirection = PickerUiConfig.DIRECTION_TOP
        uiConfig.folderListOpenMaxMargin = PViewSizeUtils.dp(context, 200f)
        uiConfig.pickerUiProvider = RedBookUiProvider()
        return uiConfig
    }

    override fun tip(context: Context?, msg: String) {
        if (context == null) {
            return
        }
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 选择超过数量限制提示
     *
     * @param context  上下文
     * @param maxCount 最大数量
     */
    override fun overMaxCountTip(context: Context?, maxCount: Int) {
        if (context == null) {
            return
        }
        val builder = AlertDialog.Builder(context)
        builder.setMessage("最多选择" + maxCount + "个文件")
        builder.setPositiveButton(
            R.string.confirm,
            DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        val dialog = builder.create()
        dialog.show()
    }

    override fun showProgressDialog(
        activity: Activity?,
        progressSceneEnum: ProgressSceneEnum
    ): DialogInterface {
        return ProgressDialog.show(
            activity,
            null,
            if (progressSceneEnum == ProgressSceneEnum.crop) "正在剪裁..." else "正在加载..."
        )
    }

    override fun interceptPickerCompleteClick(
        activity: Activity?, selectedList: ArrayList<ImageItem>,
        selectConfig: BaseSelectConfig
    ): Boolean {
        return false
    }

    /**
     * 拦截选择器取消操作，用于弹出二次确认框
     *
     * @param activity     当前选择器页面
     * @param selectedList 当前已经选择的文件列表
     * @return true:则拦截选择器取消， false，不处理选择器取消操作
     */
    override fun interceptPickerCancel(
        activity: Activity?,
        selectedList: ArrayList<ImageItem>
    ): Boolean {
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return false
        }
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("是否放弃选择？")
        builder.setPositiveButton(R.string.confirm,
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                activity.finish()
            })
        builder.setNegativeButton(R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        val dialog = builder.create()
        dialog.show()
        return true
    }

    /**
     *
     *
     * 图片点击事件拦截，如果返回true，则不会执行选中操纵，如果要拦截此事件并且要执行选中
     * 请调用如下代码：
     *
     *
     * adapter.preformCheckItem()
     *
     *
     *
     *
     * 此方法可以用来跳转到任意一个页面，比如自定义的预览
     *
     * @param activity        上下文
     * @param imageItem       当前图片
     * @param selectImageList 当前选中列表
     * @param allSetImageList 当前文件夹所有图片
     * @param selectConfig    选择器配置项，如果是微信样式，则selectConfig继承自MultiSelectConfig
     * 如果是小红书剪裁样式，则继承自CropSelectConfig
     * @param adapter         当前列表适配器，用于刷新数据
     * @param isClickCheckBox 是否点击item右上角的选中框
     * @param reloadExecutor  刷新器
     * @return 是否拦截
     */
    override fun interceptItemClick(
        activity: Activity?,
        imageItem: ImageItem,
        selectImageList: ArrayList<ImageItem>,
        allSetImageList: ArrayList<ImageItem>,
        selectConfig: BaseSelectConfig,
        adapter: PickerItemAdapter,
        isClickCheckBox: Boolean,
        reloadExecutor: IReloadExecutor?
    ): Boolean {
        return false
    }

    override fun interceptCameraClick(activity: Activity?, takePhoto: ICameraExecutor): Boolean {
        return false
    }
}