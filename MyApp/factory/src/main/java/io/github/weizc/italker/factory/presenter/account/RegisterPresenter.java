package io.github.weizc.italker.factory.presenter.account;

import android.text.TextUtils;

import java.util.regex.Pattern;

import io.github.weizc.itakler.common.Common;
import io.github.weizc.itakler.factory.presenter.BasePresenter;
import io.github.weizc.italker.factory.R;

/**
 * Created by Administrator on 2017/7/21.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter{
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String password, String name) {
        //调用默认方法,开启loading;
        start();
        //得到view接口
        final RegisterContract.View view = getmView();
        //判断账号密码是否为空
        if (!checkMobile(phone)){
            //提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        }else if (name.length()<2){
            //昵称需要大于俩位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        }else if (password.length()<6){
            //密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        }else {
            //传递数据,进行注册操作
            // TODO: 2017/7/21
        }
    }

    private boolean checkMobile(String phone) {
        //手机号不为空,并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE,phone);
    }
}
