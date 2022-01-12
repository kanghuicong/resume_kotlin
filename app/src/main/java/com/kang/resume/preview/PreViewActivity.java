package com.kang.resume.preview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.kang.resume.R;
import com.kang.resume.base.ValueConfig;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.EducationBean;
import com.kang.resume.bean.JobIntentionBean;
import com.kang.resume.bean.ResumeInfoBean;
;
import com.kang.resume.preview.adapter.ExportTagAdapter;
import com.kang.resume.preview.adapter.MyPageAdapter;
import com.kang.resume.preview.bean.AddCoverEvent;
import com.kang.resume.preview.bean.AllModuleBean;
import com.kang.resume.preview.bean.ChooseColorEvent;
import com.kang.resume.preview.bean.ChoosePreviewEvent;
import com.kang.resume.preview.bean.TemplateBean;
import com.kang.resume.preview.cover.CoverUtils;
import com.kang.resume.preview.custom.ClipConstraintLayout;
import com.kang.resume.preview.fragment.AppFragment;
import com.kang.resume.preview.fragment.SaveFragment;
import com.kang.resume.preview.fragment.ThemeFragment;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.pro.IView;
import com.kang.resume.preview.utils.ColorUtils;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.PreviewUtils;
import com.kang.resume.preview.utils.SaveUtils;
import com.kang.resume.preview.utils.ShareFileUtils;
import com.vondear.rxtool.RxBarTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxTool;
import com.vondear.rxtool.view.RxToast;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * 类描述：
 * author:kanghuicong
 */
public class PreViewActivity extends FragmentActivity implements View.OnClickListener {
    private ImageButton ivBack;
    private Button exportBtn;
    private NestedScrollView scPreview;
    private ImageView ivDefault;

    private ConstraintLayout llPreview;
    private LinearLayout llTitleLayout;
    private FrameLayout flMain;
    private TextView tvApplication;
    private TextView tvTheme;
    private TextView tvKeep;
    private TextView tvPage;
    private ViewPager viewpager;

    private LinearLayout llApp;
    private ImageView ivApp;
    private LinearLayout llTheme;
    private ImageView ivTheme;
    private LinearLayout llKeep;
    private ImageView ivKeep;

    private FragmentManager fm;
    private AppFragment appFragment;
    private ThemeFragment themeFragment;
    private SaveFragment saveFragment;


    private PopupWindow popupWindow;
    private TagFlowLayout tagFlowLayout;//展示标签的
    private ExportTagAdapter tagsAdapter;
    private List<String> exportNameList = new ArrayList<>();
    private List<String> exportTypeList = new ArrayList<>();
    //简历下标
    int position = 0;

    //简历数据
    private ResumeInfoBean clickResumeBean;
    private List<AllModuleBean> moduleBeanList = new ArrayList<>();
    List<ResumeInfoBean> list;

    PreviewUtils previewUtils;
    List<View> pageView = new ArrayList<>();
    MyPageAdapter adapter;

    //page宽度
    private int A4width;
    //page高度
    private int A4height;
    //page页数
    private int A4num;
    //page内边距
    private int A4pdHeight;

    //是否有封面
    private boolean isCover = false;


    private int moveY;
    private IView iView = null;
    TemplateBean templateBean;


    CoverUtils coverUtils;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();

        setContentView(R.layout.view_preview);
        llTitleLayout = findViewById(R.id.title_ll_layout);
        ivBack = findViewById(R.id.iv_back);
        exportBtn = findViewById(R.id.btn_export);

        llPreview = findViewById(R.id.ll_preview);
        scPreview = findViewById(R.id.sc_preview);
        tvPage = findViewById(R.id.tv_page);
        ivDefault = findViewById(R.id.iv_default);


        llApp = findViewById(R.id.ll_app);
        ivApp = findViewById(R.id.iv_app);
        llTheme = findViewById(R.id.ll_theme);
        ivTheme = findViewById(R.id.iv_theme);
        llKeep = findViewById(R.id.ll_keep);
        ivKeep = findViewById(R.id.iv_keep);

