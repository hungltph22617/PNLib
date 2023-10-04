package com.example.pnlib.Adapter;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.Dao.PhieumuonDao;
import com.example.pnlib.Dao.ThanhvienDao;
import com.example.pnlib.Dao.ThuthuDao;
import com.example.pnlib.R;
import com.example.pnlib.model.acccount;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TndungAdapter extends RecyclerView.Adapter<TndungAdapter.ViewHolder>{
    Context mContext;
    ArrayList<acccount> list = new ArrayList<>();
    ThuthuDao dao;

    public TndungAdapter(Context mContext, ArrayList<acccount> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemtndung, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        acccount acccount = list.get(position);
        holder.txtmand.setText(acccount.getMand());
        holder.txttennd.setText(acccount.getTennd());
        holder.txtmknd.setText(acccount.getMknd());
//        holder.editnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
//                View view = inflater.inflate(R.layout.edittndung, null);
//                builder.setView(view);
//                Dialog dialog = builder.create();
//                dialog.show();
//                TextInputEditText edtsmatt = view.findViewById(R.id.edtsmatt);
//                TextInputEditText edtstentt = view.findViewById(R.id.edtstentt);
//                TextInputEditText edtsmktt = view.findViewById(R.id.edtsmktt);
//                Button btnsnd = view.findViewById(R.id.btnsnd);
//                Button btnshnd = view.findViewById(R.id.btnshnd);
//                btnsnd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String mand = edtsmatt.getText().toString();
//                        String tennd = edtstentt.getText().toString();
//                        String mknd = edtsmktt.getText().toString();
//                        if(mand.equals("") || tennd.equals("") || mknd.equals("")){
//                            Toast.makeText(mContext, "Không được để trống", Toast.LENGTH_SHORT).show();
//                        }else{
//                            boolean check= dao.AddAccount(mContext, edtsmatt.getText().toString(), edtstentt.getText().toString(), edtsmktt.getText().toString());
//                            if(check){
//                                Toast.makeText(mContext, "Sủa thành công", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                                edtsmatt.setText("");
//                                edtstentt.setText("");
//                                edtsmktt.setText("");
//                                dao.Listtt(mContext);
//                            }else {
//                                Toast.makeText(mContext, "Sủa thất bại", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                btnshnd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        edtsmatt.setText("");
//                        edtstentt.setText("");
//                        edtsmktt.setText("");
//                    }
//                });
//            }
//        });
        holder.deletend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có muốn xóa khống?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = dao.deletett(mContext, Integer.parseInt(list.get(holder.getAdapterPosition()).getMand()));
                        switch (check) {
                            case 1:
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = dao.Listtt(mContext);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmand, txttennd, txtmknd;
        ImageView deletend, editnd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editnd = itemView.findViewById(R.id.editnd);
            deletend = itemView.findViewById(R.id.deletend);
            txtmand = itemView.findViewById(R.id.txtmand);
            txttennd = itemView.findViewById(R.id.txttennd);
            txtmknd = itemView.findViewById(R.id.txtmknd);
        }
    }
}
