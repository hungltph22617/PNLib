package com.example.pnlib.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pnlib.Dao.ThuthuDao;
import com.example.pnlib.Login;
import com.example.pnlib.R;
import com.google.android.material.textfield.TextInputEditText;

public class DoimkFragment extends Fragment {
    TextInputEditText edtmkc, edtmkm, edtnlmkm;
    Button btndmk;

    public DoimkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doimk, container, false);
        edtmkc = view.findViewById(R.id.edtmkc);
        edtmkm = view.findViewById(R.id.edtmkm);
        edtnlmkm = view.findViewById(R.id.edtnlmkm);
        btndmk = view.findViewById(R.id.btndmk);
        btndmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
                String matt = sharedPreferences.getString("matt", "");
                String oldPass = edtmkc.getText().toString();
                String newPass = edtmkm.getText().toString();
                String edtReNewPass = edtnlmkm.getText().toString();
                if (newPass.equals(edtReNewPass)) {
                    ThuthuDao dao = new ThuthuDao();
                    String check = dao.updateTT(getActivity(), matt, oldPass, newPass);
                    Toast.makeText(getActivity(), check, Toast.LENGTH_SHORT).show();
                    edtmkc.setText("");
                    edtmkm.setText("");
                    edtnlmkm.setText("");
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Nhập Mật khẩu không trùng với nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}