package com.nurul.simpakat.view.warek.karyawan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurul.simpakat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataKaryawanFragment extends Fragment {


    public DataKaryawanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_karyawan, container, false);
    }

}
