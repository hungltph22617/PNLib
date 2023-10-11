package com.example.pnlib.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Dao.PhieumuonDao;
import com.example.pnlib.Dao.SachDao;
import com.example.pnlib.Dao.ThanhvienDao;
import com.example.pnlib.R;
import com.example.pnlib.model.thanhvien;
import com.example.pnlib.model.sach;
import com.example.pnlib.model.phieumuon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PhieumuonAdapter extends RecyclerView.Adapter<PhieumuonAdapter.ViewHolder>{
    Context mContext;
    ArrayList<phieumuon> list = new ArrayList<>();

    public PhieumuonAdapter(Context mContext, ArrayList<phieumuon> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itempm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        phieumuon pm = list.get(position);
        String trangthai = null;
        if(list.get(position).getTrasach() == 0){
            trangthai = "Chưa trả sách";
            holder.btntrasach.setVisibility(View.VISIBLE);
        }else{
            trangthai = "Đã trả sách";
            holder.btntrasach.setVisibility(View.GONE);
            holder.itemdoimauback.setBackgroundColor(Color.GRAY);
        }
        holder.edtttvm.setText(pm.getTentv());
        holder.edttsm.setText(pm.getTensach());
        holder.edtgts.setText(String.valueOf(pm.getTienthue()));
        holder.edtnms.setText(pm.getNgay());
        holder.edtttm.setText("" + trangthai);
        holder.edtttt.setText(pm.getTentt());
        holder.btntrasach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieumuonDao dao = new PhieumuonDao();
                Boolean kiemtra = dao.thayDoiTrangThai(mContext, list.get(holder.getAdapterPosition()).getMapm());
                if (kiemtra) {
                    list.clear();
                    list = dao.Listpmm(mContext);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Thay đổi trạng thái thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(mContext, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.edipm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                v = inflater.inflate(R.layout.editpm, null);
                builder.setView(v);
                Dialog dialog  = builder.create();
                dialog.show();
                Spinner spnstv = v.findViewById(R.id.spnstv);
                getDataThanhVien(spnstv);
                Spinner spmssach = v.findViewById(R.id.spmssach);
                getDataSach(spmssach);
                TextView edtsgt = v.findViewById(R.id.edtsgt);
                Button btnspm = v.findViewById(R.id.btnspm);
                spmssach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spmssach.getSelectedItem();
                        edtsgt.setText(hsSach.get("giathue").toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnspm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hsTV = (HashMap<String, Object>) spnstv.getSelectedItem();
                        int matv = (int) hsTV.get("matv");
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spmssach.getSelectedItem();
                        int masach = (int) hsSach.get("masach");
                        int tien = (int) hsSach.get("giathue");
                        int id = pm.getMapm();
                        suapm(id, matv, masach,tien, dialog);
                    }
                });
            }
        });
        holder.deletepm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có muốn xóa không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhieumuonDao dao = new PhieumuonDao();
                        int check = dao.deletepm(mContext, list.get(holder.getAdapterPosition()).getMapm());
                        switch (check) {
                            case 1:
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = dao.Listpmm(mContext);
                                notifyDataSetChanged();
                                break;

                            case 0:
                                Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
    private void suapm(int id, int matv, int masach, int tien ,Dialog dialog) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(date);
        phieumuon pm = new phieumuon(id, matv, matt, masach, ngay, 0,tien);
        PhieumuonDao dao = new PhieumuonDao();
        String kiemtra = dao.udatepm(mContext, id, pm);
        Toast.makeText(mContext, kiemtra, Toast.LENGTH_SHORT).show();
        list.clear();
        list = dao.Listpmm(mContext);
        dialog.dismiss();
        notifyDataSetChanged();
    }
    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhvienDao thanhVienDao = new ThanhvienDao();
        ArrayList<thanhvien> list = thanhVienDao.ListTV(mContext);
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (thanhvien thanhVien : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", thanhVien.getMatv());
            hs.put("hoten", thanhVien.getTentv());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                mContext, listHM, android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1}
        );
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDao dao = new SachDao();
        ArrayList<sach> list = dao.Listsach(mContext);
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (sach book : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", book.getMas());
            hs.put("tensach", book.getTens());
            hs.put("giathue", book.getGts());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                mContext, listHM, android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView edtttvm, edttsm, edtgts, edtnms, edtttm, edtttt;
        Button btntrasach;
        ImageView deletepm, edipm;
        LinearLayout itemdoimauback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deletepm = itemView.findViewById(R.id.deletepm);
            edipm = itemView.findViewById(R.id.edipm);
            btntrasach = itemView.findViewById(R.id.btntrasach);
            edttsm = itemView.findViewById(R.id.edttsm);
            edtgts = itemView.findViewById(R.id.edtgts);
            edtnms = itemView.findViewById(R.id.edtnms);
            edtttm = itemView.findViewById(R.id.edtttm);
            edtttt = itemView.findViewById(R.id.edtttt);
            edtttvm = itemView.findViewById(R.id.edtttvm);
            itemdoimauback = itemView.findViewById(R.id.itemdoimauback);
        }
    }
}
