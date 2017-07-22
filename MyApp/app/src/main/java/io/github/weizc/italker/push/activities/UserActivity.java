package io.github.weizc.italker.push.activities;

import android.content.Context;
import android.content.Intent;

import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.frags.user.UpdateInfoFragment;

public class UserActivity extends Activity {
    private UpdateInfoFragment mCurFragment;
    public static void show(Context context){
        context.startActivity(new Intent(context,UserActivity.class));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.account_containter,mCurFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurFragment.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }

}
