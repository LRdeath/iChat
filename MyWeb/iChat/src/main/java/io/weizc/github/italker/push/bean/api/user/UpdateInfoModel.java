package io.weizc.github.italker.push.bean.api.user;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import io.weizc.github.italker.push.bean.db.User;

/**
 * @author Vzer
 * @version 1.0.0
 *          Date: 2017/6/27.
 */
public class UpdateInfoModel {
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

    public static boolean check(UpdateInfoModel model) {
        //model不能为null
        //有一个参数改变就返回true
        return model != null &&
                (!Strings.isNullOrEmpty(model.name) ||
                !Strings.isNullOrEmpty(model.desc) ||
                !Strings.isNullOrEmpty(model.portrait) ||
                model.sex != 0);
    }

    /**
     * 把当前信息填充到用户中
     * @param self User Model
     * @return User Model
     */
    public User updateToUser(User self) {
        if (!Strings.isNullOrEmpty(name)){
            self.setName(name);
        }
        if (!Strings.isNullOrEmpty(portrait)){
            self.setPortrait(portrait);
        }
        if (!Strings.isNullOrEmpty(desc)){
            self.setDescription(desc);
        }
        if (sex!=0){
            self.setSex(sex);
        }

        return self;

    }
}
