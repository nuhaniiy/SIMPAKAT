package com.nurul.simpakat.view.warek.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.ListEmployee;

import java.util.ArrayList;

public class ListEmployeeAdapter extends RecyclerView.Adapter<ListEmployeeAdapter.ListEmployeeViewHolder> {

    private ArrayList<ListEmployee> dataList;
    private ListEmployeeClicked clickListener;
    private Context mContext;

    public ListEmployeeAdapter(ArrayList<ListEmployee> dataList, ListEmployeeClicked clickListener, Context context) {
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ListEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_employee, parent, false);
        return new ListEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListEmployeeViewHolder holder, int position) {
        holder.namaKaryawan.setText(dataList.get(position).getNamaKaryawan());
        if(dataList.get(position).getKodeUnitKerja().equals("0")) {
            holder.jabatanKaryawan.setText(dataList.get(position).getJabatan());
        } else {
            holder.jabatanKaryawan.setText(dataList.get(position).getJabatan() + " - " + dataList.get(position).getNamaUnit());
        }

        String[] splitStr = dataList.get(position).getNamaKaryawan().split("\\s+");

        String initial_name;

        if(dataList.get(position).getNamaKaryawan().length()<=1){
            initial_name = dataList.get(position).getNamaKaryawan();
        }else {
            if (splitStr.length > 1) {
                initial_name = splitStr[0].substring(0, 1) + splitStr[splitStr.length - 1].substring(0, 1);
            } else {
                initial_name = dataList.get(position).getNamaKaryawan().substring(0, 2);
            }
        }

        initial_name = initial_name.toUpperCase();
        TextDrawable td = TextDrawable.builder()
                .buildRound(initial_name, this.mContext.getResources().getColor(R.color.colorPrimary));
        holder.iconAvatar.setImageDrawable(td);

        holder.itemView.setOnClickListener(view -> {
            clickListener.ListEmployeeClicked(dataList.get(position));
//            recyclerItemClickListener.onItemClick(dataList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListEmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView namaKaryawan, jabatanKaryawan;
        ImageView iconAvatar, seeDetail;

        ListEmployeeViewHolder(View itemView) {
            super(itemView);
            namaKaryawan =  itemView.findViewById(R.id.tv_nama_karyawan);
            jabatanKaryawan =  itemView.findViewById(R.id.tv_jabatan);
            iconAvatar = itemView.findViewById(R.id.icon);
            seeDetail = itemView.findViewById(R.id.icon_see_detail);

        }
    }

    public void clearData() {
        if(this.dataList != null) {
            this.dataList.clear();
        }
    }

    public interface ListEmployeeClicked{
        void ListEmployeeClicked(ListEmployee list);
    }
}
