package com.example.pnlib.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pnlib.Dao.DoanhthuDao;
import com.example.pnlib.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class DoanhthuFragment extends Fragment {
    TextInputEditText edttn, edtdn;
    Button btnkq;
    TextView txtkq;

    public DoanhthuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanhthu, container, false);
        btnkq = view.findViewById(R.id.btnkq);
        edttn = view.findViewById(R.id.edttn);
        edtdn = view.findViewById(R.id.edtdn);
        txtkq = view.findViewById(R.id.txtkq);
        Calendar calendar = Calendar.getInstance();
        edttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "",thang = "";
                        if(dayOfMonth < 10){
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if((month + 1) < 10){
                            thang = "0" + (month + 1);
                        } else {
                            thang=String.valueOf((month + 1));
                        }
                        edttn.setText( year + "/" +thang + "/" +ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        edtdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "", thang = "";
                        if(dayOfMonth < 10){
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if((month + 1) < 10){
                            thang = "0"+(month + 1);
                        } else {
                            thang = String.valueOf((month + 1));
                        }
                        edtdn.setText( year + "/" +thang + "/" +ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();
            }
        });
        btnkq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoanhthuDao dao = new DoanhthuDao();
                int doanhthu = dao.getDoanhThu(getContext(),edttn.getText().toString(),edtdn.getText().toString());
                txtkq.setText("Doanh thu: "+doanhthu);
            }
        });

        return view;
    }
}