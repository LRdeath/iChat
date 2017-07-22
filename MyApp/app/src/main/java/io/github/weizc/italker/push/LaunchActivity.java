package io.github.weizc.italker.push;

import android.util.Log;

import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.italker.push.activities.AccountActivity;
import io.github.weizc.italker.push.activities.MainActivity;
import io.github.weizc.italker.push.frags.assist.PermissionsFragment;

public class LaunchActivity extends Activity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LaunchActivity","---OnResume---");
        if (PermissionsFragment.hadAllPermission(this,getSupportFragmentManager())){
            AccountActivity.show(this);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LaunchActivity","---OnPause---");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LaunchActivity","---OnPause---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LaunchActivity","---OnDestroy---");
    }
}
