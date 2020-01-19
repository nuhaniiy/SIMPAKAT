package com.nurul.simpakat.view.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListPengajuan;

import java.util.ArrayList;

public class ListPengajuanAdapter extends RecyclerView.Adapter<ListPengajuanAdapter.ListPengajuanViewHolder> {

    private ArrayList<ListPengajuan> dataList;
    private ListPengajuanClicked clickListener;

    public ListPengajuanAdapter(ArrayList<ListPengajuan> dataList, ListPengajuanClicked clickListener) {
        this.dataList = dataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ListPengajuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_pengajuan_item, parent, false);
        return new ListPengajuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPengajuanViewHolder holder, int position) {
        holder.titlePengajuan.setText(dataList.get(position).getNamaProker());
        holder.statusPengajuan.setText(dataList.get(position).getStatusPengajuan());

        holder.itemView.setOnClickListener(view -> {
            clickListener.ListPengajuanClicked(dataList.get(position));
//            recyclerItemClickListener.onItemClick(dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListPengajuanViewHolder extends RecyclerView.ViewHolder {

        TextView titlePengajuan, statusPengajuan;

        ListPengajuanViewHolder(View itemView) {
            super(itemView);
            titlePengajuan =  itemView.findViewById(R.id.title);
            statusPengajuan =  itemView.findViewById(R.id.status);

        }
    }

    public interface ListPengajuanClicked{
        void ListPengajuanClicked(ListPengajuan list);
    }
}
