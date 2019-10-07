package com.nurul.simpakat.home.ui.proker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nurul.simpakat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProgramKerjaActivity extends AppCompatActivity {

    @BindView(R.id.spinner_unit_kerja)
    protected MaterialSpinner materialSpinner;

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccessAdd;

    @BindView(R.id.layout_button_add_proker)
    protected RelativeLayout layoutBtnAddProker;

    @BindView(R.id.app_bar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.layout_add_data_proker)
    protected NestedScrollView layoutAddDataProker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program_kerja);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setMaterialData();
        setOnClick();
    }

    private void setMaterialData() {
        materialSpinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
    }

    private void setOnClick() {
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });
    }

    private void showLayoutSuccess() {
        appBarLayout.setVisibility(View.GONE);
        layoutBtnAddProker.setVisibility(View.GONE);
        layoutAddDataProker.setVisibility(View.GONE);
        layoutSuccessAdd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.back_task)
    void moveTaskBack(){
        finish();
    }

    @OnClick(R.id.btn_add_proker)
    void addProkerClick() {
        showLayoutSuccess();
        Thread myThread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
