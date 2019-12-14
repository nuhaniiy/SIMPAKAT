package com.nurul.simpakat;

import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.nurul.simpakat.common.util.DialogUtils;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.common.util.ProgressUtils;

public abstract class AbstractFragmentView<T> extends DialogFragment implements BaseView<T> {
    protected T viewModel;
    protected PreferenceUtils appPreference;
//    protected PreferenceUtils fcmPreference;

    @Override
    public void setModel(T viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public T getModel() {
        return viewModel;
    }

    @Override
    public String getResourceString(int resId) {
        return getString(resId);
    }

    public void setAppPreference(PreferenceUtils preference) {
        this.appPreference = preference;
    }

    public PreferenceUtils getAppPreference() {
        return appPreference;
    }

    /*public void setFcmPreference(PreferenceUtils preference) {
        this.fcmPreference = preference;
    }

    public PreferenceUtils getFcmPreference() {
        return fcmPreference;
    }*/

    @Override
    public void displayLoadIndicator() {
        ProgressUtils.show(getActivity(), "", getString(R.string.loading), false);
    }

    @Override
    public void displayLoadIndicator(boolean isCancelable) {
        ProgressUtils.show(getActivity(), "", getString(R.string.loading), isCancelable);
    }

    @Override
    public void hideLoadIndicator() {
        ProgressUtils.dismiss();
    }

    @Override
    public void displayMessage(String title, String message) {
        DialogUtils.showMessage(getActivity(), title, message, true);
    }

    @Override
    public void displayMessage(String title, String message, boolean isCancelable) {
//        DialogUtils.showMessage(getActivity(), title, message, isCancelable);
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMessage(String title, String message, boolean isCancelable, final Runnable onDismiss) {
        DialogUtils.showMessage(getActivity(), title, message, isCancelable,
                dialog -> onDismiss.run());
    }

    @Override
    public void displayMessage(String title, String message, boolean isCancelable, Runnable onPositive, Runnable onNegative) {
        DialogUtils.showConfirmation(getActivity(), title, message, isCancelable,
                (dialog, which) -> {
                    dialog.dismiss();
                    onPositive.run();
                },
                (dialog, which) -> {
                    dialog.dismiss();
                    onNegative.run();
                });
    }

    @Override
    public boolean closeView() {
        getActivity().finish();
        return true;
    }
}
