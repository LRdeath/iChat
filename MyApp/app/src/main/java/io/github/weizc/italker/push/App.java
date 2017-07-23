package io.github.weizc.italker.push;

import io.github.weizc.itakler.common.app.Application;
import io.github.weizc.italker.factory.Factory;

/**
 * @author Vzc  Email:newlr@foxmail.com
 * @version 1.0.0
 * @Date 2017/6/19.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //调用Factory进行初始化
        Factory.setup();
    }
}
