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

import com.example.pnlib.Dao.LoaisachDao;
import com.example.pnlib.R;
import com.example.pnlib.model.loaisach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoaisachAdapter extends RecyclerView.Adapter<LoaisachAdapter.ViewHolder>{
    public Context mContext;
    public ArrayList<loaisach> list = new ArrayList<>();
    LoaisachDao dao;

    public LoaisachAdapter(Context mContext, ArrayList<loaisach> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemtheloai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loaisach loaisach = list.get(position);
        holder.txttentl.setText(loaisach.getTentl());
        holder.edittl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                v = inflater.inflate(R.layout.editls, null);
                builder.setView(v);
                Dialog dialog = builder.create();
                dialog.show();
                TextInputEditText edttl = v.findViewById(R.id.edttl);
                Button btnuls = v.findViewById(R.id.btnuls);

                edttl.setText(loaisach.getTentl());
                btnuls.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //edttl.setText(loaisach.getTentl());
                        if(edttl.getText().toString().equals("")){
                            Toast.makeText(mContext, "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                        }else{
                            loaisach loaisach1 = new loaisach(list.get(holder.getAdapterPosition()).getMatl(), edttl.getText().toString());
                            dao = new LoaisachDao();
                            String check = dao.updatels(mContext, String.valueOf(list.get(holder.getAdapterPosition()).getMatl()), loaisach1);
                            Toast.makeText(mContext, check, Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = dao.getLoaiSach(mContext);
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        holder.deletetl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có muốn xóa loại sách không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaisachDao dao = new LoaisachDao();
                        int check = dao.deletels(mContext, list.get(holder.getAdapterPosition()).getMatl());
                        switch (check) {
                            case 1:
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = dao.getLoaiSach(mContext);
                                notifyDataSetChanged();
                                break;
                            case -1:
                                Toast.makeText(mContext, "Không thể xóa loại sách này vì đã có sách thuộc thể loại này", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(mContext, "Xóa không thành công", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttentl;
        ImageView deletetl, edittl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttentl = itemView.findViewById(R.id.txttentl);
            deletetl = itemView.findViewById(R.id.deletels);
            edittl = itemView.findViewById(R.id.editls);
        }
    }
}
