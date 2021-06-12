package com.example.donelistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donelistapp.api.BaseApiService;
import com.example.donelistapp.api.UtilsApi;
import com.example.donelistapp.model.Result;
import com.example.donelistapp.model.SharedPrefManager;
import com.example.donelistapp.model.Value;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomePageFragment extends Fragment {

    Context context;
    FloatingActionButton btnFab;
    TextView tvName;
    String nama;
    SharedPrefManager sharedPrefManager;
    RecyclerView activityRecycler;
    List<Result> results;
    BaseApiService mApiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        mApiService = UtilsApi.getAPIService();
        context = getActivity();
        tvName = view.findViewById(R.id.tv_name);
        btnFab = view.findViewById(R.id.fab);
        activityRecycler = view.findViewById(R.id.activityRecycler);

        results = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(context);
        nama = sharedPrefManager.getSPNama();

        tvName.setText("hi " + nama + " !");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        loadData();

        btnFab.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.hostHomePageFragment, ActivityFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit());
        return view;
    }

    private void loadData(){
        mApiService.activityList(nama).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                try {
                    if (value.equals("1")) {
                        results = response.body().getResult();
                        activityRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        activityRecycler.setAdapter(new activityAdapter(getActivity(), results));
                    } else {
                        String message = response.body().getMessage();
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Log.e(TAG, "onResponse: ", e );
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });

    }

}