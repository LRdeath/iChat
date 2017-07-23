package io.github.weizc.italker.push;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.view.View;

import net.qiujuer.genius.res.Resource;

import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.italker.factory.persistence.Account;
import io.github.weizc.italker.push.activities.AccountActivity;
import io.github.weizc.italker.push.activities.MainActivity;
import io.github.weizc.italker.push.frags.assist.PermissionsFragment;

public class LaunchActivity extends Activity {

    private ColorDrawable mBgDrawable;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //拿到根布局
        View root = findViewById(R.id.activity_launch);
        //获取颜色
        int color = getResources().getColor(R.color.colorPrimary);
        //创建一个Drawable
        ColorDrawable drawable = new ColorDrawable(color);
        //设置给背景
        root.setBackground(drawable);
        mBgDrawable = drawable;
    }

    @Override
    protected void initData() {
        super.initData();
        //动画进入到50%等待PushId获取到
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        });
    }

    private void waitPushReceiverId() {
        if (Account.isLogin()) {
            //已经登录的情况下,判断是否绑定
            //如果没有绑定则等待广播接收器进行绑定
            if (Account.isBind()) {
                skip();
                return;
            }
        } else {
            //没有登录
            //如果拿到了PushId,没有登录是不能绑定PushId
            if (!TextUtils.isEmpty(Account.getPushId())) {
                //跳转
                skip();
                return;
            }
        }

        //循环等待拿到 pushId
        getWindow().getDecorView()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitPushReceiverId();
                    }
                }, 500);
    }

    /**
     * 在跳转之前需要把剩下的%50进行完成
     */
    private void skip() {
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });
    }

    /**
     * 真实的跳转
     */
    private void reallySkip() {
        //权限检测,跳转
        if (PermissionsFragment.hadAllPermission(this, getSupportFragmentManager())) {
            //检测是跳转到主页还是登录页面
            if (Account.isLogin()) {
                MainActivity.show(this);
            } else {
                AccountActivity.show(this);
            }
            finish();
        }
    }

    /**
     * 背景设置一个属性动画
     *
     * @param endProgress 动画终止的百分比
     * @param endCallBack 动画结束时回调
     */
    private void startAnim(float endProgress, final Runnable endCallBack) {
        //获取最终颜色
        int finalColor = Resource.Color.WHITE;
        //运算当前进度的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress, mBgDrawable.getColor(), finalColor);

        //构建一个属性动画
        ValueAnimator animator = ObjectAnimator.ofObject(this, property, evaluator, endColor);
        animator.setDuration(1500);//设置动画时间
        animator.setIntValues(mBgDrawable.getColor(), endColor);
        //动画监听,结束时回调;
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                endCallBack.run();
            }
        });
        animator.start();
    }

    private final Property<LaunchActivity, Object> property = new Property<LaunchActivity, Object>(Object.class, "color") {
        @Override
        public Object get(LaunchActivity object) {
            return object.mBgDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }
    };


}
