package com.nurul.simpakat.view.keuangan.listpengajuan;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.model.simpakat.ListPengajuan;
import com.nurul.simpakat.model.simpakat.ListPengajuanModel;
import com.nurul.simpakat.presenter.ListPengajuanPresenter;
import com.nurul.simpakat.view.ListPengajuanView;
import com.nurul.simpakat.view.dekan.adapter.ListPengajuanDosenAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nurul.simpakat.common.Constanta.APPLICATION_PATH;
import static com.nurul.simpakat.common.Constanta.APPLICATION_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPengajuanKeuanganFragment extends AbstractFragmentView<ListPengajuanModel> implements ListPengajuanView,
        ListPengajuanDosenAdapter.ListPengajuanClicked{

    @BindView(R.id.list_pengajuan_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.list_pengajuan_program_kerja_layout)
    RelativeLayout layoutDataPengajuan;

    @BindView(R.id.no_list_data_list_pengajuan)
    RelativeLayout layoutNoDataPengajuan;

    @BindView(R.id.recycler_list_pengajuan_prgram_kerja)
    RecyclerView rvDataPengajuan;

    @BindView(R.id.layout_bottom_sheet)
    RelativeLayout bottomSheetLayout;

    @BindView(R.id.vw_nama_proker)
    TextView namaProker;

    @BindView(R.id.vw_nama_pengaju)
    TextView namaPengaju;

    @BindView(R.id.vw_biaya)
    TextView biaya;

    @BindView(R.id.vw_biaya_terpakai)
    TextView biayaTerpakai;

    @BindView(R.id.vw_sisa_biaya)
    TextView sisaBiaya;

    @BindView(R.id.vw_biaya_diajukan)
    TextView biayaDiajukan;

    @BindView(R.id.vw_dibayar_kepada)
    TextView dibayarKepada;

    @BindView(R.id.vw_jabatan)
    TextView jabatan;

    @BindView(R.id.vw_no_rekening)
    TextView noRekening;

    @BindView(R.id.vw_tanggal_pengajuan)
    TextView tanggalPengajuan;

    private ListPengajuanPresenter listPengajuanPresenter;
    private Boolean flagView = false;
    private String idPengajuan = "0";
    private String idPProkerPengaju = "0";
    private String name = "";
    private String nipPengaju = "";

    public ListPengajuanKeuanganFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ListPengajuanProkerBroadcast"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_pengajuan_keuangan, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new ListPengajuanModel();
        listPengajuanPresenter = new ListPengajuanPresenter();
        listPengajuanPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listPengajuanPresenter.requestDataPengajuanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""),
                getAppPreference().getString(Constanta.PREF_KODE_UNIT_KERJA, ""),
                getAppPreference().getString(Constanta.PREF_JABATAN, ""));
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                listPengajuanPresenter.requestDataPengajuanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""),
                        getAppPreference().getString(Constanta.PREF_KODE_UNIT_KERJA, ""),
                        getAppPreference().getString(Constanta.PREF_JABATAN, ""));
            }
        }
    };

    @Override
    public void onListPengajuanApprove() {

    }

    @Override
    public void onListPengajuanFailed(String message) {

    }

    @Override
    public void onListPengajuanNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataPengajuan.setVisibility(View.GONE);
        layoutNoDataPengajuan.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListPengajuanRetrieves(ArrayList<ListPengajuan> listPengajuans) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataPengajuan.setVisibility(View.VISIBLE);
        layoutNoDataPengajuan.setVisibility(View.GONE);

        setDataToRecyclerView(listPengajuans);
    }

    public void setDataToRecyclerView(ArrayList<ListPengajuan> listPengajuanArrayList) {
        ListPengajuanDosenAdapter adapter = new ListPengajuanDosenAdapter(listPengajuanArrayList, this);
//        adapter.clearData();
        rvDataPengajuan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDataPengajuan.setItemAnimator(new DefaultItemAnimator());
        rvDataPengajuan.setAdapter(adapter);
    }

    @Override
    public void updateModel(ListPengajuanModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @Override
    public void ListPengajuanClicked(ListPengajuan list) {
        if(flagView) {
            return;
        } else {
            flagView = true;
            TranslateAnimation slide = new TranslateAnimation(0, 0, 1200, 0);
            slide.setDuration(1000);
//            slide.setFillAfter(true);
            bottomSheetLayout.startAnimation(slide);
            bottomSheetLayout.setVisibility(View.VISIBLE);
            bottomSheetLayout.bringToFront();

            clearData();

            nipPengaju = list.getNip();
            idPProkerPengaju = list.getIdProker();
            namaProker.setText(list.getNamaProker());
            namaPengaju.setText(list.getNamaKaryawan() + " - " + list.getNip());
            biaya.setText(list.getBiaya());
            biayaTerpakai.setText(list.getBiayaTerpakai());
            sisaBiaya.setText(list.getSisaBiaya());
            biayaDiajukan.setText(list.getBiayaDiajukan());
            dibayarKepada.setText(list.getDibayarKepada());
            jabatan.setText(list.getJabatan());
            noRekening.setText(list.getNoRekening());
            idPengajuan = list.getIdPengajuan();
        }
    }

    @OnClick(R.id.tv_slide_down)
    void onSlideDownData() {
        flagView = false;
//        Toast.makeText(getActivity().getApplicationContext(), "click", Toast.LENGTH_LONG).show();
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1200);
        slide.setDuration(1000);
//        slide.setFillAfter(true);
        bottomSheetLayout.startAnimation(slide);
        bottomSheetLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_acc)
    void onDataAccepted() {
        flagView = false;
        name = namaPengaju.getText().toString();
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1200);
        slide.setDuration(1000);
