package io.github.weizc.italker.push;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Activity;
import io.github.weizc.itakler.common.widget.PortraitView;
import io.github.weizc.italker.push.activities.AccountActivity;
import io.github.weizc.italker.push.frags.main.ActiveFragment;
import io.github.weizc.italker.push.frags.main.ContactFragment;
import io.github.weizc.italker.push.frags.main.GroupFragment;
import io.github.weizc.italker.push.tools.NavHelper;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnTabChangedListener<Integer> {

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
    NavHelper<Integer> mNavHelper;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.im_search)
    public void onSearch() {

    }

    @OnClick(R.id.btn_action)
    public void onAction() {
        AccountActivity.show(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化底部工具类
        mNavHelper = new NavHelper<>(this, R.id.lay_container, getSupportFragmentManager(), this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));

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


        return mNavHelper.performClickMenu(item.getItemId());
    }

    @Override
    protected void initData() {
        super.initData();
        //从底部接管导航中接管我们的menu，然后进行手动的触发第一次点击
        Menu menu = mNavigationView.getMenu();
        //触发首次选中home
        menu.performIdentifierAction(R.id.action_home,0);
    }

    /**
     * NavHelper 处理后回调的方法
     *
     * @param newTab 新的Tab
     * @param oldTab 旧的Tab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        mTitle.setText(newTab.extra);

        float translationY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra,R.string.title_home)){
            translationY = Ui.dipToPx(getResources(),76);
        }else {
            if (Objects.equals(newTab.extra,R.string.title_group)){
                rotation =-360;
                mAction.setImageResource(R.drawable.ic_group_add);
            }else {
                rotation = 360;
                mAction.setImageResource(R.drawable.ic_contact_add);
            }
        }
        mAction.animate()
                .rotation(rotation)
                .translationY(translationY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();
    }


}
