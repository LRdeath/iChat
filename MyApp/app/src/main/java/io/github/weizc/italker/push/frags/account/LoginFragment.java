package io.github.weizc.italker.push.frags.account;


import android.content.Context;
import android.view.View;
import android.widget.EditText;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private AccountTrigger mTrigger;

    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.btn_submit)
    Button mSubmit;
    @BindView(R.id.loading)
    Loading mLoading;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //拿到AccountActivity的引用
        mTrigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initWidget(View rootView) {
        super.initWidget(rootView);

    }
    //切换到注册页面
    @OnClick(R.id.txt_go_register)
    void go_register(){
        mTrigger.triggerView();
    }
    //进行用户登录
    @OnClick(R.id.btn_submit)
    void submit(){
        String phone = edit_phone.getText().toString();
        String password = edit_password.getText().toString();
        MainActivity.show(getContext());
    }

}
