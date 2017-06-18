package io.github.weizc.italker.push.tools;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.widget.TabHost;


/**
 * 解决对Fragment切换的处理
 *
 * @author Vzc  Email:newlr@foxmail.com
 * @version 1.0.0
 * @Date 2017/6/14.
 */

public class NavHelper<T> {
    //初始化的必须参数
    private final int containerId;
    private final Context context;
    private final FragmentManager fragmentManager;
    private final OnTabChangedListener<T> listener;
    //所有Tab集合
    private final SparseArray<Tab<T>> tabs = new SparseArray<>();

    //当前Tab
    private Tab<T> currentTab;

    public NavHelper( Context context, int containerId,FragmentManager fragmentManager, OnTabChangedListener<T> listener) {
        this.containerId = containerId;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    /**
     * 流式添加tab
     * @param menuId tab对应的菜单id
     * @param tab tab
     * @return
     */
    public NavHelper<T> add(int menuId,Tab<T> tab){
        tabs.put(menuId,tab);
        return this;
    }

    /**
     * 执行菜单点击事件方法
     * @param menuId 触发事件的菜单id
     * @return  false: 不处理当前事件 ，true： 处理当前事件
     */
    public boolean performClickMenu(int menuId){
        Tab<T> tab = tabs.get(menuId);
        if (tab!=null){
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 对TAb进行选择
     * @param tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (currentTab!=null){
            oldTab=currentTab;
            if (tab==currentTab){
                // 如果说当前的Tab就是点击的Tab，
                // 那么我们不做处理
                notifyTabReselect(tab);
                return;
            }
        }
        currentTab =tab;
        doTabChange(oldTab,currentTab);
    }

    /**
     * 对Fragment真实调度的方法
     * @param oldTab
     * @param newTab
     */
    private void doTabChange(Tab<T> oldTab, Tab<T> newTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (oldTab!=null){
            if (oldTab.fragment!=null){
                ft.detach(oldTab.fragment);
            }
        }
        if (newTab.fragment!=null){
            //从fragmentManager缓存空间中重新加载到界面中
            ft.attach(newTab.fragment);
        }else {
            //首次创建
            Fragment fragment = Fragment.instantiate(context,newTab.cls.getName(),null);
            //缓存起来
            newTab.fragment = fragment;
            //添加到Fragmentmanager中
            ft.add(containerId,fragment);
        }
        ft.commit();
        //通知回调监听器
        notifyTabSelect(newTab, oldTab);

    }

    /**
     * 回调我们的监听器
     * @param newTab 新的Tab
     * @param oldTab 旧的Tab
     */
    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        if(listener!=null){
            listener.onTabChanged(newTab,oldTab);
        }
    }

    /**
     * 第二次点击处理
     * @param tab
     */
    public void notifyTabReselect(Tab<T> tab) {
        // TODO: 2017/6/15
    }

    /**
     * 我们的TaB的定义
     * @param <T>
     */
    public static class Tab<T>{
        //Fragment对应的Class信息
        public Class<?> cls;
        //额外的字段，需要自己定义
        public T extra;

        public Tab(Class<?> cls, T extra) {
            this.cls = cls;
            this.extra = extra;
        }
        //内部缓存的Fragment，外部无法使用
        Fragment fragment;
    }

    /**
     * 得到当前的Tab
     * @return
     */
    public Tab<T> getCurrentTab() {
        return currentTab;
    }

    /**
     * 处理完成后回调的接口
     * @param <T>
     */
    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
