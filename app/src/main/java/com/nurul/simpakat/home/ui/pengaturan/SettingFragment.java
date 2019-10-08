package com.nurul.simpakat.home.ui.pengaturan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingFragment extends Fragment {

    private PreferenceUtils preferenceUtils;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        preferenceUtils = new PreferenceUtils(
                getActivity(), Constanta.APPLICATION_PREFERENCE);

        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.layer_logout)
    void onLogoutUser() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Logout Confirmation")
                .setMessage("Do you really want to logout?")
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> onClickTextLogout())
                .setNegativeButton(android.R.string.no, (dialog, whichButton) -> dialog.dismiss())
                .show();
    }

    private void onClickTextLogout() {
        preferenceUtils.clear();
        showLoginPage();
    }

    private void showLoginPage() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}