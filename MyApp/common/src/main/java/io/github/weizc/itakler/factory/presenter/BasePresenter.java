package io.github.weizc.itakler.factory.presenter;

/**
 * Created by Administrator on 2017/7/21.
 */

/**
 * MVP中 基本Presenter层
 * @param <T>
 */
public class BasePresenter<T extends BaseContract.View>implements BaseContract.Presenter {
    protected T mView;

    public BasePresenter(T view) {
        setView(view);
    }
    /**
     * P层和V层的绑定操作
     * @param view
     */
    @SuppressWarnings("unchecked")
    protected void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    /**
     * 子类获取View的方法
     * 子类不允许复写
     * @return
     */
    protected final T getmView(){
        return mView;
    }

    @Override
    public void start() {
        //开始的时候进行Loading调用
        T view = mView;
        if (view!=null){
            view.showLoading();
        }
    }

    /**
     * 销毁方法,防止内存泄漏
     */
    @SuppressWarnings("unchecked")
    @Override
    public void destory() {
        T view = mView;
        mView = null;
        if (view!=null){
            //把Presenter设置为NULL
            view.setPresenter(null);
        }
    }


}
