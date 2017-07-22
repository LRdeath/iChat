package io.github.weizc.itakler.factory.presenter;

/**
 * Created by Administrator on 2017/7/21.
 */

import android.support.annotation.StringRes;

/**
 * MVP模式中公共的基本契约
 */
public interface BaseContract {
    interface View <T extends Presenter>{
        //公共的,显示一个字符串错误
        void showError(@StringRes int str);

        //公共的,显示进度条
        void showLoading();

        //支持设置一个Presenter
        void setPresenter(T presenter);
    }
    interface Presenter{
        //共用的触发方法
        void start();

        //共用的销毁触发
        void destory();
    }
}
