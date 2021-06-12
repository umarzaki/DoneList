package com.example.donelistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class masukFragment extends Fragment {

    Context context;
    BaseApiService mApiService;
    Button btnMasuk;
    EditText etPassword, etEmail;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_masuk, container, false);

        mApiService = UtilsApi.getAPIService();
        context = getActivity();
        btnMasuk = view.findViewById(R.id.btn_masukFragment);
        etEmail = view.findViewById(R.id.et_email_masuk);
        etPassword = view.findViewById(R.id.et_password_masuk);
        sharedPrefManager = new SharedPrefManager(context);

        btnMasuk.setOnClickListener(v -> login());
        return view;
    }

    private void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mApiService.login(email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        String error = jsonRESULTS.getString("error");
                        if (error.equals("false")){
                            Toast.makeText(context, "login berhasil", Toast.LENGTH_SHORT).show();
                            String id = jsonRESULTS.getString("id");
                            String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                            String email = jsonRESULTS.getJSONObject("user").getString("email");

                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_IS_ACTIVE, true);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, id);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);

                            startActivity(new Intent(context, HomePageActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            getActivity().finish();

                        } else {
                            String message = jsonRESULTS.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("error", "onResponse: tidak berhasil");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}