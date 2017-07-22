package io.github.weizc.italker.factory.presenter.account;

import io.github.weizc.itakler.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/7/21.
 */

public interface RegisterContract {
    interface View extends BaseContract.View<Presenter>{
        //注册成功
        void registerSuccess();
    }
    interface Presenter extends BaseContract.Presenter{
        //进行注册操作
        void register(String phone,String password,String name);
    }
}
