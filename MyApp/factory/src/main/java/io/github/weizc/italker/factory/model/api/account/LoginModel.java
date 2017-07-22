package io.github.weizc.italker.factory.model.api.account;

/**
 * 登录实用的请求model
 * Created by Vzer.
 * Date: 2017/7/22.
 */

public class LoginModel {
    private String account;
    private String password;
    private String pushId;

    public LoginModel(String account, String password) {
        this(account,password,null);
    }

    public LoginModel(String account, String password, String pushId) {
        this.account = account;
        this.password = password;
        this.pushId = pushId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
