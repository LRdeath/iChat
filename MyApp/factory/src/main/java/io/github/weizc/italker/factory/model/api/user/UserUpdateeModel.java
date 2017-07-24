package io.github.weizc.italker.factory.model.api.user;

/**
 * 用户更新所使用的Model
 * Created by Vzer.
 * Date: 2017/7/24.
 */

public class UserUpdateeModel {
    private String name;
    private String protrait;
    private String desc;
    private int sex;

    public UserUpdateeModel(String name, String protrait, String desc, int sex) {
        this.name = name;
        this.protrait = protrait;
        this.desc = desc;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtrait() {
        return protrait;
    }

    public void setProtrait(String protrait) {
        this.protrait = protrait;
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

    @Override
    public String toString() {
        return "UserUpdateeModel{" +
                "name='" + name + '\'' +
                ", protrait='" + protrait + '\'' +
                ", desc='" + desc + '\'' +
                ", sex=" + sex +
                '}';
    }
}
