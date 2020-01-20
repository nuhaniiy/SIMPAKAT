package com.nurul.simpakat.view.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListKegiatan;

import java.util.ArrayList;

public class ListKegiatanAdapter extends RecyclerView.Adapter<ListKegiatanAdapter.ListKegiatanViewHolder> {

    private ArrayList<ListKegiatan> dataList;
    private ListKegiatanClicked clickListener;

    public ListKegiatanAdapter(ArrayList<ListKegiatan> dataList, ListKegiatanClicked clickListener) {
        this.dataList = dataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ListKegiatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_kegiatan, parent, false);
        return new ListKegiatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListKegiatanViewHolder holder, int position) {
        holder.namaKegiatan.setText(dataList.get(position).getNamaKegiatan());
        holder.namaPengaju.setText(dataList.get(position).getNamaPengaju());

        holder.itemView.setOnClickListener(view -> {
            clickListener.ListKegiatanClicked(dataList.get(position));
//            recyclerItemClickListener.onItemClick(dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListKegiatanViewHolder extends RecyclerView.ViewHolder {

        TextView namaKegiatan, namaPengaju;
        ImageView lihatDetail;

        ListKegiatanViewHolder(View itemView) {
            super(itemView);
            namaKegiatan =  itemView.findViewById(R.id.tv_nama_kegiatan);
            namaPengaju =  itemView.findViewById(R.id.tv_nama_pengaju);
            lihatDetail = itemView.findViewById(R.id.icon_see_detail);

        }
    }

    public interface ListKegiatanClicked{
        void ListKegiatanClicked(ListKegiatan list);
    }
}
