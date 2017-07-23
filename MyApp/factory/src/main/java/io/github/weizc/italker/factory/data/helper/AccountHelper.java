package io.github.weizc.italker.factory.data.helper;


import android.text.TextUtils;

import io.github.weizc.itakler.factory.data.DataSource;
import io.github.weizc.italker.factory.Factory;
import io.github.weizc.italker.factory.R;
import io.github.weizc.italker.factory.model.api.RspModel;
import io.github.weizc.italker.factory.model.api.account.AccountRspModel;
import io.github.weizc.italker.factory.model.api.account.LoginModel;
import io.github.weizc.italker.factory.model.api.account.RegisterModel;
import io.github.weizc.italker.factory.model.db.User;
import io.github.weizc.italker.factory.net.Network;
import io.github.weizc.italker.factory.net.RemoteService;
import io.github.weizc.italker.factory.persistence.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;

/**
 * Created by Vzer.
 * Date: 2017/7/22.
 */

public class AccountHelper {

    /**
     * 注册的M层调用
     * @param model 注册的model
     * @param callback 结果回调接口
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback){

        //获取网络请求接口
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        //网络请求回调处理
        call.enqueue(new AccountCallBack(callback));
    }

    public static void bindPush(DataSource.Callback<User> callback) {
        //检查是否为空
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId))return;
        //调用Retrofit对我们的网络请求接口做代理;
        // TODO: 2017/7/23  
    }

    /**
     * M层的登录调用
     * @param model 登录的model
     * @param callback 结果回调接口
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback){
        //获取retrofit
        RemoteService service = Network.remote();
        //获取回调
        Call<RspModel<AccountRspModel>>  call =  service.accountLogin(model);
        //网络请求回调处理
        call.enqueue(new AccountCallBack(callback));
    }

    /**
     * 请求回调的部分封装(匿名内部类)
     */
    private static class AccountCallBack implements Callback<RspModel<AccountRspModel>>{
        final DataSource.Callback<User> callback;

        public AccountCallBack(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
            //请求返回成功,拿到Model
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()){
                //拿到实体
                AccountRspModel model = rspModel.getResult();
                //拿到user
                User user = model.getUser();
                //直接保存,还有事务保存
                user.save();

                //同步到XML持久化中
                Account.login(model);
                //判定绑定状态,是否绑定设备
                if (model.isBind()){
                    //设置绑定状态为true
                    Account.setBind(true);

                    if (callback!=null){
                        //回调P层
                        callback.onDataLoaded(user);
                    }
                } else {
                    // 进行绑定的唤起
                    bindPush(callback);
                }


            }else {
                //错误解析
                Factory.decodeRspCode(rspModel,callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            //网络请求失败
            if (callback!=null){
                callback.onFailLoaded(R.string.data_network_error);
            }
        }
    }

}
