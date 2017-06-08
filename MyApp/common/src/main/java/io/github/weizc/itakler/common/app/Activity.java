package io.github.weizc.itakler.common.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Vzc on 2017/6/7.
 */

public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用初始化窗口
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            int layId = getContentLayoutId();
            setContentView(layId);
            initData();
            initWidget();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {

    }

    /**
     * 初始化相关参数Bundle
     *
     * @param extras 参数Bundle
     * @return 如果参数正确返回true，错误返回false；
     */
    private boolean initArgs(Bundle extras) {
        return true;
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        @SuppressLint("RestrictedApi") List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断是否为空 或者大小为0
        if (fragments != null && fragments.size() != 0) {
            for (Fragment fragment : fragments) {
                //判断是否为我们要处理的Fragment类型
                if (fragment instanceof io.github.weizc.itakler.common.app.Fragment) {
                    if (((io.github.weizc.itakler.common.app.Fragment) fragment).onBackPressed()) {
                        //如果为true Fragment拦截此事件
                        return;
                    }
                }
            }
        }
        super.onBackPressed();

    }
}
