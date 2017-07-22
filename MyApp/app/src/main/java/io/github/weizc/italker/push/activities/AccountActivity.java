package io.github.weizc.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;
import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.frags.account.AccountTrigger;
import io.github.weizc.italker.push.frags.account.LoginFragment;
import io.github.weizc.italker.push.frags.account.RegisterFragment;
import io.github.weizc.italker.push.frags.user.UpdateInfoFragment;


/**
 * 用户登陆界面Activity
 *
 * @author Vzc
 * @version 1.0.0
 * @Date 2017/6/18.
 */
public class AccountActivity extends Activity implements AccountTrigger{
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;
    private Fragment mCurFragment;
    @BindView(R.id.im_bg)
    ImageView mBg;

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
        //初始化Fragment
        mCurFragment = mLoginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.account_containter,mCurFragment)
                .commit();
        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView,GlideDrawable>(mBg) {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //拿到glide的Drawable
                        Drawable drawable = glideDrawable.getCurrent();
                        //使用适配器进行包装
                        drawable = DrawableCompat.wrap(drawable);
                        drawable.setColorFilter(UiCompat.getColor(getResources(),R.color.colorAccent),
                                PorterDuff.Mode.SCREEN);//设置着色的效果和颜色，蒙版模式
                        //设置给ImageView
                        this.view.setImageDrawable(drawable);
                    }
                });
    }

    @Override
    public void triggerView() {
        Fragment fragment;
        //判断当前的Fragment
        if (mCurFragment==mLoginFragment){
            if (mRegisterFragment==null){
                //第一次加载后就不为null
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        }else {
            fragment = mLoginFragment;
        }
        //重新赋值当前正在显示的Fragment
        mCurFragment = fragment;
        //切换显示
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.account_containter,mCurFragment)
                .commit();
    }
}
