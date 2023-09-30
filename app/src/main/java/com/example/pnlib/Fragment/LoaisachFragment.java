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

import com.example.pnlib.Adapter.LoaisachAdapter;
import com.example.pnlib.Dao.LoaisachDao;
import com.example.pnlib.R;
import com.example.pnlib.model.loaisach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaisachFragment extends Fragment {
    FloatingActionButton fltheloai;
    RecyclerView recytheloai;
    LoaisachDao dao;
    LoaisachAdapter adapter;
    ArrayList<loaisach> list = new ArrayList<>();

    public LoaisachFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theloai, container, false);
        fltheloai = view.findViewById(R.id.fltheloai);
        recytheloai = view.findViewById(R.id.recytheloai);
        loadform();
        fltheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtl();
            }
        });
        return view;
    }

    private void addtl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.addtheloai, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputEditText edtl = view.findViewById(R.id.edtl);
        //TextInputEditText edtml = view.findViewById(R.id.edml);
        Button btntl = view.findViewById(R.id.btnthemls);
        btntl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenls = edtl.getText().toString();
                if(tenls.equals("")){
                    Toast.makeText(getActivity(), "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check= dao.addls(getActivity(),edtl.getText().toString());
                    if(check){
                        Toast.makeText(getActivity(), "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadform();
                        edtl.setText("");
                    }else {
                        Toast.makeText(getActivity(), "THÊM THẤT BẠI", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void loadform(){
        dao = new LoaisachDao();
        list = dao.getLoaiSach(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recytheloai.setLayoutManager(manager);
        adapter = new LoaisachAdapter(getActivity(), list);
        recytheloai.setAdapter(adapter);
    }
}