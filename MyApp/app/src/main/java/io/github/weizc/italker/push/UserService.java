package io.github.weizc.italker.push;

/**
 * @author Vzc  Email:newlr@foxmail.com
 * @version 1.0.0
 * @Date 2017/6/8.
 */

public class UserService implements IUserService {
    @Override
    public String search(int hashCode) {
        String result = "User:"+hashCode;
        return result;
    }
}
