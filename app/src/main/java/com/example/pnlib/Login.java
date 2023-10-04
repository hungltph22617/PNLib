package com.example.pnlib;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pnlib.Dao.ThuthuDao;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    Button login;
    TextInputEditText pass, account;
    ThuthuDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pass = findViewById(R.id.pass);
        account = findViewById(R.id.account);
        login = findViewById(R.id.login);
        dao = new ThuthuDao();
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String u = preferences.getString("USERNAME", "");
        String p = preferences.getString("PASSWORD", "");
        pass.setText(p);
        account.setText(u);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Boolean kiemtra = dao.ListTT(Login.this, account.getText().toString().trim(),pass.getText().toString().trim());
                    if (kiemtra) {
                        Intent intent = new Intent(Login.this, HomeTong.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Bạn cần nhập account && password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("CHECK", e.toString());
                }
            }
        });
    }
}