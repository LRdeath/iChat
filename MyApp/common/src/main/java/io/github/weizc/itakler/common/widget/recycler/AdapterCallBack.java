package io.github.weizc.itakler.common.widget.recycler;

/**
 * Created by Vzc on 2017/6/8.
 */

public interface AdapterCallBack<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
