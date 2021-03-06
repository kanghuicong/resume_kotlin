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
import com.kang.resume.base.BaseActivity;
import com.kang.resume.base.BaseViewModel;
import com.kang.resume.base.ValueConfig;
import com.kang.resume.base.ViewModelProviderFactory;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.EducationBean;
import com.kang.resume.bean.JobIntentionBean;
import com.kang.resume.bean.ResumeInfoBean;
;
import com.kang.resume.custom.ExportDialog;
import com.kang.resume.custom.ISave;
import com.kang.resume.databinding.ViewPreviewBinding;
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
import com.lxj.xpopup.interfaces.OnConfirmListener;
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
 * ????????????
 * author:kanghuicong
 */
public class PreViewActivity extends BaseActivity<ViewPreviewBinding, PreviewViewModel> implements View.OnClickListener {
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
    private ImageView ivBTop;
    private ImageView ivBBottom;
    private ImageView ivTTop;
    private ImageView ivTBottom;
    private ImageView ivQuestion;

    private FragmentManager fm;
    private AppFragment appFragment;
    private ThemeFragment themeFragment;
    private SaveFragment saveFragment;


    private LoadingPopupView loadingPopup;
    //????????????
    int position = 0;

    //????????????
    private ResumeInfoBean clickResumeBean;
    private List<AllModuleBean> moduleBeanList = new ArrayList<>();
    List<ResumeInfoBean> list;

    PreviewUtils previewUtils;
    List<View> pageView = new ArrayList<>();
    MyPageAdapter adapter;

    //page??????
    private int A4width;
    //page??????
    private int A4height;
    //page??????
    private int A4num;
    //page?????????
    private int A4pdHeight;

    //???????????????
    private boolean isCover = false;


    private int moveY;
    private IView iView = null;
    TemplateBean templateBean;


    CoverUtils coverUtils;


    @Override
    public int initLayout() {
        return R.layout.view_preview;
    }

    @NotNull
    @Override
    public PreviewViewModel initViewModel() {
        mBinding.setVm(ViewModelProviderFactory.getViewModel(activity, new PreviewViewModel()));
        return mBinding.getVm();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initBinding(@NotNull ViewPreviewBinding $this$initBinding) {

//        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();

//        setContentView(R.layout.view_preview);

        llPreview = findViewById(R.id.ll_preview);
        scPreview = findViewById(R.id.sc_preview);
        tvPage = findViewById(R.id.tv_page);
        ivDefault = findViewById(R.id.iv_default);
        ivBTop = findViewById(R.id.iv_b_top);
        ivBBottom = findViewById(R.id.iv_b_bottom);
        ivTTop = findViewById(R.id.iv_t_top);
        ivTBottom = findViewById(R.id.iv_t_bottom);
        ivQuestion = findViewById(R.id.iv_question);


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
        ivBTop.setOnClickListener(this);
        ivTTop.setOnClickListener(this);
        ivBBottom.setOnClickListener(this);
        ivTBottom.setOnClickListener(this);
        ivQuestion.setOnClickListener(this);

        //??????EventBus
        EventBus.getDefault().register(this);
        //????????????

        list = (List<ResumeInfoBean>) getIntent().getExtras().getSerializable("json");

        if (list == null || list.size() == 0) {
            finish();
            RxToast.normal("???????????????????????????");
            return;
        }
        TemplateBean templateBean = (TemplateBean) getIntent().getExtras().getSerializable("template");
        RxLogTool.e("templateBean:" + templateBean.getClassName());
        //?????????????????????????????????
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
            RxToast.normal("??????????????????????????????????????????????????????");
            finish();
            return;
        }

        //??????Fragment
        fm = getSupportFragmentManager();
        appFragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        appFragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fl_main, appFragment).addToBackStack(null).commit();

        previewUtils = new PreviewUtils(this);

