package com.nurul.simpakat.view.dekan.pengaturan;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.view.home.ui.pengaturan.password.ChangePasswordActivity;
import com.nurul.simpakat.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PengaturanDekanFragment extends Fragment {

    @BindView(R.id.img_pict)
    ImageView imgAvatar;

    @BindView(R.id.setting_nama)
    TextView nama;

    @BindView(R.id.setting_nip)
    TextView nip;

    private PreferenceUtils preferenceUtils;

    public PengaturanDekanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pengaturan_dekan, container, false);

        preferenceUtils = new PreferenceUtils(
                getActivity(), Constanta.APPLICATION_PREFERENCE);

        ButterKnife.bind(this, root);

        setBindingData();

        return root;
    }

    private void setBindingData() {
        nama.setText(preferenceUtils.getString(Constanta.PREF_NAME, "") + " - " + preferenceUtils.getString(Constanta.PREF_JABATAN, ""));
        nip.setText("NIP : " + preferenceUtils.getString(Constanta.PREF_ID, ""));
        String name = isNull(preferenceUtils.getString(Constanta.PREF_NAME, ""))
                || preferenceUtils.getString(Constanta.PREF_NAME, "")
                .equals("")?"No Name":preferenceUtils.getString(Constanta.PREF_NAME, "");

        String[] splitStr = name.split("\\s+");

        String initial_name;

        if(name.length()<=1){
            initial_name = name;
        }else {
            if (splitStr.length > 1) {
                initial_name = splitStr[0].substring(0, 1) + splitStr[splitStr.length - 1].substring(0, 1);
            } else {
                initial_name = name.substring(0, 2);
            }
        }

        initial_name = initial_name.toUpperCase();
        TextDrawable td = TextDrawable.builder()
                .buildRound(initial_name, getResources().getColor(R.color.colorPrimary));
        imgAvatar.setImageDrawable(td);
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

    @OnClick(R.id.change_password)
    void onChangePasswordSetting() {
        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
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

    private boolean isNull(String s) {
        return s == null;
    }
}
