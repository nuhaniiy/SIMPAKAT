package com.nurul.simpakat.model.simpakat;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nurul.simpakat.R;
import com.nurul.simpakat.view.laporan.BaseHolder;

import butterknife.BindView;
import butterknife.OnClick;

public class BuktiBayarItemHolder extends BaseHolder {

    @BindView(R.id.edit_name)
    public EditText editName;

    @BindView(R.id.edit_nominal)
    public EditText editNominal;

    @BindView(R.id.image_remove)
    public ImageView imageRemove;

    @BindView(R.id.img_bukti)
    public ImageView buktiBayar;

    public BuktiBayarItemHolder(View itemView) {
        super(itemView);
    }

    @OnClick(R.id.image_remove)
    void onClickImageRemove() {
        callOnListItemRemove();
    }
}
