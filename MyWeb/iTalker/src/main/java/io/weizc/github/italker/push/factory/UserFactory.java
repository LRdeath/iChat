package io.weizc.github.italker.push.factory;

import io.weizc.github.italker.push.bean.db.User;
import io.weizc.github.italker.push.utils.Hib;
import io.weizc.github.italker.push.utils.TextUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Vzer
 * @version 1.0.0
 *          Date: 2017/6/27.
 */
public class UserFactory {

    //通过phone查找User
    public static User fingByphoneUser(String phone){
       return Hib.query(session -> (User) session
                .createQuery("from User where phone=:mphone")
                .setParameter("mphone",phone)
                .uniqueResult());
    }

    //通过name查找User
    public static User fingByname(String name){
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name",name)
                .uniqueResult());
    }

    public static User register(String account, String password, String name){
        //去除账户中的首位空格
        account = account.trim();
        //处理密码
        password = encodepassword(password);

        return createUser(account,password,name);
    }

    private static User createUser(String account, String password, String name) {
        User user = new User();
        //手机号就是账户
        user.setPhone(account);
        user.setPassword(password);
        user.setName(name);
        //数据库存储
        return Hib.query(session -> (User) session.save(user));
    }

    private static String encodepassword(String password) {
        //MD5非对称加密
        password = TextUtil.getMD5(password);
        //在进行一次对称的Base64加密
        return TextUtil.encodeBase64(password);
    }

    public static void myRegister(String account, String password, String name) {
        User user = new User();
        //手机号就是账户
        user.setPhone(account);
        user.setPassword(password);
        user.setName(name);
        Session session = Hib.session();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }
}
