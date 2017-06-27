package io.weizc.github.italker.push.service;

import io.weizc.github.italker.push.bean.db.User;

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
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(){
        User user = new User();
        user.setName("二狗子");
        user.setSex(1);
        return user;
    }
}