        flMain = findViewById(R.id.fl_main);
        tvApplication = findViewById(R.id.tv_application);
        tvTheme = findViewById(R.id.tv_theme);
        tvKeep = findViewById(R.id.tv_keep);

        viewpager = findViewById(R.id.viewpager);
//        loading = findViewById(R.id.loading);

        llApp.setOnClickListener(this);
        llTheme.setOnClickListener(this);
        llKeep.setOnClickListener(this);
        ivBack.setOnClickListener((view -> {
            onBackPressed();
        }));
        exportBtn.setOnClickListener((view -> {
            //弹出导出页
            initPopupWindow();
            showPopWindow();
        }));
        //注册EventBus
        EventBus.getDefault().register(this);
        //接收数据

        list = (List<ResumeInfoBean>)getIntent().getExtras().getSerializable("json");

        if (list == null || list.size() == 0) {
            finish();
            RxToast.normal("请先创建一份简历！");
            return;
        }
        TemplateBean templateBean = (TemplateBean)getIntent().getExtras().getSerializable("template");
        RxLogTool.e("templateBean:" + templateBean.getClassName());
        //动态实例化对应的模板类
        try {
            Class toolClass = Class.forName(ValueConfig.classPageName + templateBean.getClassName() + "." + templateBean.getClassName());
            Constructor constructor = toolClass.getConstructor(Context.class);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(this);
            iView = (IView) obj;
            scPreview.addView((View) iView);

            constructor.setAccessible(false);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            RxToast.normal("该模板还未更新，请尝试选择另一份模板");
            finish();
            return;
        }

