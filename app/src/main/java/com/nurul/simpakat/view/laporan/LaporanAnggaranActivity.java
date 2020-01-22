package com.nurul.simpakat.view.laporan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.ProgressUtils;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.simpakat.BuktiBayarItemHolder;
import com.nurul.simpakat.model.simpakat.BuktiBayarModel;
import com.nurul.simpakat.view.LaporanView;
import com.nurul.simpakat.view.laporan.adapter.AddLaporanAdapter;
import com.nurul.simpakat.view.laporan.adapter.BaseAdapter;
import com.nurul.simpakat.view.laporan.adapter.DefaultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nurul.simpakat.common.Constanta.APPLICATION_PATH;
import static com.nurul.simpakat.common.Constanta.APPLICATION_URL;

public class LaporanAnggaranActivity extends AppCompatActivity implements AddLaporanAdapter.UploadBuktiClicked {

    @BindView(R.id.spinner_unit_program_kerja)
    protected MaterialSpinner programKerja;

    @BindView(R.id.spinner_unit_kegiatan)
    protected MaterialSpinner namaKegiatan;

    @BindView(R.id.text_nominal_anggaran)
    protected TextInputEditText nominalAnggaran;

    @BindView(R.id.rv_bukti_belanja)
    RecyclerView rvBuktiBelanja;

    private DefaultAdapter<BuktiBayarModel, BuktiBayarItemHolder> laporanAdapter;
    private ArrayList<BuktiBayarModel> listBukti = new ArrayList<>();
//    private DefaultAdapter<BuktiBayarModel, BuktiBayarItemHolder> buktibayarAdapter;

    static final Integer CAMERAS = 0x2;
    public int posisi = 0;
    private String idKegiatan = "";
    private String[] listProker;
    private String[] kodeProker;
    private JSONArray jsonArray;

    private static LaporanAnggaranActivity instance;

