package com.nurul.simpakat.home.ui.proker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nurul.simpakat.R;

public class ProgramKerjaFragment extends Fragment {

    FloatingActionButton fabAddData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_program_kerja, container, false);

        fabAddData = root.findViewById(R.id.fab_add_program_kerja);

        fabAddData.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddProgramKerjaActivity.class));
        });

        return root;
    }
}