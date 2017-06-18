package io.github.weizc.italker.push.activities;

import android.content.Context;
import android.content.Intent;

import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.frags.account.UpdateInfoFragment;


/**
 * 用户界面Activity
 *
 * @author Vzc
 * @version 1.0.0
 * @Date 2017/6/18.
 */
public class AccountActivity extends Activity {


    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
       UpdateInfoFragment mFragment  = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.account_containter,mFragment)
                .commit();
    }
}
