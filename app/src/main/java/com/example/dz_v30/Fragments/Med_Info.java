package com.example.dz_v30.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dz_v30.Activities.ApplicationClass;
import com.example.dz_v30.Adapters.med_info_adapter;
import com.example.dz_v30.Models.meds_list_model;
import com.example.dz_v30.R;

import java.util.ArrayList;

public class Med_Info extends Fragment {
    ArrayList<meds_list_model> meds;
    RecyclerView recyclerView;
    View view;
    public static RecyclerView.Adapter adapter;

    public Med_Info(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_med_info, container, false);
        recyclerView = view.findViewById(R.id.med_info_list);

        meds = ApplicationClass.meds;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new med_info_adapter(this.getActivity(),meds);
        recyclerView.setAdapter(adapter);
        return view;
    }
}