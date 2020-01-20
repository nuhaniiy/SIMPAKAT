package com.nurul.simpakat.view.keuangan.coa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.nurul.simpakat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCoaActivity extends AppCompatActivity {

    @BindView(R.id.app_bar)
    public AppBarLayout appBarLayout;

    private static AddCoaActivity instance;

    public static final AddCoaActivity instance() {
        if (instance != null)
            return instance;
        throw new RuntimeException("Act not instantiated yet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coa);

        ButterKnife.bind(this);

        instance = this;
    }

    public void hideAppBar() {
        appBarLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.back_task)
    void moveTaskBack(){
        finish();
    }
}