//        slide.setFillAfter(true);
        bottomSheetLayout.startAnimation(slide);
        bottomSheetLayout.setVisibility(View.GONE);
        clearData();
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        layoutDataPengajuan.setVisibility(View.GONE);
        updateApprovalSubmission("Setuju", idPengajuan, getAppPreference().getString(Constanta.PREF_JABATAN, ""));
    }

    @OnClick(R.id.btn_reject)
    void onDataRejected() {
        flagView = false;
        name = namaPengaju.getText().toString();
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1200);
        slide.setDuration(1000);
//        slide.setFillAfter(true);
        bottomSheetLayout.startAnimation(slide);
        bottomSheetLayout.setVisibility(View.GONE);
        clearData();
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        layoutDataPengajuan.setVisibility(View.GONE);
        updateApprovalSubmission("Tolak", idPengajuan, getAppPreference().getString(Constanta.PREF_JABATAN, ""));
    }

    private void updateApprovalSubmission(String status, String id, String jabatan) {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_update_approval_submission.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();

        params.put("id_pengajuan", id);
        params.put("id_proker", idPProkerPengaju);
        params.put("jabatan", jabatan);
        params.put("status", status);
        params.setUseJsonStreamer(true);

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond",response);
                    JSONObject json = new JSONObject(response);
                    try {
//                        json = new JSONObject(response);
                        Log.d("respond","update approval pengajuan : " + json.getString("resultCode"));

                        Toast.makeText(getContext().getApplicationContext(), json.getString("messageText"), Toast.LENGTH_LONG).show();

                        listPengajuanPresenter.requestDataPengajuanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""),
                                getAppPreference().getString(Constanta.PREF_KODE_UNIT_KERJA, ""),
                                getAppPreference().getString(Constanta.PREF_JABATAN, ""));

                        sendNotificationWarek(getAppPreference().getString(Constanta.PREF_JABATAN,""),
                                name);
                        sendNotificationDosen(nipPengaju, status, name);
                    } catch (JSONException ex) {
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        layoutDataPengajuan.setVisibility(View.VISIBLE);
                        layoutNoDataPengajuan.setVisibility(View.GONE);
                        Toast.makeText(getContext().getApplicationContext(), json.getString("messageText"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException js) {
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    layoutDataPengajuan.setVisibility(View.VISIBLE);
                    layoutNoDataPengajuan.setVisibility(View.GONE);
                    Toast.makeText(getContext().getApplicationContext(), "Somethiing wrong!", Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    layoutDataPengajuan.setVisibility(View.VISIBLE);
                    layoutNoDataPengajuan.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                String response;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("error",response);
                    //Toast.makeText(EditBusinessUnitWizardActivity.this,"Cannot Connect to server",Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendNotificationWarek(String jabatan, String nama) {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_push_notification_pimpinan.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();
        params.put("jabatan", jabatan);
        params.put("nama", nama);
        params.setUseJsonStreamer(true);

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond",response);
                    JSONObject json, jsonData;
                    try {
                        json = new JSONObject(response);
                        Log.d("respond","response notification : " + json.getString("resultCode"));

                    } catch (JSONException ex) {
                        Log.d("respond","response notification : failed");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                String response;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("error",response);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendNotificationDosen(String nip, String status, String nama) {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_push_notification_approval.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();
        params.put("NIP", nip);
        params.put("status", status);
        params.put("nama", getAppPreference().getString(Constanta.PREF_NAME, ""));
        params.setUseJsonStreamer(true);

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond",response);
                    JSONObject json, jsonData;
                    try {
                        json = new JSONObject(response);
                        Log.d("respond","response notification : " + json.getString("resultCode"));

                    } catch (JSONException ex) {
                        Log.d("respond","response notification : failed");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                String response;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("error",response);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void clearData() {
        namaProker.setText("");
        namaPengaju.setText("");
        biaya.setText("");
        biayaTerpakai.setText("");
        sisaBiaya.setText("");
        biayaDiajukan.setText("");
        dibayarKepada.setText("");
        jabatan.setText("");
        noRekening.setText("");
    }
}
