package com.nurul.simpakat.login.signup;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurul.simpakat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupUserFragment extends Fragment {


    public SignupUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_signup_user, container, false);

        return root;
    }

}