        //加载Fragment
        fm = getSupportFragmentManager();
        appFragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        appFragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fl_main, appFragment).addToBackStack(null).commit();

        previewUtils = new PreviewUtils(this);

        //确定预览宽高
        llPreview.post((() -> {
            //测量的宽高
            int height = llPreview.getHeight();
            int width = llPreview.getWidth();
            //实际的宽高
            int rlHeight;
            int rlWidth;
            if (width >= height) {
                rlHeight = height;
                rlWidth = height * 210 / 297;
            } else {
                rlWidth = width;
                rlHeight = width * 297 / 210;
                if (rlHeight > height) {
                    rlHeight = height;
                    rlWidth = height * 210 / 297;
                }
            }

            //预览页面大小
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rlWidth, rlHeight);
            lp.gravity = Gravity.CENTER;
            llPreview.setLayoutParams(lp);
            //设置字体大小为 页面宽度的1/55
            Config.setTextSize(rlWidth / 55);
            //内边距
            Config.setA4Padding(Config.textSize * 3);

            coverUtils = new CoverUtils(this, rlWidth, rlHeight, Config.textSize);

            //加载数据显示预览
            initData(position, templateBean);
        }));
    }

    private void showLoad(boolean isShow) {
//        if (isShow) {
//            llLoad.setVisibility(View.VISIBLE);
//            loading.start();
//        } else {
//            llLoad.setVisibility(View.GONE);
//            loading.stop();
//
//        }
    }

    //数据
    private void initData(int position, TemplateBean templateBean) {
        this.position = position;
        this.templateBean = templateBean;
        showLoad(true);
        ivDefault.setVisibility(View.VISIBLE);

        //占位隐藏，不影响计算高度的提示隐藏
        iView.getView().setVisibility(View.INVISIBLE);

        //简历列表position被点击
        list.get(position).setClick(true);
        //当前简历的信息
        clickResumeBean = list.get(position);

        moduleBeanList.clear();
//        moduleBeanList.addAll(JSON.parseArray(clickResumeBean.getAllModules(), AllModuleBean.class));

        //previewUtils添加数据，下一步addViews需要使用
        previewUtils.setData(clickResumeBean, templateBean);
        //添加布局
        addViews(moduleBeanList, iView);

        //重置完布局立刻计算clPreview宽高是获取不到数值的
        iView.getView().post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
//                RxTool.delayToDo(1000, () -> {
                //先计算页数；
                //A4比例
                A4width = iView.getPreviewWidth();
                RxLogTool.e("A4width:" + A4width);
                A4height = iView.getPreviewWidth() * 297 / 210;
                RxLogTool.e("A4height:" + A4height);
                //无边距时的页数
                float x = iView.getClipView().getMeasuredHeight();
                A4num = iView.getClipView().getMeasuredHeight() / A4height + ((iView.getClipView().getMeasuredHeight() % A4height == 0) ? 0 : 1);
                RxLogTool.e("iView.getClipView().getMeasuredHeight():" + iView.getClipView().getMeasuredHeight());
                RxLogTool.e("无边距A4num:" + A4num);
                //加入边距后重新计算页数
                A4pdHeight = A4num * Config.A4Padding * 2 + iView.getClipView().getMeasuredHeight();
                RxLogTool.e("A4pdHeight:" + A4pdHeight);
                //最终页数
                A4num = A4pdHeight / A4height + ((A4pdHeight % A4height < 2) ? 0 : 1);
                RxLogTool.e("有边距A4num:" + A4num);
                RxLogTool.e("Config.A4Padding:" + Config.A4Padding);

                RxLogTool.e("A4num:" + A4num + "-----clPreview:" + iView.getPreviewWidth() + "------" + iView.getClipView().getMeasuredHeight());

                //输出pdf的布局
                pageView.clear();


                for (int i = 0; i < A4num; i++) {
                    List<View> viewList = addViews(moduleBeanList, null);

                    //动态实例化对应的模板类
                    try {
                        Class toolClass = Class.forName(ValueConfig.classPageName + templateBean.getClassName() + "." + templateBean.getClassName());
                        Constructor constructor = toolClass.getConstructor(Context.class);
                        constructor.setAccessible(true);
                        Object obj = constructor.newInstance(com.kang.resume.preview.PreViewActivity.this);
                        IView miView = (IView) obj;
                        XCRTemplate_pdf_base baseView = (XCRTemplate_pdf_base) miView;
                        baseView.setClickResumeBean(clickResumeBean);
                        miView.setClipViewLayoutParams(iView.getClipView().getMeasuredWidth()).setData(viewList);
                        pageView.add((View) miView);
                        constructor.setAccessible(false);

                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                        RxToast.normal("发生错误");
                        finish();
                        return;
                    }
                }
                //设置默认颜色
                Config.setChooseColor(ColorUtils.hexToColor(templateBean.getXcrMainThemeStr()));
                previewUtils.setColor(pageView, ColorUtils.hexToColor(templateBean.getXcrMainThemeStr()));

                RxLogTool.e("size1:" + pageView.size());
                moveY = 0;
                for (int i = 0; i < pageView.size(); i++) {
                    //切割成views.size()份，每个view取对应的区域
                    RxLogTool.e("size2:" + pageView.size());

                    int finalI = i;
                    RxTool.delayToDo(100, () -> {
                        int maxY = A4height * (finalI + 1) - Config.A4Padding * ((finalI + 1) * 2) - moveY;

                        RxLogTool.e("maxY---A4height:" + A4height + "------Config.A4Padding:" + Config.A4Padding);
                        RxLogTool.e("maxY:" + maxY + "------moveY:" + moveY);

                        //不为白色像素点数量小于maxN时，认为这一行为白色
                        if (finalI < pageView.size()) {
                            int maxN = ((IView) (pageView.get(finalI))).getLineWidth();

                            ClipConstraintLayout layout = ((IView) (pageView.get(finalI))).getClipView().setClipView(0,
                                    A4height * finalI - (finalI * 2) * Config.A4Padding - moveY,
                                    //这里是切割的ClipView
                                    ((IView) (pageView.get(finalI))).getClipView().getMeasuredWidth(),
                                    maxY,
                                    maxN,
                                    new ClipConstraintLayout.IMove() {
                                        @Override
                                        public void moveY(int y) {
                                            moveY = moveY + y;

                                            LinearLayout.MarginLayoutParams lp = (LinearLayout.MarginLayoutParams) ((IView) (pageView.get(finalI))).getClipView().getLayoutParams();
                                            lp.topMargin = -(A4height * finalI
                                                    - Config.A4Padding * ((finalI == 0 ? (((IView) (pageView.get(finalI))).needTopMargin() ? 1 : 0) : 1 + finalI * 2))
                                                    - moveY);
                                            RxLogTool.e("moveY:" + moveY);
                                            ((IView) (pageView.get(finalI))).getClipView().setLayoutParams(lp);
                                            //但是直接取区域可能会切割内容，所以再对view进行clipRect，

                                        }
                                    });


                            //说明计算误差生成的空白页，需要删除
                            if (layout == null) {
                                pageView.remove(pageView.size() - 1);

                                addCover();
                                adapter.notifyDataSetChanged();
                                viewpager.setVisibility(View.VISIBLE);
                                ivDefault.setVisibility(View.GONE);
                                showLoad(false);
                                tvPage.setText("1/" + pageView.size());
                            } else {
                                //显示viewpager
                                if (finalI == pageView.size() - 1) {
                                    addCover();
                                    adapter.notifyDataSetChanged();
                                    viewpager.setVisibility(View.VISIBLE);
                                    ivDefault.setVisibility(View.GONE);
                                    showLoad(false);
                                    tvPage.setText("1/" + pageView.size());
                                }
                            }
                        }
                    });
                }

                adapter = new MyPageAdapter(pageView);
                viewpager.setAdapter(adapter);
                viewpager.setVisibility(View.INVISIBLE);
                viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        int index = position + 1;
                        tvPage.setText(index + "/" + pageView.size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        });
    }

    private void addCover() {
        if (isCover) {
            pageView.add(0, coverUtils.getCover(clickResumeBean, coverId, coverPosition));
        }
    }

    private List<View> addViews(List<AllModuleBean> mModuleBeanList, IView iView) {
        List<View> baseItemViews = new ArrayList<>();
        LinearLayout layout = null;
        if (iView != null) {
            layout = iView.getLinearLayout();
            if (layout != null)
                layout.removeAllViews();
        }
        //按moduleBeanList数据顺序添加view

        List<AllModuleBean> moduleBeanList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add(Config.INFO);
        list.add(Config.POSITION);
        list.add(Config.EDUCATION);
        list.add(Config.JOB);
        list.add(Config.PROJECT);
        list.add(Config.SKILLS);
        list.add(Config.AWARD);
        list.add(Config.INTEREST);
        list.add(Config.ASSESSMENT);

        for (String ty: list){
            AllModuleBean bean = new AllModuleBean();
            bean.setModuleType(ty);
            bean.setName(ty);
            bean.setShowed(true);
            moduleBeanList.add(bean);
        }

        for (int i = 0; i < moduleBeanList.size(); i++) {
            switch (moduleBeanList.get(i).getModuleType()) {
                case Config.INFO:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addInfo(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.POSITION:
                    if (moduleBeanList.get(i).isShowed()) {
                        if (this.iView.needPosition()) {
                            previewUtils.addNullView(baseItemViews, previewUtils.addPosition(moduleBeanList.get(i).getName(), layout));
                        }
                    }

                    break;
                case Config.EDUCATION:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addEducation(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.JOB:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addWork(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.PROJECT:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addProject(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.SKILLS:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addSkill(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.AWARD:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addRecord(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.INTEREST:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addHobbies(moduleBeanList.get(i).getName(), layout));
                    break;
                case Config.ASSESSMENT:
                    if (moduleBeanList.get(i).isShowed())
                        previewUtils.addNullView(baseItemViews, previewUtils.addAbstract(moduleBeanList.get(i).getName(), layout));
                    break;
            }
        }
        return baseItemViews;
    }

    //切换简历
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChoosePreview(ChoosePreviewEvent event) {
        initData(event.getPosition(), templateBean);
    }

    //切换颜色
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChooseColor(ChooseColorEvent event) {
        Config.setChooseColor(event.getColor());
        previewUtils.setColor(pageView, event.getColor());
    }

    int coverId;
    int coverPosition;

    //添加封面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AddCoverEvent(AddCoverEvent event) {

        coverId = event.coverId;
        coverPosition = event.position;
        isCover = event.position != 0;
        initData(position, templateBean);

    }

    //调用flutter，进入会员充值页面
    private void goPay() {

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (appFragment != null) {
            transaction.hide(appFragment);
        }
        if (themeFragment != null) {
            transaction.hide(themeFragment);
        }
        if (saveFragment != null) {
            transaction.hide(saveFragment);
        }
        switch (v.getId()) {
            case R.id.ll_app:
                selectItem(1);
                if (appFragment == null) {
                    appFragment = new AppFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    appFragment.setArguments(bundle);
                    transaction.add(R.id.fl_main, appFragment);
                } else {
                    transaction.show(appFragment);
                }
                transaction.commitAllowingStateLoss();
                break;
            case R.id.ll_theme:
                selectItem(2);
                if (themeFragment == null) {
                    transaction.add(R.id.fl_main, themeFragment = new ThemeFragment());
                } else {
                    transaction.show(themeFragment);
                }
                transaction.commitAllowingStateLoss();
                break;
            case R.id.ll_keep:
//                selectItem(3);
//                if (saveFragment == null) {
//                    transaction.add(R.id.fl_main, saveFragment = new SaveFragment());
//                } else {
//                    transaction.show(saveFragment);
//                }
//                transaction.commitAllowingStateLoss();
                initPopupWindow();
                showPopWindow();
                break;
            default:
                break;
        }
    }

    private void selectItem(int index) {
        ivApp.setBackground(ContextCompat.getDrawable(this, index == 1 ? R.mipmap.icon_preview_app_select : R.mipmap.icon_preview_app_unselect));
        tvApplication.setTextColor(ContextCompat.getColor(this, index == 1 ? R.color.primary : R.color.black25));

        ivTheme.setBackground(ContextCompat.getDrawable(this, index == 2 ? R.mipmap.icon_preview_style_select : R.mipmap.icon_preview_style_unselect));
        tvTheme.setTextColor(ContextCompat.getColor(this, index == 2 ? R.color.primary : R.color.black25));

        ivKeep.setBackground(ContextCompat.getDrawable(this, index == 3 ? R.mipmap.icon_preview_save_select : R.mipmap.icon_preview_save_unselect));
        tvKeep.setTextColor(ContextCompat.getColor(this, index == 3 ? R.color.primary : R.color.black25));

    }

    private void initPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.view_export, null, false);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(R.drawable.black_round_3a_12));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroundAlpha(1.0f);
            }
        });
        EditText nameEdit = contentView.findViewById(R.id.resume_name_et);
        //数据源
        if (exportNameList.isEmpty()) {
            exportNameList.add("姓名");
            exportNameList.add("求职意向");
            //生成简历名称
            BaseInfoBean infoBean = clickResumeBean.getBaseInfo();
            JobIntentionBean positionBean = clickResumeBean.getJobIntention();
            if (positionBean!=null) {
                nameEdit.setText(infoBean.getName() + "-" + positionBean.getPosition());
            }
        } else {
            updateNameEditText(nameEdit);
        }
        //选择tag设置简历名
        tagFlowLayout = contentView.findViewById(R.id.type_name_flowLayout);
        String[] hobbiesList = new String[]{"求职意向", "姓名", "工作经验", "电话号码", "邮箱", "学历", "期望城市"};
        List<String> lists = new ArrayList<>();
        for (String str : hobbiesList) {
            lists.add(str);
        }
        tagsAdapter = new ExportTagAdapter(this, lists);
        tagFlowLayout.setAdapter(tagsAdapter);
        tagsAdapter.setSelectedList(exportNameList);
        //监听点击事件
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ViewGroup viewGroup = (ViewGroup) view;
                View tagView = viewGroup.getChildAt(0);
                String clickTagText = hobbiesList[position];
                if (exportNameList.contains(clickTagText)) {//已经包含
                    tagView.setSelected(false);
                    String str = hobbiesList[position];
                    exportNameList.remove(str);
                } else {//没有包含
                    tagView.setSelected(true);
                    String str = hobbiesList[position];
                    exportNameList.add(str);
                }
                updateNameEditText(nameEdit);
                return false;
            }
        });
        //保存格式
        if (exportTypeList.isEmpty()) {//如果是空的
            exportTypeList.add("导出为PDF");
        }
        TagFlowLayout exportTypeFlowLayout = contentView.findViewById(R.id.export_type_flowlayout);
        String[] exportTypeArray = new String[]{"导出为PDF", "导出为图片"};
        List<String> exportList = new ArrayList<>();
        for (String str : exportTypeArray) {
            exportList.add(str);
        }
        ExportTagAdapter exportTagAdapter = new ExportTagAdapter(this, exportList);
        exportTypeFlowLayout.setAdapter(exportTagAdapter);
        exportTagAdapter.setSelectedList(exportTypeList);
        //监听点击事件
        exportTypeFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ViewGroup viewGroup = (ViewGroup) view;
                View tagView = viewGroup.getChildAt(0);
                String clickTagText = exportTypeArray[position];
                exportTypeList.remove(0);
                if (clickTagText.equals("导出为PDF")) {//已经包含
                    exportTypeList.add("导出为PDF");
                } else {//没有包含
                    exportTypeList.add("导出为图片");
                }
                exportTagAdapter.setSelectedList(exportTypeList);
                return false;
            }
        });

        ImageButton closeBtn = (ImageButton) contentView.findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        Button exportBtn = (Button) contentView.findViewById(R.id.export_btn);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = nameEdit.getText().toString();
                //开始导出
                if (exportTypeList.get(0).equals("导出为图片")) {
                    if (clickResumeBean == null) return;
                    if (isVip()) {

                        SaveUtils.saveBitmapForImg(com.kang.resume.preview.PreViewActivity.this, iView.getClipView(), fileName, new SaveUtils.ICreatePdf() {
                            @Override
                            public void createSuccess(String path) {
                                ShareFileUtils.shareImage(com.kang.resume.preview.PreViewActivity.this, path);
                            }
                        });
                    } else {
                        goPay();
                    }

                } else {
                    if (clickResumeBean == null) return;
                    if (isVip()) {
                        showLoad(true);
                        SaveUtils.saveBitmapForPdf(com.kang.resume.preview.PreViewActivity.this, pageView, fileName, A4width, A4height, new SaveUtils.ICreatePdf() {
                            @Override
                            public void createSuccess(String path) {
                                showLoad(false);
                                ShareFileUtils.shareFile(com.kang.resume.preview.PreViewActivity.this, path);
                            }
                        });
                    } else {
                        goPay();
                    }
                }
            }
        });

    }

    private boolean isVip(){
        return false;
    }

    private void updateNameEditText(EditText editText) {
        String nameStr = "";
        BaseInfoBean infoBean = clickResumeBean.getBaseInfo();

        for (int i = 0; i < exportNameList.size(); i++) {
            String str = exportNameList.get(i);
            String tempStr = "";
            if (str.equals("求职意向")) {
                JobIntentionBean positionBean = clickResumeBean.getJobIntention();
                if (positionBean != null) {
                    tempStr = positionBean.getPosition();
                }
            } else if (str.equals("姓名")) {
                if (infoBean != null) {
                    tempStr = infoBean.getName();
                }
            } else if (str.equals("工作经验")) {
                if (infoBean != null) {
                    tempStr = infoBean.getStartWorkTime();
                }
            } else if (str.equals("电话号码")) {
                if (infoBean != null) {
                    tempStr = infoBean.getPhone();
                }
            } else if (str.equals("邮箱")) {
                if (infoBean != null) {
                    tempStr = infoBean.getEmail();
                }
            } else if (str.equals("学历")) {
                List<EducationBean> educations = clickResumeBean.getEducations();
                if (educations != null && educations.size() != 0) {
                    EducationBean eduBean = educations.get(educations.size() - 1);
                    tempStr = eduBean.getRecord();
                }
            } else if (str.equals("期望城市")) {
                JobIntentionBean positionBean = clickResumeBean.getJobIntention();
                if (positionBean != null) {
                    tempStr = positionBean.getCity();
                }
            }

            if (nameStr.equals("")) {
                nameStr = tempStr;
            } else {
                if (!tempStr.equals("")) {
                    nameStr += ("-" + tempStr);
                }
            }
        }
        editText.setText(nameStr);
    }

    private void showPopWindow() {
        View rootView = LayoutInflater.from(com.kang.resume.preview.PreViewActivity.this).inflate(R.layout.preview_activity, null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.6f);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
