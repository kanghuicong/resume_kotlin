package com.kang.resume.preview.bean;

/**
 * 类描述：
 * author:kanghuicong
 */
public class AllModuleBean {

    String moduleType;
    boolean isFixed;
    String name;
    boolean showed;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    @Override
    public String toString() {
        return "AllModuleBean{" +
                "moduleType='" + moduleType + '\'' +
                ", isFixed=" + isFixed +
                ", name='" + name + '\'' +
                ", showed=" + showed +
                '}';
    }
}
