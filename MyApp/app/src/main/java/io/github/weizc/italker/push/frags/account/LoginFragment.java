package io.github.weizc.italker.push.frags.account;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.itakler.common.app.PresenterFragment;
import io.github.weizc.italker.factory.presenter.account.LoginContract;
import io.github.weizc.italker.factory.presenter.account.LoginPresenter;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements LoginContract.View {

    private AccountTrigger mTrigger;//切换fragment开关
    //private LoginContract.Presenter mPresenter;//

    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.btn_submit)
    Button mSubmit;
    @BindView(R.id.txt_go_register)
    TextView mGoRegister;
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

    /**
     * 创建登陆的P层,进行双向绑定
     * @return
     */
    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
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

        mPresenter.login(phone,password);//调用Presenter层进行登录操作
    }

    //登陆成功后进入主页面,结束当前页面
    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }


    @Override
    public void showLoading() {
        super.showLoading();
        //正在进行登陆,界面不可以操作
        //开始Loading
        mLoading.start();
        //让控件不可操作
        mGoRegister.setEnabled(false);
        edit_password.setEnabled(false);
        edit_phone.setEnabled(false);
        mSubmit.setEnabled(false);
    }

    @Override
    public void showError(int str) {
        super.showError(str);

        mLoading.stop();
        //恢复界面操作
        mGoRegister.setEnabled(true);
        edit_password.setEnabled(true);
        edit_phone.setEnabled(true);
        mSubmit.setEnabled(true);
    }
}
