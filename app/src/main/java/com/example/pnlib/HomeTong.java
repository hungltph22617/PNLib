package com.example.pnlib;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pnlib.Fragment.DoanhthuFragment;
import com.example.pnlib.Fragment.DoimkFragment;
import com.example.pnlib.Fragment.LoaisachFragment;
import com.example.pnlib.Fragment.PhieumuonFragment;
import com.example.pnlib.Fragment.SachFragment;
import com.example.pnlib.Fragment.ThanhvienFragment;
import com.example.pnlib.Fragment.Themmguoidung;
import com.example.pnlib.Fragment.Top10Fragment;
import com.google.android.material.navigation.NavigationView;

public class HomeTong extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView nav;
    private static final int Theloai = 0;
    private static final int Sach = 1;
    private static final int Hoadon = 2;
    private static final int Thanhvien = 3;
    private static final int Top = 4;
    private static final int Doanhthu = 5;
    private static final int Nguoidung = 6;
    private static final int Doimk = 7;
    private int Banchinh = Theloai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tong);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);
        replaceFragment(new LoaisachFragment());
        nav.getMenu().findItem(R.id.nav_theloai).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_theloai){
            if(Banchinh != Theloai){
                replaceFragment(new LoaisachFragment());
                Banchinh = Theloai;
            }
        }else if(id == R.id.nav_book){
            if(Banchinh != Sach){
                replaceFragment(new SachFragment());
                Banchinh = Sach;
            }
        }else if(id == R.id.nav_hoadon){
            if(Banchinh != Hoadon){
                replaceFragment(new PhieumuonFragment());
                Banchinh = Hoadon;
            }
        }else if(id == R.id.nav_thanhvien){
            if(Banchinh != Thanhvien){
                replaceFragment(new ThanhvienFragment());
                Banchinh = Thanhvien;
            }
        }else if(id == R.id.nav_topsp){
            if(Banchinh != Top){
                replaceFragment(new Top10Fragment());
                Banchinh = Top;
            }
        }else if(id == R.id.nav_doanhthu){
            if(Banchinh != Doanhthu){
                replaceFragment(new DoanhthuFragment());
                Banchinh = Doanhthu;
            }
        }else if(id == R.id.nav_tnguoidung){
            if(Banchinh != Nguoidung){
                replaceFragment(new Themmguoidung());
                Banchinh = Nguoidung;
            }
        }else if(id == R.id.nav_doimk){
            if(Banchinh != Doimk){
                replaceFragment(new DoimkFragment());
                Banchinh = Doimk;
            }
        }else if(id == R.id.nav_logout){
            showdialoglogout();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showdialoglogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn có chắc muốn đăng xuất không?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeTong.this, Login.class);
                startActivity(intent);
                finish();
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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}