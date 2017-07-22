package io.github.weizc.italker.push.frags.account;


import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.itakler.common.app.PresenterFragment;
import io.github.weizc.itakler.factory.data.DataSource;
import io.github.weizc.italker.factory.presenter.account.RegisterContract;
import io.github.weizc.italker.factory.presenter.account.RegisterPresenter;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.activities.MainActivity;

import static io.github.weizc.italker.factory.presenter.account.RegisterContract.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View {
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.btn_submit)
    Button mSubmit;
    @BindView(R.id.txt_go_login)
    TextView mGoRegister;
    @BindView(R.id.loading)
    Loading mLoading;

    private AccountTrigger mTrigger;//切换到LoginFragment触发器

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTrigger = (AccountTrigger) context;
    }

    //V层和P层进行绑定
    @Override
    protected Presenter initPresenter() {

        return new RegisterPresenter(this);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }
    //切换到注册页面
    @OnClick(R.id.txt_go_login)
    void go_register(){
        mTrigger.triggerView();
    }
    //进行用户注册
    @OnClick(R.id.btn_submit)
    void submit(){
        String phone = edit_phone.getText().toString();
        String password = edit_password.getText().toString();
        String name = edit_name.getText().toString();
        mPresenter.register(phone,password,name);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //正在进行注册,界面不可以操作
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
        //结束Loading
        mLoading.stop();
        //恢复界面操作
        mGoRegister.setEnabled(true);
        edit_password.setEnabled(true);
        edit_phone.setEnabled(true);
        mSubmit.setEnabled(true);
    }

    @Override
    public void registerSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }



}
