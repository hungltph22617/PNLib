package com.example.pnlib.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Adapter.Top10Adapter;
import com.example.pnlib.Dao.DoanhthuDao;
import com.example.pnlib.R;
import com.example.pnlib.model.sach;

import java.util.ArrayList;

public class Top10Fragment extends Fragment {
    RecyclerView recytop10;
    Top10Adapter adapter;
    ArrayList<sach> list = new ArrayList<>();
    DoanhthuDao dao;

    public Top10Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        recytop10 = view.findViewById(R.id.recytop10);
        dao = new DoanhthuDao();
        list = dao.getTop10(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recytop10.setLayoutManager(manager);
        adapter = new Top10Adapter(getActivity(), list);
        recytop10.setAdapter(adapter);
        return view;
    }
}