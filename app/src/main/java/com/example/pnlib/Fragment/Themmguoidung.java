package com.example.pnlib.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Adapter.TndungAdapter;
import com.example.pnlib.Dao.ThuthuDao;
import com.example.pnlib.R;
import com.example.pnlib.model.acccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Themmguoidung extends Fragment {
    RecyclerView rectnd;
    FloatingActionButton fltnd;
    ThuthuDao dao;
    TndungAdapter adapter;
    ArrayList<acccount> list = new ArrayList<>();
    public Themmguoidung() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_themmguoidung, container, false);
        rectnd = view.findViewById(R.id.recytnd);
        fltnd = view.findViewById(R.id.fltnd);
        Loadrecynd();
        fltnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnd();
            }
        });
        return view;
    }

    private void addnd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.addtndung, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputEditText edtmatt = view.findViewById(R.id.edtmatt);
        TextInputEditText edttentt = view.findViewById(R.id.edttentt);
        TextInputEditText edtmktt = view.findViewById(R.id.edtmktt);
        Button btntnd = view.findViewById(R.id.btntnd);
        Button btnhnd = view.findViewById(R.id.btnhnd);
        btntnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mand = edtmatt.getText().toString();
                String tennd = edttentt.getText().toString();
                String mknd = edtmktt.getText().toString();
                if(mand.equals("") || tennd.equals("") || mknd.equals("")){
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check= dao.AddAccount(getActivity(), edtmatt.getText().toString(), edttentt.getText().toString(), edtmktt.getText().toString());
                    if(check){
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        edtmatt.setText("");
                        edttentt.setText("");
                        edtmktt.setText("");
                        Loadrecynd();
                    }else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnhnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtmatt.setText("");
                edttentt.setText("");
                edtmktt.setText("");
            }
        });
    }
    public void Loadrecynd(){
        dao = new ThuthuDao();
        list = dao.Listtt(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rectnd.setLayoutManager(manager);
        adapter = new TndungAdapter(getActivity(), list);
        rectnd.setAdapter(adapter);
    }
}