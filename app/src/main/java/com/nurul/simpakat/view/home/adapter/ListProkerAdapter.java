package com.nurul.simpakat.view.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListProker;
import com.nurul.simpakat.view.home.ui.proker.DetailProgramKerjaActivity;

import java.util.ArrayList;

public class ListProkerAdapter extends RecyclerView.Adapter<ListProkerAdapter.ListProkerViewHolder> {

    private ArrayList<ListProker> dataList;
    private Context mContext;
//    private RecyclerItemClickListener recyclerItemClickListener;


    public ListProkerAdapter(ArrayList<ListProker> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListProkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_proker_item, parent, false);
        return new ListProkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProkerViewHolder holder, int position) {
        holder.titleProker.setText(dataList.get(position).getNamaProker());
        if(dataList.get(position).getStatusProker().equals("Belum Diajukan")) {
            holder.statusProker.setTextColor(mContext.getResources().getColor(R.color.red_notification));
            holder.statusProker.setText(dataList.get(position).getStatusProker());
        } else {
            holder.statusProker.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.statusProker.setText(dataList.get(position).getStatusProker());
        }

        holder.itemView.setOnClickListener(view -> {
//            recyclerItemClickListener.onItemClick(dataList.get(position));
            Intent intent = new Intent(mContext, DetailProgramKerjaActivity.class);
            intent.putExtra("data", dataList.get(position).getIdProker());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListProkerViewHolder extends RecyclerView.ViewHolder {

        TextView titleProker, statusProker;

        ListProkerViewHolder(View itemView) {
            super(itemView);
            titleProker =  itemView.findViewById(R.id.title);
            statusProker =  itemView.findViewById(R.id.status);

        }
    }
}
