package io.github.weizc.italker.factory.net;

import io.github.weizc.italker.factory.model.api.RspModel;
import io.github.weizc.italker.factory.model.api.account.AccountRspModel;
import io.github.weizc.italker.factory.model.api.account.LoginModel;
import io.github.weizc.italker.factory.model.api.account.RegisterModel;
import io.github.weizc.italker.factory.model.api.user.UserUpdateeModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Vzer.
 * Date: 2017/7/22.
 */

public interface RemoteService {

    /**
     * 注册接口
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     * @param model 传入的是LoginModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 登录接口
     * @param pushId 设备Id
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true,value = "pushId") String pushId);

    //用户更新网络请求接口
    @PUT("user")
    Call<RspModel<AccountRspModel>> userUpdate(@Body UserUpdateeModel model);
}
