package com.nurul.simpakat.view.warek.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListUnitKerja;

import java.util.ArrayList;

public class ListUnitKerjaAdapter extends RecyclerView.Adapter<ListUnitKerjaAdapter.ListUnitKerjaViewHolder> {

    private ArrayList<ListUnitKerja> dataList;
    private ListUnitKerjaClicked clickListener;

    public ListUnitKerjaAdapter(ArrayList<ListUnitKerja> dataList, ListUnitKerjaClicked clickListener) {
        this.dataList = dataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ListUnitKerjaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_unit_kerja, parent, false);
        return new ListUnitKerjaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUnitKerjaViewHolder holder, int position) {
        holder.namaUnit.setText(dataList.get(position).getNama());
        holder.statusUnit.setText(dataList.get(position).getStatus());

        holder.itemView.setOnClickListener(view -> {
            clickListener.ListUnitKerjaClicked(dataList.get(position));
//            recyclerItemClickListener.onItemClick(dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListUnitKerjaViewHolder extends RecyclerView.ViewHolder {

        TextView namaUnit, statusUnit;

        ListUnitKerjaViewHolder(View itemView) {
            super(itemView);
            namaUnit =  itemView.findViewById(R.id.tv_nama_unitkerja);
            statusUnit =  itemView.findViewById(R.id.tv_status_unitkerja);

        }
    }

    public interface ListUnitKerjaClicked{
        void ListUnitKerjaClicked(ListUnitKerja list);
    }
}
