package io.github.weizc.itakler.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Vzc on 2017/6/7.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View mRoot;
    protected Unbinder mRootUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layId = getContentLayoutId();
            //初始化当前布局的根布局，但是不在创建时就添加到container里面去  设为false
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                //把当前布局的父布局移除掉
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 得到当前界面资源id
     *
     * @return 资源id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View rootView) {
        mRootUnbinder = ButterKnife.bind(this, rootView);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化相关参数Bundle
     *
     * @param extras 参数Bundle
     */
    protected void initArgs(Bundle extras) {
    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回true 代表我已经处理的返回逻辑，acticity不用自己finish
     * 返回false Activity走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
