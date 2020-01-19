package com.nurul.simpakat.view.dekan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListPengajuan;
import com.nurul.simpakat.view.home.adapter.ListPengajuanAdapter;

import java.util.ArrayList;

public class ListPengajuanDosenAdapter extends RecyclerView.Adapter<ListPengajuanDosenAdapter.ListPengajuanDosenViewHolder> {

    private ArrayList<ListPengajuan> dataList;
    private ListPengajuanClicked clickListener;

    public ListPengajuanDosenAdapter(ArrayList<ListPengajuan> dataList, ListPengajuanClicked clickListener) {
        this.dataList = dataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ListPengajuanDosenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_dosen_pengajuan_item, parent, false);
        return new ListPengajuanDosenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPengajuanDosenViewHolder holder, int position) {
        holder.titlePengajuan.setText(dataList.get(position).getNamaProker());
        holder.statusPengajuan.setText("Menunggu Persetujuan");

        holder.itemView.setOnClickListener(view -> {
            this.clickListener.ListPengajuanClicked(dataList.get(position));
//            recyclerItemClickListener.onItemClick(dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListPengajuanDosenViewHolder extends RecyclerView.ViewHolder {

        TextView titlePengajuan, statusPengajuan;
        ImageView approve;

        ListPengajuanDosenViewHolder(View itemView) {
            super(itemView);
            titlePengajuan =  itemView.findViewById(R.id.title);
            statusPengajuan =  itemView.findViewById(R.id.status);
            approve = itemView.findViewById(R.id.icon_approve);

        }
    }

    public interface ListPengajuanClicked{
        void ListPengajuanClicked(ListPengajuan list);
    }

    public void clearData() {
        this.dataList = null;
    }
}
