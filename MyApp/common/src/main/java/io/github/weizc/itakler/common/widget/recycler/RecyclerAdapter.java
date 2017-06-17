package io.github.weizc.itakler.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.weizc.itakler.common.R;

/**
 * Created by Vzc on 2017/6/8.
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener,
        View.OnLongClickListener, AdapterCallBack<Data> {
    private final List<Data> mDataList;


    private AdapterListener<Data> mListener;

    /**
     * 构造方法模块
     */
    protected RecyclerAdapter() {
        this(null);
    }

    protected RecyclerAdapter(AdapterListener<Data> mListener) {
        this(new ArrayList<Data>(), mListener);
    }

    protected RecyclerAdapter(List<Data> mDataList, AdapterListener<Data> mListener) {
        this.mDataList = mDataList;
        this.mListener = mListener;
    }


    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型，约定为XML布局id
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        //得到layoutInfalter用于把XML初始化View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //把XML id为viewType的文件初始化为rootView
        View rootView = inflater.inflate(viewType, parent, false);
        //通过子类必须实现的ViewHolder方法，得到ViewHolder
        ViewHolder<Data> viewHolder = onCreateViewHolder(rootView, viewType);
        //设置View的Tag为viewholder 进行双向绑定
        rootView.setTag(R.id.tag_recycler_holder, viewHolder);
        //设置事件点击
        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);
        //界面注解绑定
        viewHolder.mUnbinder = ButterKnife.bind(viewHolder, rootView);
        //绑定callback
        viewHolder.callBack = this;
        return viewHolder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     item根布局
     * @param viewType xml的id
     * @return viewHodler
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个ViewHolder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //得到要绑定的数据
        Data data = mDataList.get(position);
        //触发Holder绑定方法
        holder.bind(data);
    }

    /**
     * 得到当前集合的Size
     *
     * @return 集合的Size
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 复写默认的布局类型返回
     *
     * @param position item坐标
     * @return 返回复写后的xml文件id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局类型
     *
     * @param position item坐标
     * @return xml文件id，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);


    /**
     * 集合的添加操作模块
     *
     * @param data 需要添加的数据
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void add(Data... datalist) {
        if (datalist != null && datalist.length > 0) {
            int statPos = mDataList.size();
            Collections.addAll(mDataList, datalist);
            notifyItemRangeChanged(statPos, datalist.length);
        }
    }

    public void add(Collection<Data> datalist) {
        if (datalist != null && datalist.size() > 0) {
            int statPos = mDataList.size();
            mDataList.addAll(datalist);
            notifyItemRangeChanged(statPos, datalist.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 集合修改替换操作
     *
     * @param datas 新集合
     */
    public void replace(Collection<Data> datas) {
        mDataList.clear();
        if (datas == null || datas.size() == 0) return;
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }
    /**
     * 更新操作
     */
    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        //获得当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if (pos>=0&&pos<getItemCount()){
            //数据移除和更新
            mDataList.remove(pos);
            mDataList.add(pos,data);
            //通知这个坐标下的Item有更新
            notifyItemChanged(pos);
        }
    }

    /**
     * 处理点击事件
     *
     * @param v 触发点击事件的View
     */
    @Override
    public void onClick(View v) {
        ViewHolder<Data> holder = (ViewHolder<Data>) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = holder.getAdapterPosition();
            this.mListener.onItemClick(holder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder<Data> holder = (ViewHolder<Data>) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = holder.getAdapterPosition();
            this.mListener.onItemLongClick(holder, mDataList.get(pos));
        }
        return false;
    }

    /**
     * 设置点击事件监听器
     *
     * @param mListener 监听器
     */
    public void setmListener(AdapterListener<Data> mListener) {
        this.mListener = mListener;
    }

    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder<Data> holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder<Data> holder, Data data);
    }

    /**
     * 对回调接口AdapterListener做一次实现
     * 外部调用实现至少一个方法
     * @param <Data>
     */
    public static abstract class AdapterListenerImpl<Data> implements AdapterListener<Data>{

        @Override
        public void onItemClick(ViewHolder<Data> holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder<Data> holder, Data data) {

        }
    }

    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder mUnbinder;
        private AdapterCallBack<Data> callBack;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候，必须复写
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * Holder 对 自己对应的Data进行更新操作
         *
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (callBack != null) {
                callBack.update(data, this);
            }
        }
    }
}
