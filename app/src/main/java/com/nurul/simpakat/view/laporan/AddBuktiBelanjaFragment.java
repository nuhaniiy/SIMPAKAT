package com.nurul.simpakat.view.laporan;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.nurul.simpakat.R;
import com.nurul.simpakat.model.simpakat.BuktiBayarModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBuktiBelanjaFragment extends DialogFragment {

    @BindView(R.id.add_nama_barang)
    protected TextInputEditText namaBarang;

    @BindView(R.id.add_nominal)
    protected TextInputEditText nominal;

    @BindView(R.id.pict_bukti)
    protected ImageView pictBukti;

    @BindView(R.id.btn_add_bukti)
    protected Button btnAddBukti;

    private int id;
    private int mode;
    private BuktiBayarModel data;

    public AddBuktiBelanjaFragment() {
        // Required empty public constructor
    }

    public static AddBuktiBelanjaFragment newInstance() {
        AddBuktiBelanjaFragment fragment = new AddBuktiBelanjaFragment();
        return fragment;
    }

    public static AddBuktiBelanjaFragment newInstance(int mode, BuktiBayarModel data) {
        AddBuktiBelanjaFragment fragment = new AddBuktiBelanjaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mode = bundle.getInt("mode");
            id = bundle.getInt("id", -1);
            data = (BuktiBayarModel) bundle.getSerializable("data");
        } else {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    mode = intent.getIntExtra("mode", 0);
                    id = intent.getIntExtra("id", -1);
                    data = (BuktiBayarModel) intent.getSerializableExtra("data");
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_add_bukti_belanja,
                container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_add_bukti)
    void onAddBuktiBelanja() {
        if (mode == 1) {
            if(!namaBarang.getText().toString().equals("") && !nominal.getText().toString().equals("")) {
                data = new BuktiBayarModel();
                data.setNameBarang(namaBarang.getText().toString());
                data.setNominal(nominal.getText().toString());


                FragmentActivity activity = getActivity();
                if (activity != null) {
                    Intent intent = new Intent();

                    intent.putExtra("action", 1);
                    intent.putExtra("data", data);
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                }
            }else{
                if(namaBarang.getText().toString().equals("")){
                    namaBarang.setError(getResources().getString(R.string.nama_barang_required));
                }
                if(nominal.getText().toString().equals("")){
                    nominal.setError(getResources().getString(R.string.nominal_barang_required));
                }
            }
        }
    }

}
