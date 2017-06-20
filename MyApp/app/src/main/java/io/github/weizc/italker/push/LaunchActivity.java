package io.github.weizc.italker.push;

import android.os.Bundle;

import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.italker.push.frags.assist.PermissionsFragment;

public class LaunchActivity extends Activity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsFragment.hadAllPermission(this,getSupportFragmentManager())){
            MainActivity.show(this);
            finish();
        }
    }
}
