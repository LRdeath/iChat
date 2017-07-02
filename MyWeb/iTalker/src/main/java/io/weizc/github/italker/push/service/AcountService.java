package io.weizc.github.italker.push.service;

import io.weizc.github.italker.push.bean.api.account.AccountRspModel;
import io.weizc.github.italker.push.bean.api.account.LoginModel;
import io.weizc.github.italker.push.bean.api.account.RegisterModel;
import io.weizc.github.italker.push.bean.api.base.ResponseModel;
import io.weizc.github.italker.push.bean.api.user.UserInfoModel;
import io.weizc.github.italker.push.bean.db.User;
import io.weizc.github.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vzc on 2017/6/5.
 */
@Path("/account")
public class AcountService {
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
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk((AccountRspModel) rspModel);
        } else {
            //登录异常
            return ResponseModel.buildLoginError();
        }
    }
}
