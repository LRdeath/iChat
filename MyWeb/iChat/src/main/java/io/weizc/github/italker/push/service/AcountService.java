package io.weizc.github.italker.push.service;

import com.google.common.base.Strings;
import io.weizc.github.italker.push.bean.api.account.AccountRspModel;
import io.weizc.github.italker.push.bean.api.account.LoginModel;
import io.weizc.github.italker.push.bean.api.account.RegisterModel;
import io.weizc.github.italker.push.bean.api.base.ResponseModel;
import io.weizc.github.italker.push.bean.db.User;
import io.weizc.github.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vzc on 2017/6/5.
 */
@Path("/account")
public class AcountService extends BaceService{
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        if (!RegisterModel.check(model)) {
            //传入的参数异常
            return ResponseModel.buildParameterError();
        }
        if (UserFactory.fingByphoneUser(model.getAccount()) != null) {
            //已有账户
            return ResponseModel.buildHaveAccountError();
        }
        if (UserFactory.fingByname(model.getName()) != null) {
            //已有用户名
            return ResponseModel.buildHaveNameError();
        }
        //开始注册逻辑
        User user = UserFactory.register(model.getAccount(),
                model.getPassword(),
                model.getName());
        if (user != null) {
            //如果pushId不为空，就进行绑定操作
            if (!Strings.isNullOrEmpty(model.getPushId())){
                return bind(user,model.getPushId());
            }
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk((AccountRspModel) rspModel);
        } else {
            //注册异常
            return ResponseModel.buildRegisterError();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            //传入的参数异常
            return ResponseModel.buildParameterError();
        }
        if (UserFactory.fingByphoneUser(model.getAccount()) == null) {
            //账户不存在
            return ResponseModel.buildNotFoundUserError("User is not exist!");
        }

        //开始登录逻辑
        User user = UserFactory.login(model.getAccount(),
                model.getPassword());

        if (user != null) {
            //如果pushId不为空，就进行绑定操作
            if (!Strings.isNullOrEmpty(model.getPushId())){
              return bind(user,model.getPushId());
            }
            // 返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            //登录异常
            return ResponseModel.buildLoginError();
        }
    }

    //绑定设备id
    @POST
    @Path("/bind/{pushid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> bind(@HeaderParam("token") String token,
                                               @PathParam("pushid") String pushid) {
        if(Strings.isNullOrEmpty(token)||Strings.isNullOrEmpty(pushid)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }
        //拿到自己的个人信息
        User self = getSelf();
        return bind(self,pushid);
    }

    /**
     * 绑定的操作
     * @param self user自己
     * @param pushid PushId
     * @return user
     */
    public ResponseModel<AccountRspModel> bind(User self, String pushid){
        //进行设备绑定的操作
        User user = UserFactory.bindPushId(self,pushid);
        //绑定失败则是服务器异常
        if (user == null){
            return ResponseModel.buildServiceError();
        }
        AccountRspModel rspModel = new AccountRspModel(user,true);
        return ResponseModel.buildOk(rspModel);
    }
}
