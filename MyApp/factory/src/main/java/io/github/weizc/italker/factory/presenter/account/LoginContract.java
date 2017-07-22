package io.github.weizc.italker.factory.presenter.account;

import io.github.weizc.itakler.factory.presenter.BaseContract;
import io.github.weizc.itakler.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/7/21.
 */

public interface LoginContract {
    interface View extends BaseContract.View<Presenter>{
        //登录成功
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        //发起一个登录
        void login(String phone,String password);
    }
}
