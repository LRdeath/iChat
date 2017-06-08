package io.github.weizc.italker.push;

import android.text.TextUtils;

/**
 * @author Vzc  Email:newlr@foxmail.com
 * @version 1.0.0
 * @Date 2017/6/8.
 */

public class Presenter implements IPresenter {
    private IView mView;

    public Presenter(IView mView) {
        this.mView = mView;
    }

    @Override
    public void search() {
        String inputString = mView.getResultString();
        if (TextUtils.isEmpty(inputString)){
            //为空处理，直接返回
            return;
        }
        int hashCode = inputString.hashCode();
        IUserService service = new UserService();
        String result = "Result:"+service.search(hashCode);

        //数据处理完毕，关闭loading等操作

        mView.setResultString(result);
    }
}
