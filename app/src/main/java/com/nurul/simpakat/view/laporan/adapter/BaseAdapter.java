package com.nurul.simpakat.view.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.view.laporan.BaseHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, H extends BaseHolder> extends RecyclerView.Adapter<H> {

    public interface BaseAdapterDelegate<T, H extends BaseHolder> {
        H onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);
        void onBindViewHolder(Context context, List<T> listItem, H holder, int position);
    }

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> listItem = new ArrayList<>();

    private BaseHolder.OnListItemClick onListItemClick;
    private BaseAdapterDelegate<T, H> delegate;

    public BaseAdapter(Context context, BaseHolder.OnListItemClick onListItemClick, BaseAdapterDelegate<T, H> delegate) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.delegate = delegate;
        this.setOnListItemClick(onListItemClick);
    }

    public BaseAdapter(Context context, List<T> listItem, BaseHolder.OnListItemClick onListItemClick, BaseAdapterDelegate<T, H> delegate) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listItem = listItem;
        this.delegate = delegate;
        this.setOnListItemClick(onListItemClick);
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        H result = null;
        if (delegate != null) {
            result = delegate.onCreateViewHolder(inflater, parent, viewType);
        }
        return result;
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setItemPosition(position);
        holder.setOnListItemClick(getOnListItemClick());
        if (delegate != null) {
            delegate.onBindViewHolder(context, listItem, holder, position);
        }
    }

    public BaseHolder.OnListItemClick getOnListItemClick() {
        return onListItemClick;
    }

    public void setOnListItemClick(BaseHolder.OnListItemClick onListItemClick) { this.onListItemClick = onListItemClick; }

    @Override
    public int getItemCount() {
        int retVal = 0;
        if (listItem != null) { retVal = listItem.size(); }
        return retVal;
    }

    public void addItem(int index, T data) {
        listItem.add(index, data);
    }

    public void addItem(T data) {
        listItem.add(data);
    }

    public void addItem(List<T> data) {
        listItem.addAll(data);
    }

    public T getItem(int position) {
        T itemData = null;

        if (position >= 0) {
            itemData = listItem.get(position);
        }

        return itemData;
    }

    public void removeItem(int position) {
        listItem.remove(position);
    }

    public void clearItem() {
        listItem.clear();
    }

}
