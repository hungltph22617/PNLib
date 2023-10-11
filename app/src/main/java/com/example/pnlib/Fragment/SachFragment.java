package com.example.pnlib.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Adapter.SachAdapter;
import com.example.pnlib.Dao.LoaisachDao;
import com.example.pnlib.Dao.SachDao;
import com.example.pnlib.R;
import com.example.pnlib.model.sach;
import com.example.pnlib.model.loaisach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SachFragment extends Fragment {
    RecyclerView recys;
    FloatingActionButton fls;
    ArrayList<sach> list = new ArrayList<>();
    SachDao dao;
    SachAdapter adapter;

    public SachFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        recys = view.findViewById(R.id.recybook);
        fls = view.findViewById(R.id.flbook);
        loadrecy();
        fls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.addbook, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                TextInputEditText edts = view.findViewById(R.id.edts);
                TextInputEditText edgs = view.findViewById(R.id.edgs);
                TextInputEditText edanh = view.findViewById(R.id.edanh);
                Button btnts = view.findViewById(R.id.btnts);
                Spinner spnmls = view.findViewById(R.id.spnmls);
                btnts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tens = edts.getText().toString();
                        String anh = edanh.getText().toString();
                        String gts = edgs.getText().toString();
                        if (tens.equals("") || anh.equals("") || gts.equals("")) {
                            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else{
                            HashMap<String, Object> hsTV = (HashMap<String, Object>) spnmls.getSelectedItem();
                            String maloai = String.valueOf(hsTV.get("maloai"));
                            boolean check = dao.adds(getActivity(), edts.getText().toString(), Integer.parseInt(edgs.getText().toString()), edanh.getText().toString(), Integer.parseInt(maloai));
                            if (check) {
                                Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                                loadrecy();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                getDataSach(spnmls);
            }
        });
        return view;
    }

    public void loadrecy(){
        dao = new SachDao();
        list = dao.Listsach_tenloai(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recys.setLayoutManager(manager);
        adapter = new SachAdapter(getActivity(), list);
        recys.setAdapter(adapter);
    }
    private void getDataSach(Spinner spnSach) {
        LoaisachDao dao1 = new LoaisachDao();
        ArrayList<loaisach> list = dao1.getLoaiSach(getContext());
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (loaisach x : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", x.getMatl());
            hs.put("maloai_tenloai", x.getMatl() + ":" + x.getTentl());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listHM, android.R.layout.simple_list_item_1,
                new String[]{"maloai_tenloai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }
}