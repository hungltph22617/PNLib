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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pnlib.Dao.LoaisachDao;
import com.example.pnlib.Dao.SachDao;
import com.example.pnlib.R;
import com.example.pnlib.model.sach;
import com.example.pnlib.model.loaisach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    public Context mContext;
    public ArrayList<sach> list = new ArrayList<>();

    public SachAdapter(Context mContext, ArrayList<sach> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itembook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sach book = list.get(position);
        Glide.with(mContext).load(book.getAnh()).into(holder.imgls);
        holder.txtts.setText(book.getTens());
        holder.txtgts.setText(String.valueOf(book.getGts()));
        holder.txtmls.setText(list.get(position).getMls()+":");
        holder.txttenls.setText(list.get(position).getTls());
        holder.deles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có muốn xóa không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SachDao dao = new SachDao();
                        int check = dao.deletes(mContext, list.get(holder.getAdapterPosition()).getMas());
                        switch (check) {
                            case 1:
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = dao.Listsach_tenloai(mContext);
                                notifyDataSetChanged();
                                break;

                            case -1:
                                Toast.makeText(mContext, "Không thể xóa sách này vì phiếu mượn có", Toast.LENGTH_SHORT).show();
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
        holder.edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                v = inflater.inflate(R.layout.editbook, null);
                builder.setView(v);
                Dialog dialog = builder.create();
                dialog.show();
                TextInputEditText edtts = v.findViewById(R.id.edtts);
                TextInputEditText edtgs = v.findViewById(R.id.edtgs);
                TextInputEditText edtanh = v.findViewById(R.id.edtanh);
                Button btnss = v.findViewById(R.id.btnss);
                Spinner spnnmls = v.findViewById(R.id.spnnmls);
                edtts.setText(book.getTens());
                edtgs.setText(String.valueOf(book.getGts()));
                edtanh.setText(book.getAnh());
                btnss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tens = edtts.getText().toString();
                        String gs = edtgs.getText().toString();
                        String anh = edtanh.getText().toString();
                        if (tens.equals("") || anh.equals("") || gs.equals("")) {
                            Toast.makeText(mContext, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else{
                            HashMap<String, Object> hsTV = (HashMap<String, Object>) spnnmls.getSelectedItem();
                            int maloai = Integer.parseInt(String.valueOf(hsTV.get("maloai")));
                            sach book = new sach(list.get(holder.getAdapterPosition()).getMas(), edtts.getText().toString(), Integer.parseInt(edtgs.getText().toString()), edtanh.getText().toString(), maloai);
                            SachDao dao = new SachDao();
                            String check = dao.updates(mContext, String.valueOf(list.get(holder.getAdapterPosition()).getMas()), book);
                            Toast.makeText(mContext, check, Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = dao.Listsach_tenloai(mContext);
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }
                    }
                });
                getDataSach(spnnmls);
            }
        });
    }
    private void getDataSach(Spinner spnSach) {
        LoaisachDao dao1 = new LoaisachDao();
        ArrayList<loaisach> list = dao1.getLoaiSach(mContext);
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (loaisach x : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", x.getMatl());
            hs.put("maloai_tenloai", x.getMatl() + ":" + x.getTentl());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                mContext, listHM, android.R.layout.simple_list_item_1,
                new String[]{"maloai_tenloai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtts, txtgts, txtmls, txttenls;
        ImageView imgls, deles, edits;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgts = itemView.findViewById(R.id.txtgs);
            txtts = itemView.findViewById(R.id.txtts);
            txtmls = itemView.findViewById(R.id.txtmls);
            imgls = itemView.findViewById(R.id.imgsach);
            deles = itemView.findViewById(R.id.deles);
            edits = itemView.findViewById(R.id.edits);
            txttenls = itemView.findViewById(R.id.txttenls);
        }
    }
}
