package io.github.weizc.italker.push.frags.main;


import android.view.View;

import butterknife.BindView;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.itakler.common.widget.GalleryView;
import io.github.weizc.italker.push.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements GalleryView.SelectedChangeListener{
    @BindView(R.id.galleryView)
     GalleryView mGallery;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData() {
        super.initData();
        mGallery.setup(getLoaderManager(),this);
    }



    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public void onSelectedChangerListener(int count) {

    }
}