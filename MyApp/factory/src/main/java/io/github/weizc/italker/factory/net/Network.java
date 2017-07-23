package io.github.weizc.italker.factory.net;

import android.accounts.Account;
import android.text.TextUtils;

import java.io.IOException;

import io.github.weizc.itakler.common.Common;
import io.github.weizc.italker.factory.Factory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vzer.
 * Date: 2017/7/22.
 */

public class Network {

    private static Network instance;
    private Retrofit retrofit;
    static {
        instance = new Network();
    }
    private Network() {
    }

    public static Retrofit getRetrofit(){
        if (instance.retrofit!=null)
            return instance.retrofit;

        //得到一个Ok Client
        OkHttpClient client = new OkHttpClient.Builder()
                //给所有请求添加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();//拿到我们的请求
                        Request.Builder builder = original.newBuilder();//重新进行build

                        builder.addHeader("Content-Type","application/json");
                        Request newRequest = builder.build();
                        //返回
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        instance.retrofit = new Retrofit.Builder()
                .baseUrl(Common.Constance.API_URL)
                //设置client
                .client(client)
                //设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return  instance.retrofit;
    }

    /**
     * 返回一个网络请求接口
     * @return
     */
    public static RemoteService remote(){
        return Network.getRetrofit().create(RemoteService.class);
    }
}
