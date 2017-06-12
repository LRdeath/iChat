package io.github.weizc.italker.push;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Activity;

public class MainActivity extends Activity implements IView {

    @BindView(R.id.appbar)
    View mLayoutAppBar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }




    @Override
    public void setResultString(String result) {

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Glide.with(this)
                .load(R.mipmap.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View,GlideDrawable>(mLayoutAppBar) {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(glideDrawable.getCurrent());
                    }


                });
    }

    @Override
    public String getResultString() {
        return null;
    }
}
