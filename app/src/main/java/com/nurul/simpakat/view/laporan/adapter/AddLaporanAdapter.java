package com.nurul.simpakat.view.laporan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.model.simpakat.BuktiBayarModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AddLaporanAdapter extends RecyclerView.Adapter<AddLaporanAdapter.AddLaporanViewHolder> {

    private ArrayList<BuktiBayarModel> dataList;
    private UploadBuktiClicked clickListener;
    private AppCompatActivity activity;

    public AddLaporanAdapter(ArrayList<BuktiBayarModel> dataList, UploadBuktiClicked clickListener, AppCompatActivity activity) {
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AddLaporanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_bukti_bayar, parent, false);
        return new AddLaporanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddLaporanViewHolder holder, int position) {
//        holder.namaKegiatan.setText(dataList.get(position).getNamaKegiatan());
//        holder.namaPengaju.setText(dataList.get(position).getNamaPengaju());
//
//        holder.itemView.setOnClickListener(view -> {
//            clickListener.ListKegiatanClicked(dataList.get(position));
////            recyclerItemClickListener.onItemClick(dataList.get(position));
//        });
        holder.imgremove.setOnClickListener(view -> {
            dataList.remove(position);
            notifyDataSetChanged();
        });

        holder.buktiBayar.setOnClickListener(view -> {
            this.clickListener.UploadBuktiClicked();
//            OpenGallery();
//            ImagePicker.create(activity)
//                    .single().showCamera(true)
//                    .start(12);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class AddLaporanViewHolder extends RecyclerView.ViewHolder {

        TextView namaBarang, nominalBarang;
        ImageView buktiBayar, imgremove;

        AddLaporanViewHolder(View itemView) {
            super(itemView);
            namaBarang =  itemView.findViewById(R.id.edit_name);
            nominalBarang =  itemView.findViewById(R.id.edit_nominal);
            buktiBayar = itemView.findViewById(R.id.img_bukti);
            imgremove = itemView.findViewById(R.id.image_remove);

        }
    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, 12);
    }

    public interface UploadBuktiClicked{
        void UploadBuktiClicked();
    }

}
