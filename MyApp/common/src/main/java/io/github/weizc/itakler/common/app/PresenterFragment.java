package io.github.weizc.itakler.common.app;

import android.content.Context;

import io.github.weizc.itakler.factory.presenter.BaseContract;

/**
 * Created by Vzer .
 * Date: 2017/7/21.
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter>extends Fragment
        implements BaseContract.View<Presenter>{
    protected Presenter mPresenter;//子类可复用的Persenter

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //在界面onAttach之后就触发初始化Presenter
        initPresenter();
    }
    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        //显示错误
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
        // TODO: 显示一个Loading
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }
}
