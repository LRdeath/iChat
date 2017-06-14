package io.github.weizc.italker.push;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.itakler.common.widget.PortraitView;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.appbar)
    View mLayoutAppBar;
    @BindView(R.id.txt_title)
    TextView mTitle;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.lay_container)
    FrameLayout mContainer;
    @BindView(R.id.btn_action)
    FloatActionButton mAction;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.im_search)
    public void onSearch() {

    }

    @OnClick(R.id.btn_action)
    public void onAction() {

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mNavigationView.setOnNavigationItemSelectedListener(this);
        Glide.with(this)
                .load(R.mipmap.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayoutAppBar) {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(glideDrawable.getCurrent());
                    }
                });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        return true;
    }
}
