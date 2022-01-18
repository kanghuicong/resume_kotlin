package com.kang.resume.custom

import android.app.Activity
import android.content.Context
import android.text.Selection
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import com.kang.resume.R
import com.kang.resume.base.ValueConfig
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.preview.adapter.ExportTagAdapter
import com.kang.resume.utils.SwitchUtils
import com.lxj.xpopup.core.BottomPopupView
import com.zhy.view.flowlayout.TagFlowLayout
import com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener
import java.util.*


/**
 * 类描述：
 * author:kanghuicong
 */
class ExportDialog(
    context: Context,
    var clickResumeBean: ResumeInfoBean? = null,
    var iSave: ISave? = null,
) : BottomPopupView(context) {
    private val exportNameList = arrayListOf<String>()
    private val exportTypeList = arrayListOf<String>()
    private lateinit var tagFlowLayout: TagFlowLayout //展示标签的
    private lateinit var tagsAdapter: ExportTagAdapter

    val hobbiesList = arrayOf("姓名", "求职意向", "工作经验", "电话号码", "邮箱", "学历", "期望城市")
    val exportTypeArray = arrayOf("导出为PDF", "导出为图片")


    override fun getImplLayoutId(): Int {
        return R.layout.view_export
    }

    override fun onCreate() {
        super.onCreate()

        val nameEdit: EditText = findViewById<EditText>(R.id.resume_name_et)
        //数据源
        //数据源
        if (exportNameList.isEmpty()) {
            exportNameList.add(hobbiesList[0])
            exportNameList.add(hobbiesList[1])
        }
        updateNameEditText(nameEdit)

        //选择tag设置简历名
        tagFlowLayout = findViewById(R.id.type_name_flowLayout)
        val lists: MutableList<String> = ArrayList()
        for (str in hobbiesList) {
            lists.add(str)
        }
        tagsAdapter = ExportTagAdapter(context, lists)
        tagFlowLayout.adapter = tagsAdapter
        tagsAdapter.setSelectedList(exportNameList)
        //监听点击事件
        //监听点击事件
        tagFlowLayout.setOnTagClickListener(OnTagClickListener { view, position, parent ->
            val viewGroup = view as ViewGroup
            val tagView = viewGroup.getChildAt(0)
            val clickTagText = hobbiesList[position]
            if (exportNameList.contains(clickTagText)) { //已经包含
                tagView.isSelected = false
                val str = hobbiesList[position]
                exportNameList.remove(str)
            } else { //没有包含
                tagView.isSelected = true
                val str = hobbiesList[position]
                exportNameList.add(str)
            }
            updateNameEditText(nameEdit)
            false
        })
        //保存格式
        //保存格式
        if (exportTypeList.isEmpty()) { //如果是空的
            exportTypeList.add(exportTypeArray[0])
        }
        val exportTypeFlowLayout: TagFlowLayout =
            findViewById(R.id.export_type_flowlayout)
        val exportList: MutableList<String> = ArrayList()
        for (str in exportTypeArray) {
            exportList.add(str)
        }
        val exportTagAdapter = ExportTagAdapter(context, exportList)
        exportTypeFlowLayout.adapter = exportTagAdapter
        exportTagAdapter.setSelectedList(exportTypeList)
        //监听点击事件
        //监听点击事件
        exportTypeFlowLayout.setOnTagClickListener { view, position, parent ->
            val clickTagText = exportTypeArray[position]
            exportTypeList.removeAt(0)
            if (clickTagText == exportTypeArray[0]) { //已经包含
                exportTypeList.add(exportTypeArray[0])
            } else { //没有包含
                exportTypeList.add(exportTypeArray[1])
            }
            exportTagAdapter.setSelectedList(exportTypeList)
            false
        }

        findViewById<View>(R.id.close_btn).setOnClickListener { dismiss() }

        findViewById<View>(R.id.export_btn).setOnClickListener(OnClickListener {
            val fileName = nameEdit.text.toString()
            //开始导出
            if (exportTypeList[0] == exportTypeArray[1]) {
                if (clickResumeBean == null) return@OnClickListener
                if (iSave != null) iSave?.saveImg(fileName)
            } else {
                if (clickResumeBean == null) return@OnClickListener
                if (iSave != null) iSave?.savePdf(fileName)

            }
        })

    }

    private fun updateNameEditText(editText: EditText) {
        var nameStr = ""
        val infoBean: BaseInfoBean? = clickResumeBean?.baseInfo
        for (i in exportNameList.indices) {
            val str = exportNameList[i]
            var tempStr = ""
            if (str == hobbiesList[0]) {
                if (infoBean != null) {
                    tempStr = infoBean.name
                }
            } else if (str == hobbiesList[1]) {
                val positionBean: JobIntentionBean? = clickResumeBean?.jobIntention
                if (positionBean != null) {
                    tempStr = positionBean.position
                }
            } else if (str == hobbiesList[2]) {
                if (infoBean != null) {
                    tempStr = SwitchUtils.switchYear(infoBean.startWorkTime)+"年"
                }
            } else if (str == hobbiesList[3]) {
                if (infoBean != null) {
                    tempStr = infoBean.phone
                }
            } else if (str == hobbiesList[4]) {
                if (infoBean != null) {
                    tempStr = infoBean.email
                }
            } else if (str == hobbiesList[5]) {
                if (infoBean != null) {
                    tempStr = infoBean.record
                }
            } else if (str == hobbiesList[6]) {
                val positionBean: JobIntentionBean? = clickResumeBean?.jobIntention
                if (positionBean != null) {
                    tempStr = positionBean.city
                }
            }
            if (nameStr == "") {
                nameStr = tempStr
            } else {
                if (tempStr != "") {
                    nameStr += ValueConfig.space + tempStr
                }
            }
        }
        editText.setText(nameStr)
        val etAble = editText.text
        Selection.setSelection(etAble, etAble.length)
    }

}

interface ISave {
    fun saveImg(name: String)
    fun savePdf(name: String)
}