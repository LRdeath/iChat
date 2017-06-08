package io.github.weizc.italker.push;

import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Activity;

public class MainActivity extends Activity implements IView {
    @BindView(R.id.tv_main)
    TextView tv_mian;
    @BindView(R.id.et_main)
    EditText editText;
    IPresenter mPresenter;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.bt_main)
    public void submit() {
        if (mPresenter == null) mPresenter = new Presenter(this);
        mPresenter.search();
    }

    @Override
    public void setResultString(String result) {
        tv_mian.setText(result);
    }

    @Override
    public String getResultString() {
        return String.valueOf(editText.getText());
    }
}
