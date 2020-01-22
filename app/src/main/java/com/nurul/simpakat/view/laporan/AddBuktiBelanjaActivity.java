package com.nurul.simpakat.view.laporan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nurul.simpakat.R;

import butterknife.ButterKnife;

public class AddBuktiBelanjaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bukti_belanja);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            int mode = intent.getIntExtra("mode", 0);
            if (mode == 1) {
                setTitle("Add Bukti Belanja");
            }
        }
    }
}
