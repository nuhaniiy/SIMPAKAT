package com.nurul.simpakat;

public interface BaseView<T> {

    void setModel(T viewModel);
    T getModel();
    String getResourceString(int resId);

    void displayLoadIndicator();
    void displayLoadIndicator(boolean isCancelable);
    void hideLoadIndicator();

    void displayMessage(String title, String message);
    void displayMessage(String title, String message, boolean isCancelable);
    void displayMessage(String title, String message, boolean isCancelable, Runnable onDismiss);
    void displayMessage(String title, String message, boolean isCancelable, Runnable onPositive, Runnable onNegative);

    void updateModel(T model);

    boolean goBack();
    boolean closeView();
}