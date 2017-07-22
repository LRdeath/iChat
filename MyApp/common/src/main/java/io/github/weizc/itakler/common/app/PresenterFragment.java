package io.github.weizc.itakler.common.app;

import android.content.Context;

import io.github.weizc.itakler.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/7/21.
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter>extends Fragment
        implements BaseContract.View<Presenter>{
    protected Presenter mPresenter;

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
    protected abstract void initPresenter();

    @Override
    public void showError(int str) {
        //显示错误
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
        // TODO: 2017/7/21
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }
}
