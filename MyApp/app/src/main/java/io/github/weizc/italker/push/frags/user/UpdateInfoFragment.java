package io.github.weizc.italker.push.frags.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.weizc.itakler.common.app.Application;
import io.github.weizc.itakler.common.app.Fragment;
import io.github.weizc.itakler.common.widget.PortraitView;
import io.github.weizc.italker.factory.Factory;
import io.github.weizc.italker.factory.net.UploadHelper;
import io.github.weizc.italker.push.R;
import io.github.weizc.italker.push.frags.media.GalleryFragment;

import static android.app.Activity.RESULT_OK;

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
    void onPortrait() {
        new GalleryFragment()
                .setSelectListener(this)
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    /**
     * 得到选择的图片,并开启Ucrop
     *
     * @param path 图片路径
     */
    @Override
    public void selectImage(String path) {
        UCrop.Options options = new UCrop.Options();
        //设置图片的处理格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //设置压缩后图片的精度
        options.setCompressionQuality(96);

        //得到头像缓存地址
        File cPath = Application.getPortraitTmpFile();

        //开启Ucrop，进行图片裁剪
        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(cPath))
                .withAspectRatio(1, 1)
                .withMaxResultSize(520, 520)
                .withOptions(options)
                .start(getActivity());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //通过UCrop得到对应的uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) loadProtrait(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void loadProtrait(Uri uri) {
        Glide.with(getContext())
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortraitView);

        // 拿到本地文件的地址
        final String localPath = uri.getPath();
        Log.e("TAG", "localPath:" + localPath);
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
               String url =  UploadHelper.upLoadPortrait(localPath);
                Log.e("TAG", "url:" + url);

            }
        });

    }
}
