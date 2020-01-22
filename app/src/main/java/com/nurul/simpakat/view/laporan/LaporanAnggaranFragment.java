package com.nurul.simpakat.view.laporan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurul.simpakat.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaporanAnggaranFragment extends Fragment {


    public LaporanAnggaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_laporan_anggaran, container, false);

        ButterKnife.bind(this, root);
        return root;
    }

}
