package io.github.weizc.italker.factory.presenter.account;

import android.text.TextUtils;

import java.util.regex.Pattern;

import io.github.weizc.itakler.common.Common;
import io.github.weizc.itakler.factory.presenter.BasePresenter;
import io.github.weizc.italker.factory.R;

/**
 * Created by Administrator on 2017/7/21.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }


    @Override
    public void login(String phone, String password) {
        start();
        final LoginContract.View view = getmView();
        //判断账号密码是否填写正确
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            //账号密码为空
            view.showError(R.string.data_account_login_invalid_parameter);
        } else if (!checkMobile(phone) && password.length() < 6) {
            //格式错误
            view.showError(R.string.data_account_login_invalid_validate);
        } else {
            //网络请求
            //传递数据,进行登录操作
            // TODO: 2017/7/21
        }
    }

    private boolean checkMobile(String phone) {
        // 手机号不为空，并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }
}
