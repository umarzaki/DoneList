package com.example.donelistapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LandingPageFragment extends Fragment {

    Button btn_daftar, btn_masuk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing_page, container, false);

        btn_daftar = view.findViewById(R.id.btnDaftar);
        btn_masuk = view.findViewById(R.id.btnMasuk);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        btn_daftar.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.hostFragmentLayout, daftarFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit());
        btn_masuk.setOnClickListener(v -> fragmentManager.beginTransaction()
                .replace(R.id.hostFragmentLayout, masukFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("homepage") // name can be null
                .commit());
        return view;
    }
}