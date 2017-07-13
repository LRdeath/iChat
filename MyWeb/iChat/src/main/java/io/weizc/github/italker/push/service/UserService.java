package io.weizc.github.italker.push.service;

import io.weizc.github.italker.push.bean.api.base.ResponseModel;
import io.weizc.github.italker.push.bean.api.user.UpdateInfoModel;
import io.weizc.github.italker.push.bean.card.UserCard;
import io.weizc.github.italker.push.bean.db.User;
import io.weizc.github.italker.push.factory.UserFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Vzer
 * @version 1.0.0
 *          Date: 2017/7/13.
 */
@Path("/user")
public class UserService extends BaceService{

    @PUT
    //@Path("") //127.0.0.1/api/user 不需要写，就是当前目录
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        // 更新用户信息
        self = model.updateToUser(self);
        self = UserFactory.update(self);
        // 构架自己的用户信息
        UserCard card = new UserCard(self, true);
        // 返回
        return ResponseModel.buildOk(card);
    }
}