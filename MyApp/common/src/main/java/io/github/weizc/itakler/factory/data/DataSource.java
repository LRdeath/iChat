package io.github.weizc.itakler.factory.data;

import android.support.annotation.StringRes;

/**
 * 数据加载的基础回调接口
 * Created by Vzer.
 * Date: 2017/7/22.
 */

public interface DataSource {


    /**
     * 同时包括成功和失败接口
     * @param <T>需要加载的数据类型
     */
    interface Callback<T> extends SuccessCallback<T>,FailedCallback{
    }

    /**
     * 只关注成功的接口
     * @param <T>需要加载的数据类型
     */
    interface SuccessCallback<T>{

        //数据加载成功
        void onDataLoaded(T t);
    }

    /**
     * 只关注失败的接口
     *
     */
    interface FailedCallback{
        //数据加载失败,返回错误信息
        void onFailLoaded(@StringRes int error);
    }
}
