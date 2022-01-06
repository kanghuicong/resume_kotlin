package com.kang.resume.preview.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.kang.resume.base.ValueConfig;
import com.kang.resume.bean.BaseInfoBean;
import com.kang.resume.bean.CertificateBean;
import com.kang.resume.bean.EducationBean;
import com.kang.resume.bean.HobbyBean;
import com.kang.resume.bean.JobIntentionBean;
import com.kang.resume.bean.ProjectBean;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.bean.SkillBean;
import com.kang.resume.bean.WorkExperienceBean;
;
import com.kang.resume.preview.bean.TemplateBean;
import com.kang.resume.preview.adapter.AngleAdapter;
import com.kang.resume.preview.adapter.ExperiencesAdapter;
import com.kang.resume.preview.adapter.HobbiesAdapter;
import com.kang.resume.preview.adapter.PositionAdapter;
import com.kang.resume.preview.adapter.WrapAdapter;
import com.kang.resume.preview.preview.BaseItemView;
import com.kang.resume.preview.pro.IInfo;
import com.kang.resume.preview.pro.IItem;
import com.kang.resume.preview.pro.ITitle;
import com.kang.resume.preview.pro.IView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public class PreviewUtils {
    private ResumeInfoBean clickResumeBean;
    private Context context;
    private int color;
    private int size;
    private TemplateBean templateBean;

    public PreviewUtils(Context context) {
        this.context = context;
    }

    //基本信息
    public View addInfo(String title, LinearLayout llItem) {
        BaseInfoBean data = clickResumeBean.getBaseInfo();
        if (data == null) return null;

        //动态实例化对应的模板类
        IInfo iInfo = getIInfo();
        if (iInfo != null) {
            iInfo.setData(clickResumeBean, size);
            if (llItem != null)
                llItem.addView((View) iInfo);
            return (View) iInfo;
        } else {
            BaseItemView baseItemView = new BaseItemView(context, templateBean);
            baseItemView.setGridManager(2).setInfoData(title, clickResumeBean, size).setColor(color);
            if (llItem != null)
                llItem.addView(baseItemView);
            return baseItemView;
        }


    }


    //求职意向
    public View addPosition(String title, LinearLayout llItem) {
        JobIntentionBean data = clickResumeBean.getJobIntention();
        if (data == null) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);
        List<JobIntentionBean> list = new ArrayList<>();
        list.add(data);

        baseItemView.setData(title, size, new PositionAdapter(list, color, size)).setColor(color);
        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //教育经历
    public View addEducation(String title, LinearLayout llItem) {
        List<EducationBean> educationList = clickResumeBean.getEducations();
        if (TextEmptyUtil.isEmpty(educationList)) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);
        baseItemView.setData(title, size, new ExperiencesAdapter(educationList, color, size, getIItem())).setColor(color);
        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //工作经历
    public View addWork(String title, LinearLayout llItem) {
        List<WorkExperienceBean> workBeanList = clickResumeBean.getWorkExperiences();
        if (TextEmptyUtil.isEmpty(workBeanList)) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);
        baseItemView.setData(title, size, new ExperiencesAdapter(workBeanList, color, size, getIItem())).setColor(color);

        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //项目经验
    public View addProject(String title, LinearLayout llItem) {
        List<ProjectBean> projectBeanList = clickResumeBean.getProjects();
        if (TextEmptyUtil.isEmpty(projectBeanList)) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);
        baseItemView.setData(title, size, new ExperiencesAdapter(projectBeanList, color, size, getIItem())).setColor(color);

        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //专业技能
    public View addSkill(String title, LinearLayout llItem) {
        List<SkillBean> skillBeanList = clickResumeBean.getSkills();
        if (TextEmptyUtil.isEmpty(skillBeanList)) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);
        baseItemView.setGridManager(4).setData(title, size, new AngleAdapter(skillBeanList, color, size, getIItem())).setColor(color);

        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //证书奖状
    public View addRecord(String title, LinearLayout llItem) {
        List<CertificateBean> certificateBeanList = clickResumeBean.getCertificates();
        if (TextEmptyUtil.isEmpty(certificateBeanList)) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);

        List<String> list = new ArrayList<>();
        for (CertificateBean certificateBean : certificateBeanList) {
            list.add(certificateBean.getCertificate());
        }

        baseItemView.setData(title, size, new WrapAdapter(list, size)).setColor(color);

        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //兴趣爱好
    public View addHobbies(String title, LinearLayout llItem) {
        IInfo iInfo = getIInfo();
        if (iInfo != null && iInfo.hasInterest()) {
            return null;
        }
        List<HobbyBean> hobbiesList = clickResumeBean.getHobbies();
        if (TextEmptyUtil.isEmpty(hobbiesList)) return null;

        BaseItemView baseItemView = new BaseItemView(context, templateBean);

        List<String> list = new ArrayList<>();
        for (HobbyBean hobbyBean : hobbiesList) {
            list.add(hobbyBean.getHobby());
        }

        baseItemView.setData(title, size, new HobbiesAdapter(context, list, size)).setColor(color);

        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }

    //自我介绍
    public View addAbstract(String title, LinearLayout llItem) {
        if (TextEmptyUtil.isEmpty(clickResumeBean.getSelfEvaluation())) return null;

        List<String> list = new ArrayList<>();
        list.add(clickResumeBean.getSelfEvaluation());

        BaseItemView baseItemView = new BaseItemView(context, templateBean);
        baseItemView.setData(title, size, new WrapAdapter(list, size)).setColor(color);

        if (llItem != null) llItem.addView(baseItemView);
        return baseItemView;
    }


    public PreviewUtils setData(ResumeInfoBean clickResumeBean, TemplateBean templateBean) {
        this.clickResumeBean = clickResumeBean;
        this.color = Config.chooseColor;
        this.size = Config.textSize;
        this.templateBean = templateBean;


        return this;
    }

    private IInfo getIInfo() {

        //动态实例化对应的模板类
        try {
            Class toolClass = Class.forName(ValueConfig.classPageName + templateBean.getClassName() + "." + templateBean.getClassName() + "_info");

            if (toolClass != null) {
                Constructor constructor = toolClass.getConstructor(Context.class);
                constructor.setAccessible(true);
                Object obj = constructor.newInstance(context);
                IInfo iInfo = (IInfo) obj;
                constructor.setAccessible(false);
                return iInfo;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ITitle getIItem() {

        try {
            Class toolClass = Class.forName(ValueConfig.classPageName + templateBean.getClassName() + "." + templateBean.getClassName() + "_TitleView");
            Constructor constructor = toolClass.getConstructor(Context.class);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(context);
            ITitle iTitle = (ITitle) obj;
            constructor.setAccessible(false);
            return iTitle;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();

        }
        return null;
    }

    public void addNullView(List<View> baseItemViews, View view) {
        if (view != null) baseItemViews.add(view);
    }

    public void setColor(List<View> pageView, int color) {
        if (pageView == null) return;

        for (int i = 0; i < pageView.size(); i++) {
            if (pageView.get(i) instanceof IView) {
                for (int n = 0; n < ((IView) (pageView.get(i))).getLinearLayout().getChildCount(); n++) {
                    ((IView) (pageView.get(i))).setColor(color);

                    if (((IView) (pageView.get(i))).getLinearLayout().getChildAt(n) instanceof IItem) {
                        ((IItem) ((IView) (pageView.get(i))).getLinearLayout().getChildAt(n)).setColor(color);
                    } else if (((IView) (pageView.get(i))).getLinearLayout().getChildAt(n) instanceof IInfo) {
                        ((IInfo) ((IView) (pageView.get(i))).getLinearLayout().getChildAt(n)).setColor(color);
                    }
                }
            }
        }
    }
}