    public static final LaporanAnggaranActivity instance() {
        if (instance != null)
            return instance;
        throw new RuntimeException("Act not instantiated yet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_anggaran);

        ButterKnife.bind(this);

        instance = this;

        checkRequiredPermissions();

        onShowItem();

        if(getIntent() != null) {
            programKerja.setText(getIntent().getStringExtra("namaProker").replace(":",""));
            namaKegiatan.setText(getIntent().getStringExtra("namaKegiatan").replace(":",""));
            nominalAnggaran.setText(getIntent().getStringExtra("nominal").replace(":", ""));
            idKegiatan = getIntent().getStringExtra("id");
        }

//        retrieveDataUnitKerja();
    }

    @OnClick(R.id.back_task)
    void moveTaskBack(){
        finish();
    }

    @OnClick(R.id.button_add_more_bukti)
    void onAddBuktiBelanja() {
//        BuktiBayarModel buktiBayarModel = new BuktiBayarModel();
//        buktiBayarModel.setNameBarang("");
//        buktiBayarModel.setNominal("");
//        buktiBayarModel.setPathBukti("");
//
//        listBukti.add(buktiBayarModel);
        listBukti.add(new BuktiBayarModel());
        laporanAdapter.notifyDataSetChanged();
        if (listBukti.size() > 0) {
            rvBuktiBelanja.scrollToPosition(listBukti.size() - 1);
        }

//        onShowItem();
//        laporanAdapter.notifyDataSetChanged();
//        if (listBukti.size() > 0) {
//            rvBuktiBelanja.scrollToPosition(listBukti.size() - 1);
//        }
//        laporanAdapter = new AddLaporanAdapter(listBukti, this, this);
//        rvBuktiBelanja.setLayoutManager(new LinearLayoutManager(this));
//        rvBuktiBelanja.setItemAnimator(new DefaultItemAnimator());
//        rvBuktiBelanja.setAdapter(laporanAdapter);


//        Intent intent = new Intent(this, AddBuktiBelanjaActivity.class);
//        intent.putExtra("mode", 1);
//        startActivityForResult(intent, Constanta.REQUEST_ADD_BUKTI);


    }

    private void onShowItem() {
        laporanAdapter = new DefaultAdapter<BuktiBayarModel, BuktiBayarItemHolder>(this, listBukti, new BaseHolder.OnListItemClick() {

            @Override
            public void onItemCheckedChanged(int position, boolean isChecked) {

            }

            @Override
            public void onItemEdit(int position) {

            }

            @Override
            public void onItemRemove(int position) {
                listBukti.remove(position);
                laporanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClick(int position, int actionType) {

            }

            @Override
            public void onItemClick(int position, int actionType, View view) {

            }
        }, new BaseAdapter.BaseAdapterDelegate<BuktiBayarModel, BuktiBayarItemHolder>() {

            @Override
            public BuktiBayarItemHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.item_list_bukti_bayar, parent, false);
                BuktiBayarItemHolder holder = new BuktiBayarItemHolder(view);

//                holder.editName.setTag(
//                        new CreateBusinessUnitWizardTabletFragment.EditNameTextWatcher());
//                holder.editEmail.setTag(
//                        new CreateBusinessUnitWizardTabletFragment.EditEmailTextWatcher());

                return holder;
            }

            @Override
            public void onBindViewHolder(Context context, List<BuktiBayarModel> listItem, BuktiBayarItemHolder holder, int position) {
                if(listItem.get(posisi).getImgFoto() != null) {
                    holder.buktiBayar.setImageBitmap(listItem.get(posisi).getImgFoto());
                }
                holder.buktiBayar.setOnClickListener(view -> {
                    LaporanAnggaranActivity.instance().setPositionItem(position);
                    LaporanAnggaranActivity.instance().postFoto();
                });
            }
        });
        rvBuktiBelanja.setLayoutManager(new LinearLayoutManager(this));
        rvBuktiBelanja.setItemAnimator(new DefaultItemAnimator());
        rvBuktiBelanja.setAdapter(laporanAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Constanta.REQUEST_ADD_BUKTI &&
                resultCode == Activity.RESULT_OK) {

        }
        else if (requestCode == 12 && resultCode == RESULT_OK && null != data) {
            File file = new File(ImagePicker.getImages(data).get(0).getPath());
            Log.e("NAMEIMG", "name image : " + file.getName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

            listBukti.get(posisi).setImgFoto(rotatedBitmap);
            listBukti.get(posisi).setPathBukti(file.getName());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            Log.e("base", encodedImage);

            laporanAdapter.notifyDataSetChanged();
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private boolean requestPermissions() {
        String[] perms = checkRequiredPermissions();
        if (perms.length > 0) {
            ActivityCompat.requestPermissions(this, perms, Constanta.REQ_PERMS_CAMERAS);
            return true;
        }
        return false;
    }

    private String[] checkRequiredPermissions() {
        ArrayList<String> perms = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            perms.add(Manifest.permission.CAMERA);
        }

        return perms.toArray(new String[perms.size()]);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constanta.REQ_PERMS_CAMERAS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getApplicationContext(), "This permission is mandatory", Toast.LENGTH_LONG).show();
                requestPermissions();
                return;

            }
        }
    }

    public void postFoto() {
//        Toast.makeText(this.getApplicationContext(), "click", Toast.LENGTH_LONG).show();
        ImagePicker.create(this)
                .single().showCamera(true)
                .start(12);
    }

    public void setPositionItem(int position) {
        posisi = position;
    }

    @Override
    public void UploadBuktiClicked() {
        requestPermissions();
    }

    @OnClick(R.id.btn_laporkan)
    void onLaporkanClicked() {
        updateLaporanKegiatan();
    }

    private void updateLaporanKegiatan() {
        ProgressUtils.show(this, "", getString(R.string.loading), false);
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_laporkan_kegiatan.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();

        params.put("status_kegiatan", "Sudah Dilaporkan");
        params.put("id_kegiatan", idKegiatan);
        params.setUseJsonStreamer(true);

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                ProgressUtils.dismiss();
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond",response);
                    JSONObject json, jsonData;
                    try {
                        json = new JSONObject(response);
                        Log.d("respond","update data : " + json.getString("resultCode"));

                        if(json.getString("resultCode").equals(Constanta.OK)) {
                            Toast.makeText(getApplicationContext(), json.getString("messageText"), Toast.LENGTH_LONG).show();
                            Thread myThread = new Thread(){
                                @Override
                                public void run(){
                                    try{
                                        sleep(3000);
                                        finish();
                                    } catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            myThread.start();
                        } else {
                            Toast.makeText(getApplicationContext(), json.getString("messageText"), Toast.LENGTH_LONG).show();
                            Thread myThread = new Thread(){
                                @Override
                                public void run(){
                                    try{
                                        sleep(3000);
                                        finish();
                                    } catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            myThread.start();
                        }
                    } catch (JSONException ex) {

                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                ProgressUtils.dismiss();
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
}
