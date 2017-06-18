package io.github.weizc.italker.push.frags.account;


import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.itakler.common.widget.PortraitView;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.frags.media.GalleryFragment;

/**
 * 更新用户头像Fragment
 *
 * @author Vzc
 * @version 1.0.0
 * @Date 2017/6/18.
 */
public class UpdateInfoFragment extends Fragment implements GalleryFragment.ImageSelectListener {
    @BindView(R.id.uif_im_pv)
    PortraitView mPortraitView;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.uif_im_pv)
    void onPortrait(){
        new GalleryFragment()
                .setSelectListener(this)
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    /**
     * 得到选择的图片
     * @param path 图片路径
     */
    @Override
    public void selectImage(String path) {

    }
}
