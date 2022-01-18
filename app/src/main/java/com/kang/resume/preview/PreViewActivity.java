package com.kang.resume.preview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.kang.resume.custom.ExportDialog;
import com.kang.resume.custom.ISave;
import com.kang.resume.http.ApiResponse;
import com.kang.resume.http.ApiService;
import com.kang.resume.http.HttpRequest;
import com.kang.resume.http.RetrofitClient;
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
import com.kang.resume.pro.IHttp;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.vondear.rxtool.RxBarTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxTool;
import com.vondear.rxtool.view.RxToast;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;


/**
 * 类描述：
 * author:kanghuicong
 */
public class PreViewActivity extends FragmentActivity implements View.OnClickListener {
    private NestedScrollView scPreview;
    private ImageView ivDefault;

    private ConstraintLayout llPreview;
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


    private LoadingPopupView loadingPopup;
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

        tvApplication = findViewById(R.id.tv_application);
        tvTheme = findViewById(R.id.tv_theme);
        tvKeep = findViewById(R.id.tv_keep);

        viewpager = findViewById(R.id.viewpager);


        loadingPopup = new XPopup.Builder(this)
                .dismissOnBackPressed(true)
                .dismissOnTouchOutside(false)
                .shadowBgColor(0x00ffffff)
                .isLightNavigationBar(true)
                .isRequestFocus(false)
                .asLoading(getString(R.string.loading));

        llApp.setOnClickListener(this);
        llTheme.setOnClickListener(this);
        llKeep.setOnClickListener(this);

        //注册EventBus
        EventBus.getDefault().register(this);
        //接收数据

        list = (List<ResumeInfoBean>) getIntent().getExtras().getSerializable("json");

        if (list == null || list.size() == 0) {
            finish();
            RxToast.normal("请先创建一份简历！");
            return;
        }
        TemplateBean templateBean = (TemplateBean) getIntent().getExtras().getSerializable("template");
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

    public void showLoad(boolean isShow) {
        if (isShow) {
            loadingPopup.show();
        } else {
            loadingPopup.dismiss();
        }
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

        for (String ty : list) {
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

    private void doSaveImg(String fileName) {
        if (isVip()) {
            if (keepPop != null && keepPop.isShow()) keepPop.dismiss();
            SaveUtils.saveBitmapForImg(this, iView.getClipView(), fileName, new SaveUtils.ICreatePdf() {
                @Override
                public void createSuccess(String path) {
                    ShareFileUtils.shareImage(com.kang.resume.preview.PreViewActivity.this, path);
                }
            });
        } else {
            goPay();
        }
    }

    private void doSavePdf(String fileName) {
        if (isVip()) {
            if (keepPop != null && keepPop.isShow()) keepPop.dismiss();
            SaveUtils.saveBitmapForPdf(this, pageView, fileName, A4width, A4height, new SaveUtils.ICreatePdf() {
                @Override
                public void createSuccess(String path) {
                    ShareFileUtils.shareFile(com.kang.resume.preview.PreViewActivity.this, path);
                }
            });
        } else {
            goPay();
        }
    }

    //调用flutter，进入会员充值页面
    private void goPay() {

    }

    BasePopupView keepPop;

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
                keepPop = new XPopup.Builder(this)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(new ExportDialog(this, clickResumeBean, new ISave() {

                            @Override
                            public void savePdf(@NotNull String name) {
                                doSavePdf(name);
                            }

                            @Override
                            public void saveImg(@NotNull String name) {
                                doSaveImg(name);
                            }
                        }))
                        .show();
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

    private <T> boolean isVip() {

        return true;
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
