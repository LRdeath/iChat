package io.weizc.github.italker.push.bean.api.user;

import com.google.gson.annotations.Expose;

/**
 * @author Vzer
 * @version 1.0.0
 *          Date: 2017/6/27.
 */
public class UserInfoModel {
    @Expose
    private String name;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
