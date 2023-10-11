package com.example.pnlib.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Adapter.PhieumuonAdapter;
import com.example.pnlib.Dao.PhieumuonDao;
import com.example.pnlib.Dao.SachDao;
import com.example.pnlib.Dao.ThanhvienDao;
import com.example.pnlib.R;
import com.example.pnlib.model.thanhvien;
import com.example.pnlib.model.sach;
import com.example.pnlib.model.phieumuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PhieumuonFragment extends Fragment {
    RecyclerView recypm;
    FloatingActionButton flpm;
    PhieumuonDao dao;
    PhieumuonAdapter adapter;
    ArrayList<phieumuon> list = new ArrayList<>();
    public PhieumuonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoadon, container, false);
        recypm = view.findViewById(R.id.recypm);
        flpm = view.findViewById(R.id.flpm);
        loadrecypm();
        flpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additempm();
            }
        });
        return view;
    }

    private void additempm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.addpm,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Spinner spntv = view.findViewById(R.id.spntv);
        Spinner spnsach = view.findViewById(R.id.spnsach);
        TextView edttgt = view.findViewById(R.id.edttgt);
        Button btntpm = view.findViewById(R.id.btntpm);
        spnsach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnsach.getSelectedItem();
                edttgt.setText(hsSach.get("giathue").toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btntpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spntv.getSelectedItem();
                int matv = (int) hsTV.get("matv");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnsach.getSelectedItem();
                int masach = (int) hsSach.get("masach");
                int tien = (int) hsSach.get("giathue");
                themPhieuMuon(matv, masach, tien,dialog);
            }
        });
        getDataSach(spnsach);
        getDataThanhVien(spntv);
    }
    private void themPhieuMuon(int matv, int masach, int tien ,Dialog dialog) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(date);
        phieumuon pm = new phieumuon(matv, matt, masach, ngay, 0,tien);
        boolean kiemtra = dao.Addpm(getContext(), pm);
        if (kiemtra) {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thành Công", Toast.LENGTH_SHORT).show();
            loadrecypm();
            dialog.dismiss();
        } else {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thất Bại", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadrecypm(){
        dao = new PhieumuonDao();
        list = dao.Listpmm(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recypm.setLayoutManager(manager);
        adapter = new PhieumuonAdapter(getActivity(), list);
        recypm.setAdapter(adapter);
    }
    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhvienDao thanhVienDao = new ThanhvienDao();
        ArrayList<thanhvien> list = thanhVienDao.ListTV(getContext());
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (thanhvien thanhVien : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", thanhVien.getMatv());
            hs.put("hoten", thanhVien.getTentv());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listHM, android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1}
        );
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDao dao = new SachDao();
        ArrayList<sach> list = dao.Listsach(getContext());
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (sach book : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", book.getMas());
            hs.put("tensach", book.getTens());
            hs.put("giathue", book.getGts());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listHM, android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }
}