package com.example.pnlib.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Adapter.ThanhvienAdapter;
import com.example.pnlib.Dao.ThanhvienDao;
import com.example.pnlib.R;
import com.example.pnlib.model.thanhvien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhvienFragment extends Fragment {
    RecyclerView recytv;
    FloatingActionButton fltv;
    ThanhvienDao dao;
    ArrayList<thanhvien> list = new ArrayList<>();
    ThanhvienAdapter adapter;

    public ThanhvienFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanhvien, container, false);
        recytv = view.findViewById(R.id.recytv);
        fltv = view.findViewById(R.id.fltv);
        loadrecytv();
        fltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additemtv();
            }
        });
        return view;
    }

    private void additemtv() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.addtv, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputEditText edtttentv = view.findViewById(R.id.edtttentv);
        TextInputEditText edttnstv = view.findViewById(R.id.edttnstv);
        Button btnthemtv = view.findViewById(R.id.btnthemtv);
        btnthemtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentv = edtttentv.getText().toString();
                String nstv = edttnstv.getText().toString();
                if(tentv.equals("") || nstv.equals("")){
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = dao.addTV(getActivity(), tentv, Integer.parseInt(nstv));
                    if (check) {
                        Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        loadrecytv();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Thêm Thành Viên Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void loadrecytv(){
        dao = new ThanhvienDao();
        list = dao.ListTV(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recytv.setLayoutManager(manager);
        adapter = new ThanhvienAdapter(getActivity(), list);
        recytv.setAdapter(adapter);
    }
}