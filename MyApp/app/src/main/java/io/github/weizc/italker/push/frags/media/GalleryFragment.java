package io.github.weizc.italker.push.frags.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import io.github.weizc.itakler.common.tools.UiTool;
import io.github.weizc.itakler.common.widget.GalleryView;
import io.github.weizc.italker.push.R;


/**
 * 图片选择弹窗
 *
 * @author Vzc
 * @version 1.0.0
 * @Date 2017/6/18.
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {
    private GalleryView mGalleryView;
    private ImageSelectListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView = (GalleryView) root.findViewById(R.id.galleryView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setup(getLoaderManager(), this);
    }

    /**
     * 处理图片选择的回调
     * @param count 选择的图片数量
     */
    @Override
    public void onSelectedChangerListener(int count) {
        if (count>0){
            //隐藏自己
            dismiss();
            if (mListener!=null){
                //得到所有图片路径
                String[] paths = mGalleryView.getSelectImages();
                //返回第一张图片
                mListener.selectImage(paths[0]);
                //加快内存回收
                mListener = null;
            }
        }
    }

    /**
     * 设置图片选择监听器
     * @param listener
     * @return 返回自己，流式添加
     */
    public GalleryFragment setSelectListener(ImageSelectListener listener){
        mListener = listener;
        return this;
    }

    /**
     * 选择图片监听器
     */
    public interface ImageSelectListener {
        void selectImage(String path);
    }

    public static class TransStatusBottomSheetDialog extends BottomSheetDialog {

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null)
                return;


            // 得到屏幕高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            // 得到状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            // 计算dialog的高度并设置
            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);

        }
    }
}
