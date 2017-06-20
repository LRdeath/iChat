package io.github.weizc.italker.push.frags.main;


import android.view.View;

import butterknife.BindView;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.itakler.common.widget.GalleryView;
import io.github.weizc.italker.push.R;


/**
 *
 * @author Vzc
 * @version 1.0.0
 * @Date 2017/6/18.
 */
public class GroupFragment extends Fragment implements GalleryView.SelectedChangeListener{

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData() {
        super.initData();
    }



    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public void onSelectedChangerListener(int count) {

    }
}
