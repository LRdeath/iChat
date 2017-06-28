package io.weizc.github.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;

/**
 * @author Vzer
 * @version 1.0.0
 *          Date: 2017/6/27.
 */
public class RegisterModel {
    @Expose
    private String account;
    @Expose
    private String name;
    @Expose
    private String password;
    @Expose
    private String pushId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
