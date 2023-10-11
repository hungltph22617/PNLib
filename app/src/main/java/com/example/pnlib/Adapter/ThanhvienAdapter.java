package com.example.pnlib.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Dao.ThanhvienDao;
import com.example.pnlib.R;
import com.example.pnlib.model.thanhvien;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhvienAdapter extends RecyclerView.Adapter<ThanhvienAdapter.ViewHolder>{
    Context mContext;
    ArrayList<thanhvien> list = new ArrayList<>();

    public ThanhvienAdapter(Context mContext, ArrayList<thanhvien> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemtv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        thanhvien vien = list.get(position);
        holder.txtttv.setText(vien.getTentv());
        holder.txtnstv.setText(String.valueOf(vien.getNamsinh()));
        holder.deletetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có muốn xóa TV không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThanhvienDao dao = new ThanhvienDao();
                        int check = dao.deleteTV(mContext, list.get(holder.getAdapterPosition()).getMatv());
                        switch (check) {
                            case 1:
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = dao.ListTV(mContext);
                                notifyDataSetChanged();
                                break;
                            case -1:
                                Toast.makeText(mContext, "Không thể xóa thành viên  này vì đã có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(mContext, "xóa thành viên không thành công ", Toast.LENGTH_SHORT).show();
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
        holder.edittv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                View view = inflater.inflate(R.layout.edittv, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                TextInputEditText edtstentv = view.findViewById(R.id.edtstentv);
                TextInputEditText edtsnstv = view.findViewById(R.id.edtsnstv);
                Button btnsuatv = view.findViewById(R.id.btnsuatv);
                edtstentv.setText(vien.getTentv());
                edtsnstv.setText(String.valueOf(vien.getNamsinh()));
                btnsuatv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ThanhvienDao dao = new ThanhvienDao();
                        String tentv = edtstentv.getText().toString();
                        String nstv = edtsnstv.getText().toString();
                        if(tentv.equals("") || nstv.equals("")){
                            Toast.makeText(mContext, "Không được để trống", Toast.LENGTH_SHORT).show();
                        }else{
                            thanhvien thanhVien = new thanhvien(list.get(holder.getAdapterPosition()).getMatv(), tentv, Integer.parseInt(nstv));
                            String check = dao.updateTV(mContext, list.get(holder.getAdapterPosition()).getMatv(), thanhVien);
                            Toast.makeText(mContext, check, Toast.LENGTH_SHORT).show();
                            list.clear();
                            dialog.dismiss();
                            list = dao.ListTV(mContext);
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtttv, txtnstv;
        ImageView deletetv, edittv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtttv = itemView.findViewById(R.id.txttentv);
            txtnstv = itemView.findViewById(R.id.txtnstv);
            deletetv = itemView.findViewById(R.id.deletetv);
            edittv = itemView.findViewById(R.id.edittv);
        }
    }
}
