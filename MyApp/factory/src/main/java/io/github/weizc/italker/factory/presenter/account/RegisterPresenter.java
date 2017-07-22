package io.github.weizc.italker.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

import io.github.weizc.itakler.common.Common;
import io.github.weizc.itakler.factory.data.DataSource;
import io.github.weizc.itakler.factory.presenter.BasePresenter;
import io.github.weizc.italker.factory.R;
import io.github.weizc.italker.factory.data.helper.AccountHelper;
import io.github.weizc.italker.factory.model.api.account.RegisterModel;
import io.github.weizc.italker.factory.model.db.User;

/**
 * Created by Administrator on 2017/7/21.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

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
        if (!checkMobile(phone)) {
            //提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            //昵称需要大于俩位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            //密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {
            //对数据进行封装
            RegisterModel model = new RegisterModel(phone, password, name);
            //传递给Model层,进行注册操作
            AccountHelper.register(model, this);
        }
    }

    private boolean checkMobile(String phone) {
        //手机号不为空,并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }

    //P层 注册成功的操作
    @Override
    public void onDataLoaded(User user) {
        final RegisterContract.View view = getmView();
        //view被销毁,结束当前
        if (view == null) return;

        //当前回调可能在子线程中,进行线程的切换
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //告知V层
                view.registerSuccess();
            }
        });
    }

    //注册失败
    @Override
    public void onFailLoaded(final int error) {
        final RegisterContract.View view = getmView();
        //view被销毁,结束当前
        if (view == null) return;

        //当前回调可能在子线程中,进行线程的切换
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //告知V层
                //提示错误信息
                view.showError(error);
            }
        });
    }
}
