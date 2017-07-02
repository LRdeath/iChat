package io.weizc.github.italker.push.factory;

import io.weizc.github.italker.push.bean.db.User;
import io.weizc.github.italker.push.utils.Hib;
import io.weizc.github.italker.push.utils.TextUtil;

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
     *
     * @param account 手机号
     * @param password 密码
     * @return user
     */
    public static User login(String account, String password) {
        //对用户名和密码 进行处理
       final String  phone = account.trim();
       final String encodePassword = encodepassword(password);

        String finalPassword = password;
        User user = Hib.query(session -> (User)session.createQuery("from User where phone=:phone and password=:password")
        .setParameter("phone",phone)
        .setParameter("password", encodePassword)
        .uniqueResult());
        //对找到的user进行登陆操作
        if (user!=null) user = login(user);
        return user;
    }

    /**
     * 对用户进行登陆操作
     * 本质上是对Token进行操作
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
        });    }

    /**
     * 注册账户
     * 注册操作需要写入数据库，返回数据库中的User
     * @param account 账户
     * @param password 密码
     * @param name 用户名
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
     * @param account 手机号
     * @param password 密码
     * @param name 用户名
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
     * @param password
     * @return
     */
    private static String encodepassword(String password) {
        //MD5非对称加密
        password = TextUtil.getMD5(password);
        //在进行一次对称的Base64加密
        return TextUtil.encodeBase64(password);
    }



}
