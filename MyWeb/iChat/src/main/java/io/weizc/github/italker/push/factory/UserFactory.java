package io.weizc.github.italker.push.factory;

import com.google.common.base.Strings;
import io.weizc.github.italker.push.bean.db.User;
import io.weizc.github.italker.push.utils.Hib;
import io.weizc.github.italker.push.utils.TextUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author Vzer
 * @version 1.0.0
 *          Date: 2017/6/27.
 */
public class UserFactory {

    //通过phone查找User
    public static User fingByphoneUser(String phone) {
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:mphone")
                .setParameter("mphone", phone)
                .uniqueResult());
    }

    //通过name查找User
    public static User fingByname(String name) {
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    /**
     * @param account  手机号
     * @param password 密码
     * @return user
     */
    public static User login(String account, String password) {
        //对用户名和密码 进行处理
        final String phone = account.trim();
        final String encodePassword = encodepassword(password);

        String finalPassword = password;
        User user = Hib.query(session -> (User) session.createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone", phone)
                .setParameter("password", encodePassword)
                .uniqueResult());
        //对找到的user进行登陆操作
        if (user != null) user = login(user);
        return user;
    }

    /**
     * 对用户进行登陆操作
     * 本质上是对Token进行操作
     *
     * @param user 用户
     * @return 登陆更新后的user
     */
    private static User login(User user) {
        //随机生成UUID为token值
        String token = UUID.randomUUID().toString();
        //进行一次base64格式化
        token = TextUtil.encodeBase64(token);
        user.setToken(token);

        return updata(user);
    }

    private static User updata(User user) {
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 注册账户
     * 注册操作需要写入数据库，返回数据库中的User
     *
     * @param account  账户
     * @param password 密码
     * @param name     用户名
     * @return User
     */
    public static User register(String account, String password, String name) {
        //去除账户中的首位空格
        account = account.trim();
        //处理密码
        password = encodepassword(password);

        return createUser(account, password, name);
    }

    /**
     * 注册部分的 新建用户逻辑
     *
     * @param account  手机号
     * @param password 密码
     * @param name     用户名
     * @return user
     */
    private static User createUser(String account, String password, String name) {
        User user = new User();
        //手机号就是账户
        user.setPhone(account);
        user.setPassword(password);
        user.setName(name);
        //数据库存储
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    /**
     * 对密码进行加密处理
     *
     * @param password
     * @return
     */
    private static String encodepassword(String password) {
        //MD5非对称加密
        password = TextUtil.getMD5(password);
        //在进行一次对称的Base64加密
        return TextUtil.encodeBase64(password);
    }


    /**
     * 绑定pushid的操作
     *
     * @param user
     * @param pushid
     * @return
     */
    public static User bindPushId(User user, String pushid) {
        if (Strings.isNullOrEmpty(pushid)) return null;

        Hib.queryOnly(session -> {
            List<User> userList = (List<User>) session
                    .createQuery("from  User WHERE lower(pushId)=:pushid and id!=:userId ")
                    .setParameter("pushid", pushid.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();
            for (User u :
                    userList) {
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });
        if (pushid.equalsIgnoreCase(user.getPushId())) {
            // 如果当前需要绑定的设备Id，之前已经绑定过了
            // 那么不需要额外绑定
            return user;
        } else {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(user.getPushId())) {
                // TODO 推送一个退出消息
            }

            // 更新新的设备Id
            user.setPushId(pushid);
            return update(user);
        }
    }

    /**
     * 更新用户信息到数据库
     *
     * @param user
     * @return
     */
    public static User update(User user) {

        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    //通过token查询到用户信息
    //只能自己使用，查询的是个人信息，非他人信息
    public static User findByToken(String token) {
        return Hib.query(session -> (User) session
                .createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }
}