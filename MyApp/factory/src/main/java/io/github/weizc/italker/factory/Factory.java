package io.github.weizc.italker.factory;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.github.weizc.itakler.common.app.Application;

/**
 * @author Vzc  Email:newlr@foxmail.com
 * @version 1.0.0
 * @Date 2017/6/22.
 */

public class Factory {
    //单例模式
    private static final Factory instance;
    private final Executor executor;
    static {
        instance = new Factory();
    }
    private Factory(){
        executor = Executors.newFixedThreadPool(4);
    }

    /**
     * 得到全局的Application
     * @return Application
     */
    public static Application app() {
        return Application.getInstance();
    }
    public static void runOnAsync(Runnable runnable){
        //拿到单例，得到线程池，然后异步执行
        instance.executor.execute(runnable);
    }

}
