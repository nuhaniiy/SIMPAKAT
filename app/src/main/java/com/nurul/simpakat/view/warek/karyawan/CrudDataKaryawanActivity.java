package com.nurul.simpakat.view.warek.karyawan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.nurul.simpakat.R;
import com.nurul.simpakat.view.CrudKaryawan;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrudDataKaryawanActivity extends AppCompatActivity {

    @BindView(R.id.app_bar)
    public AppBarLayout appBarLayout;

    @BindView(R.id.title_name)
    public TextView tvTitle;

    private static CrudDataKaryawanActivity instance;

    public Intent intent;

    public static final CrudDataKaryawanActivity instance() {
        if (instance != null)
            return instance;
        throw new RuntimeException("Act not instantiated yet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_data_karyawan);

        ButterKnife.bind(this);

        instance = this;

        intent = getIntent();

        if(intent.getStringExtra("action").equals("0")) {
            tvTitle.setText("Tambah Data Karyawan");
            CrudDataKaryawanFragment pf = new CrudDataKaryawanFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container, pf).commit();
        } else {
            tvTitle.setText("Ubah Data Karyawan");
            EditKaryawanFragment pf = new EditKaryawanFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container, pf).commit();
        }
    }

    public void hideAppBar() {
        appBarLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.back_task)
    void moveTaskBack(){
        finish();
    }
}
