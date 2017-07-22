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
        //获取retrofit
        Retrofit retrofit = Network.getRetrofit();
        //retrofit和网络接口绑定
        RemoteService service = retrofit.create(RemoteService.class);
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call,
                                   Response<RspModel<AccountRspModel>> response) {
                //请求返回成功
                //从返回中获取我们的全局Model,内部是使用的Gson进行解析
                RspModel<AccountRspModel> rspModel = response.body();

                if (rspModel.success()){
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();
                    //获取我的信息
                    User user = accountRspModel.getUser();
                    //保存
                    user.save();

                    //同步到XML持久化中
                    Account.login(accountRspModel);
                    //判断绑定状态,是否绑定设备
                    if (accountRspModel.isBind()){
                        //设置绑定状态为True
                        Account.setBind(true);
                        //然后回调P层
                        if (callback!=null){
                            callback.onDataLoaded(user);
                        }else {
                            //进行绑定的唤起
                            bindPush(callback);
                        }
                    }
                }else {
                    //错误解析
                    Factory.decodeRspCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                //网络请求失败
                if (callback!=null)
                    callback.onFailLoaded(R.string.data_network_error);
            }
        });
    }

    private static void bindPush(DataSource.Callback<User> callback) {
        //检查是否为空
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId))return;

    }

    /**
     * 登录的M层调用
     * @param model 登录的model
     * @param callback 结果回调接口
     */
    public static void login(LoginModel model, DataSource.Callback<User> callback){

    }
}
