package com.nurul.simpakat.home.ui.pengajuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nurul.simpakat.R;

public class PengajuanProkerFragment extends Fragment {

    FloatingActionButton fabAddSubmission;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pengajuan_proker, container, false);

        fabAddSubmission = root.findViewById(R.id.fab_add_pengajuan_program_kerja);

        fabAddSubmission.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddPengajuanProkerActivity.class));
        });

        return root;
    }
}