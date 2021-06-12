package com.example.donelistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donelistapp.api.BaseApiService;
import com.example.donelistapp.api.UtilsApi;
import com.example.donelistapp.model.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ActivityFragment extends Fragment {

    BaseApiService mApiService;
    Context context;
    Button btnTambah;
    TextView tvTitle;
    EditText etActivity;
    String id, nama, activity;
    SharedPrefManager sharedPrefManager;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        context = getActivity();
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(context);
        tvTitle = view.findViewById(R.id.tv_title2);
        btnTambah = view.findViewById(R.id.btn_tambah);
        etActivity = view.findViewById(R.id.et_activity);
        fragmentManager = getParentFragmentManager();

        nama = sharedPrefManager.getSPNama();
        id = sharedPrefManager.getSPId();
        tvTitle.setText("what do you gonna do " + nama + " ?");

        btnTambah.setOnClickListener(v -> simpanAktivitas());
        return view;
    }

    private void simpanAktivitas() {
        activity = etActivity.getText().toString();
        mApiService.tambahActivity(id, activity).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        String error = jsonRESULTS.getString("error");
                        String message = jsonRESULTS.getString("message");
                        if (error.equals("false")){
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            fragmentManager.popBackStack();
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("debug", "onResponse: tidak berhasil");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}