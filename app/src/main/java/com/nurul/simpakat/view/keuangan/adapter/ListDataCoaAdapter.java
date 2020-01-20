package com.nurul.simpakat.view.keuangan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListDataCoa;

import java.util.ArrayList;

public class ListDataCoaAdapter extends RecyclerView.Adapter<ListDataCoaAdapter.ListCoaViewHolder> {

    private ArrayList<ListDataCoa> dataList;
    private ListCoaClicked clickListener;

    public ListDataCoaAdapter(ArrayList<ListDataCoa> dataList, ListCoaClicked clickListener) {
        this.dataList = dataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ListCoaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_data_coa, parent, false);
        return new ListCoaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCoaViewHolder holder, int position) {
        holder.namaCoa.setText(dataList.get(position).getNamaCoa());
        holder.kodeCoa.setText(dataList.get(position).getKodeCoa());

        holder.itemView.setOnClickListener(view -> {
            clickListener.ListCoaClicked(dataList.get(position));
//            recyclerItemClickListener.onItemClick(dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListCoaViewHolder extends RecyclerView.ViewHolder {

        TextView namaCoa, kodeCoa;

        ListCoaViewHolder(View itemView) {
            super(itemView);
            namaCoa =  itemView.findViewById(R.id.tv_nama_coa);
            kodeCoa =  itemView.findViewById(R.id.tv_kode_coa);

        }
    }

    public interface ListCoaClicked{
        void ListCoaClicked(ListDataCoa list);
    }
}
