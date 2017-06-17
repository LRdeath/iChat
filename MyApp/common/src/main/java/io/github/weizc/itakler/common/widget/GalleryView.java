package io.github.weizc.itakler.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.github.weizc.itakler.common.R;
import io.github.weizc.itakler.common.widget.recycler.RecyclerAdapter;

/**
 * @author Vzc  Email:newlr@foxmail.com
 * @version 1.0.0
 * @Date 2017/6/16.
 */

public class GalleryView extends RecyclerView {
    //用的一些限制常量
    private static final int LOADER_ID = 0x0100;
    private static final int MAX_IMAGE_CAPACITY = 3; //最大选中图片数量
    private static final int MAX_IMAGE_FILE_SIZE = 1024 * 10;//最小图片大小

    private Adapter mAdapter = new Adapter();
    private List<Image> mSelectImages = new LinkedList<>();
    private SelectedChangeListener mListener;
    private LoaderCallback mLoaderCallback = new LoaderCallback();

    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setmListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder<Image> holder, Image image) {
                if (onItemChangeClick(image)) {
                    holder.updateData(image);
                }
            }

        });

    }

    /**
     * 初始化方法
     *
     * @param loaderManager loader管理器
     * @param listener      图片状态改变监听器
     * @return 任何一个LOADER_ID, 可用于销毁loader
     */
    public int setup(LoaderManager loaderManager, @NonNull SelectedChangeListener listener) {
        mListener = listener;
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        return LOADER_ID;
    }

    /**
     * 用于实际加载数据的 loader Callback
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media._ID,//图片id
                MediaStore.Images.Media.DATA,//路径
                MediaStore.Images.Media.DATE_ADDED //图片创建时间
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ID) {
                return new CursorLoader(getContext(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + " DESC");//倒序查找
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //当loader加载完成时
            List<Image> images = new ArrayList<>();
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();

                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    do {
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long time = data.getLong(indexDate);
                        File file = new File(path);
                        if (!file.exists() || file.length() < MAX_IMAGE_FILE_SIZE) {
                            //如果没有图片，或者图片太小，则跳过
                            continue;
                        }
                        //添加一条新数据
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = time;
                        images.add(image);
                    } while (data.moveToNext());
                }
            }
            udateSource(images);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            //当Loader重置或者销毁，进行页面清空
            udateSource(null);
        }
    }

    /**
     * 获取选中图片的全部路径
     *
     * @return 选中图片路径的数组
     */
    public String[] getSelectImages() {
        String[] paths = new String[mSelectImages.size()];
        int index = 0;
        for (Image image : mSelectImages) {
            paths[index++] = image.path;
        }
        return paths;
    }

    /**
     * Adapter数据更改
     *
     * @param images 新数据
     */
    private void udateSource(List<Image> images) {
        mAdapter.replace(images);
    }

    /**
     * 清空选中图片状态
     */
    public void clear() {
        for (Image image : mSelectImages) {
            image.isSelect = false;
        }
        mSelectImages.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 图片点击的具体逻辑 实现
     *
     * @param image 点击的具体图片
     * @return True，代表我进行了数据更改，你需要刷新；反之不刷新
     */
    private boolean onItemChangeClick(Image image) {
        boolean isReflesh;
        if (mSelectImages.contains(image)) {
            isReflesh = true;
            image.isSelect = false;
            mSelectImages.remove(image);
        } else {
            if (mSelectImages.size() < MAX_IMAGE_CAPACITY) {
                isReflesh = true;
                image.isSelect = true;
                mSelectImages.add(image);
            } else {
                isReflesh = false;
                showToast();
            }
        }
        if (isReflesh) notifySelectChange();
        return isReflesh;
    }

    /**
     * 通知外部图片状态改变
     */
    private void notifySelectChange() {
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedChangerListener(mSelectImages.size());
        }
    }

    /**
     * 提示达到选择图片最大值
     */
    private void showToast() {
        //获取提示文字
        String str = getResources().getString(R.string.label_gallery_select_max_size);
        //格式化填充
        str = String.format(str, MAX_IMAGE_CAPACITY);
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 适配器实现
     */
    private class Adapter extends RecyclerAdapter<Image> {


        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleryView.ViewHolder(root);
        }

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_gallery;
        }
    }

    /**
     * Adapter的Viewholder内部实现
     */
    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image> {
        private ImageView mPic;
        private View mShade;
        private CheckBox mSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.gallery_im);
            mShade = itemView.findViewById(R.id.gallery_shade);
            mSelect = (CheckBox) itemView.findViewById(R.id.gallery_cb);
        }

        @Override
        protected void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .placeholder(R.color.grey_200)
                    .into(mPic);
            mShade.setVisibility(image.isSelect ? VISIBLE : INVISIBLE);
            mSelect.setChecked(image.isSelect);
            mSelect.setVisibility(VISIBLE);
        }
    }

    /**
     * 内部Image数据结构
     */
    private class Image {
        int id;//数据Id
        long date; //图片创建日期
        String path;//图片路径
        boolean isSelect;//图片是否被选中

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Image image = (Image) obj;

            return path != null ? path.equals(image.path) : image.path == null;
        }

        @Override
        public int hashCode() {
            return path == null ? 0 : path.hashCode();
        }
    }

    /**
     * 对外部实现
     * 图片选中状态改变监听器
     */
    public interface SelectedChangeListener {
        void onSelectedChangerListener(int count);
    }
}
