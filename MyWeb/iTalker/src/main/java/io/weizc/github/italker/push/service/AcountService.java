package io.weizc.github.italker.push.service;

import io.weizc.github.italker.push.bean.api.account.RegisterModel;
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
    @GET
    @Path("/login")
    public String get(){
        return "YOU get the login.";
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfoModel post(RegisterModel model){
        UserInfoModel user = new UserInfoModel();
        if (UserFactory.fingByphoneUser(model.getAccount())!=null){
            user.setName("手机号重复！");
            return user;
        }
        if (UserFactory.fingByname(model.getName())!=null){
            user.setName("用户名重复！");
            return user;
        }
        UserFactory.register(model.getAccount(),model.getPassword(),model.getName());
        user.setName(model.getName());
        user.setSex(1);
        return user;
    }
}
