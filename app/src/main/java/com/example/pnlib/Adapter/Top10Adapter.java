package com.example.pnlib.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pnlib.R;
import com.example.pnlib.model.sach;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Viewholder>{
    Context mContext;
    ArrayList<sach> list = new ArrayList<>();

    public Top10Adapter(Context mContext, ArrayList<sach> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemtop10, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        sach sach = list.get(position);
        Glide.with(mContext).load(sach.getAnh()).into(holder.imgtop10);
        holder.txttensachtop10.setText(sach.getTens());
        holder.txtsoluongtop10.setText(String.valueOf(sach.getSoluongdamuon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imgtop10;
        TextView txttensachtop10, txtsoluongtop10;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgtop10 = itemView.findViewById(R.id.imgtop10);
            txttensachtop10 = itemView.findViewById(R.id.txttenstop10);
            txtsoluongtop10 = itemView.findViewById(R.id.txtsltop10);
        }
    }
}
