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
import android.widget.Toast;

import com.example.donelistapp.api.BaseApiService;
import com.example.donelistapp.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftarFragment extends Fragment {

    BaseApiService mApiService;
    Context context;
    FragmentManager fragmentManager;
    Button btnDaftar;
    EditText etNama, etEmail, etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftar, container, false);

        context = getActivity();
        mApiService = UtilsApi.getAPIService();
        btnDaftar = view.findViewById(R.id.btn_daftarFragment);
        etNama = view.findViewById(R.id.et_nama);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        fragmentManager = getActivity().getSupportFragmentManager();

        btnDaftar.setOnClickListener(v -> registUser());
        return view;
    }

    private void registUser() {
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mApiService.register(nama, email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        String error = jsonRESULTS.getString("error");
                        String message = jsonRESULTS.getString("message");
                        if (error.equals("false")){
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.hostFragmentLayout, LandingPageFragment.class, null)
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null)
                                    .commit();
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