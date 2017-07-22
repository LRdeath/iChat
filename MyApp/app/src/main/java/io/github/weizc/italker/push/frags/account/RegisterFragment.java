package io.github.weizc.italker.push.frags.account;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private AccountTrigger trigger;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }
    //切换到注册页面
    @OnClick(R.id.txt_go_login)
    void go_register(){
        trigger.triggerView();
    }
    //进行用户登录
    @OnClick(R.id.btn_submit)
    void submit(){
        MainActivity.show(getContext());
    }

}
