package com.nurul.simpakat.view.laporan.adapter;

import android.content.Context;

import com.nurul.simpakat.view.laporan.BaseHolder;

import java.util.List;

public class DefaultAdapter<T, H extends BaseHolder> extends BaseAdapter<T, H> {

    public DefaultAdapter(
            Context context,
            BaseHolder.OnListItemClick onListItemClick,
            BaseAdapterDelegate<T, H> delegate) {
        super(context, onListItemClick, delegate);
    }

    public DefaultAdapter(
            Context context,
            List<T> listItem,
            BaseHolder.OnListItemClick onListItemClick,
            BaseAdapterDelegate<T, H> delegate) {
        super(context, listItem, onListItemClick, delegate);
    }

}