        //??????????????????
        llPreview.post((() -> {
            //???????????????
            int height = llPreview.getHeight();
            int width = llPreview.getWidth();
            //???????????????
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

            //??????????????????
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rlWidth, rlHeight);
            lp.gravity = Gravity.CENTER;
            llPreview.setLayoutParams(lp);
            //????????????????????? ???????????????1/55
            Config.setTextSize(rlWidth / 55);
            //?????????
            Config.setA4Padding(Config.textSize * 3);

            coverUtils = new CoverUtils(this, rlWidth, rlHeight, Config.textSize);

            //????????????????????????
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

    int index = 0;
    int defaultEndY = 0;

    //??????
    private void initData(int position, TemplateBean templateBean) {
        this.position = position;
        this.templateBean = templateBean;
        showLoad(true);
        ivDefault.setVisibility(View.VISIBLE);

        //???????????????????????????????????????????????????
        iView.getView().setVisibility(View.INVISIBLE);

        //????????????position?????????
        list.get(position).setClick(true);
        //?????????????????????
        clickResumeBean = list.get(position);

        moduleBeanList.clear();
//        moduleBeanList.addAll(JSON.parseArray(clickResumeBean.getAllModules(), AllModuleBean.class));

        //previewUtils????????????????????????addViews????????????
        previewUtils.setData(clickResumeBean, templateBean);
        //????????????
        addViews(moduleBeanList, iView);

        //???????????????????????????clPreview??????????????????????????????
        iView.getView().post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
//                RxTool.delayToDo(1000, () -> {
                //??????????????????
                //A4??????
                A4width = iView.getPreviewWidth();
                RxLogTool.e("A4width:" + A4width);
                A4height = iView.getPreviewWidth() * 297 / 210;
                RxLogTool.e("A4height:" + A4height);
                //?????????????????????
                float x = iView.getClipView().getMeasuredHeight();
                A4num = iView.getClipView().getMeasuredHeight() / A4height + ((iView.getClipView().getMeasuredHeight() % A4height == 0) ? 0 : 1);
                RxLogTool.e("iView.getClipView().getMeasuredHeight():" + iView.getClipView().getMeasuredHeight());
                RxLogTool.e("?????????A4num:" + A4num);
                //?????????????????????????????????
                A4pdHeight = A4num * Config.A4Padding * 2 + iView.getClipView().getMeasuredHeight();
                RxLogTool.e("A4pdHeight:" + A4pdHeight);
                //????????????
                A4num = A4pdHeight / A4height + ((A4pdHeight % A4height < 2) ? 0 : 1);
                RxLogTool.e("?????????A4num:" + A4num);
                RxLogTool.e("Config.A4Padding:" + Config.A4Padding);

                RxLogTool.e("A4num:" + A4num + "-----clPreview:" + iView.getPreviewWidth() + "------" + iView.getClipView().getMeasuredHeight());

                //??????pdf?????????
                pageView.clear();


                for (int i = 0; i < A4num; i++) {
                    List<View> viewList = addViews(moduleBeanList, null);

                    //?????????????????????????????????
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
                        RxToast.normal("????????????");
                        finish();
                        return;
                    }
                }
                //??????????????????
                Config.setChooseColor(ColorUtils.hexToColor(templateBean.getXcrMainThemeStr()));
                previewUtils.setColor(pageView, ColorUtils.hexToColor(templateBean.getXcrMainThemeStr()));

                RxLogTool.e("size1:" + pageView.size());
                moveY = 0;


                for (int i = 0; i < pageView.size(); i++) {
                    //?????????views.size()????????????view??????????????????
                    RxLogTool.e("size2:" + pageView.size());

                    int finalI = i;

                    RxTool.delayToDo(0, () -> {

                        int startY;
                        int endY;


                        startY = A4height * finalI - (finalI * 2) * Config.A4Padding - moveY;
//                        startY = 0;
                        endY = A4height * (finalI + 1) - Config.A4Padding * ((finalI + 1) * 2) - moveY;
//
                        RxLogTool.e("endY:" + endY);
                        RxLogTool.e("maxY---A4height:" + A4height + "------Config.A4Padding:" + Config.A4Padding);
                        RxLogTool.e("maxY:" + endY + "------moveY:" + moveY);

                        //?????????????????????????????????maxN??????????????????????????????
                        if (finalI < pageView.size()) {
                            int maxN = ((IView) (pageView.get(finalI))).getLineWidth();

                            if (finalI == 4) {
                                ((IView) (pageView.get(finalI))).getClipView().moveY(-50);
                            }
                            ClipConstraintLayout layout = ((IView) (pageView.get(finalI))).getClipView().setClipView(
                                    0,
                                    startY,
                                    //??????????????????ClipView
                                    ((IView) (pageView.get(finalI))).getClipView().getXWidth(),
                                    endY,
                                    maxN,
                                    finalI,
                                    y -> {
                                        moveY = moveY + y;
                                        RxLogTool.e("setClipView--moveY:" + moveY);

                                        LinearLayout.MarginLayoutParams lp = (LinearLayout.MarginLayoutParams) ((IView) (pageView.get(finalI))).getClipView().getLayoutParams();
                                        lp.topMargin = -(A4height * finalI
                                                - Config.A4Padding * ((finalI == 0 ? (((IView) (pageView.get(finalI))).needTopMargin() ? 1 : 0) : 1 + finalI * 2))
                                                - moveY);
                                        RxLogTool.e("topMargin:" + lp.topMargin);
                                        ((IView) (pageView.get(finalI))).getClipView().setLayoutParams(lp);
                                    });

                            //???????????????????????????????????????????????????
                            if (layout == null) {
                                pageView.remove(pageView.size() - 1);

                                addCover();
                                adapter.notifyDataSetChanged();
                                viewpager.setVisibility(View.VISIBLE);
                                ivDefault.setVisibility(View.GONE);
                                showLoad(false);
                                tvPage.setText("1/" + pageView.size());
                            } else {
                                //??????viewpager
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
                        index = position;
                        tvPage.setText(index + 1 + "/" + pageView.size());
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
        //???moduleBeanList??????????????????view

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

    //????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChoosePreview(ChoosePreviewEvent event) {
        initData(event.getPosition(), templateBean);
    }

    //????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChooseColor(ChooseColorEvent event) {
        Config.setChooseColor(event.getColor());
        previewUtils.setColor(pageView, event.getColor());
    }

    int coverId;
    int coverPosition;

    //????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AddCoverEvent(AddCoverEvent event) {

        coverId = event.coverId;
        coverPosition = event.position;
        isCover = event.position != 0;
        initData(position, templateBean);

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
                        .moveUpToKeyboard(false) //????????????????????????????????????????????????????????????
                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                        .asCustom(new ExportDialog(this, clickResumeBean, new ISave() {

                            @Override
                            public void savePdf(@NotNull String name) {
                                doExport(true, name);
                            }

                            @Override
                            public void saveImg(@NotNull String name) {
                                doExport(false, name);
                            }
                        }))
                        .show();
                break;
            case R.id.iv_b_top:
                if (pageView != null && pageView.size() > index) {
                    ((IView) (pageView.get(index))).getClipView().moveBY(true);
                }
                break;
            case R.id.iv_b_bottom:
                if (pageView != null && pageView.size() > index) {
                    ((IView) (pageView.get(index))).getClipView().moveBY(false);
                }
                break;
            case R.id.iv_t_top:
                if (pageView != null && pageView.size() > index) {
                    ((IView) (pageView.get(index))).getClipView().moveTY(true);
                }
                break;
            case R.id.iv_t_bottom:
                if (pageView != null && pageView.size() > index) {
                    ((IView) (pageView.get(index))).getClipView().moveTY(false);
                }
                break;
            case R.id.iv_question:
                new XPopup.Builder(activity)
                        .dismissOnBackPressed(false)
                        .isDestroyOnDismiss(true)
                        .dismissOnTouchOutside(false)
                        .asConfirm(getString(R.string.tip_preview), getString(R.string.detail_hint_preview), new OnConfirmListener() {
                            @Override
                            public void onConfirm() {

                            }
                        }).show();

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

    private boolean doExport(boolean isPdf, String fileName) {
        mVm.export(clickResumeBean.getResumeId(), () -> {

            runOnUiThread(() -> {
                if (keepPop != null && keepPop.isShow()) keepPop.dismiss();

                if (isPdf) {
                    SaveUtils.saveBitmapForPdf(PreViewActivity.this, pageView, fileName, A4width, A4height, new SaveUtils.ICreatePdf() {
                        @Override
                        public void createSuccess(String path) {
                            ShareFileUtils.shareFile(PreViewActivity.this, path);
                        }
                    });
                } else {
                    SaveUtils.saveBitmapForImg(PreViewActivity.this, iView.getClipView(), fileName, new SaveUtils.ICreatePdf() {
                        @Override
                        public void createSuccess(String path) {
                            ShareFileUtils.shareImage(PreViewActivity.this, path);
                        }
                    });
                }
            });
        });

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


