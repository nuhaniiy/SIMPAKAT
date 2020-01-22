package com.nurul.simpakat.view.laporan;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public abstract class BaseHolder extends RecyclerView.ViewHolder {

    public interface OnListItemClick {
        void onItemCheckedChanged(int position, boolean isChecked);
        void onItemEdit(int position);
        void onItemRemove(int position);
        void onItemClick(int position, int actionType);
        void onItemClick(int position, int actionType, View view);
    }

    private int itemPosition;
    private OnListItemClick onListItemClick;

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public OnListItemClick getOnListItemClick() {
        return onListItemClick;
    }

    public void setOnListItemClick(OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }

    protected void callOnListItemCheckedChanged(boolean isChecked) {
        if (getOnListItemClick() != null) {
            getOnListItemClick().onItemCheckedChanged(
                    getItemPosition(),
                    isChecked);
        }
    }

    protected void callOnListItemEdit() {
        if (getOnListItemClick() != null) {
            getOnListItemClick().onItemEdit(
                    getItemPosition());
        }
    }

    protected void callOnListItemRemove() {
        if (getOnListItemClick() != null) {
            getOnListItemClick().onItemRemove(
                    getItemPosition());
        }
    }

    protected void callOnListItemClick(int actionType) {
        if (getOnListItemClick() != null) {
            getOnListItemClick().onItemClick(
                    getItemPosition(),
                    actionType
            );
        }
    }

    protected void callOnListItemClick(int actionType, View view) {
        if (getOnListItemClick() != null) {
            getOnListItemClick().onItemClick(
                    getItemPosition(),
                    actionType,
                    view);
        }
    }

}
