package com.example.pnlib.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.Database.DBHelper;
import com.example.pnlib.model.sach;

import java.util.ArrayList;

public class DoanhthuDao {
    public ArrayList<sach> getTop10(Context context){
        ArrayList<sach> list=new ArrayList<>();
        DBHelper DBHelper =new DBHelper(context);
        Cursor cursor= DBHelper.Getdata("SELECT pm.MASACH, sc.ANH,sc.TENSACH,COUNT(pm.MASACH) FROM PHIEUMUON pm,SACH sc WHERE pm.MASACH = sc.MASACH GROUP BY pm.masach,sc.TENSACH ORDER BY COUNT(pm.MASACH) DESC LIMIT 10");
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()){
                list.add(new sach(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3)));
            }
        }
        return list;
    }
    public int getDoanhThu(Context context,String ngaybatdau,String ngayketthuc){
        ngaybatdau = ngaybatdau.replace("/","");
        ngayketthuc = ngayketthuc.replace("/","");
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = DBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(TIENTHUE) FROM  PHIEUMUON WHERE substr(ngay,7)||substr(ngay,4,2)||substr(ngay,1,2) BETWEEN ? AND ?", new String[]{ngaybatdau,ngayketthuc});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }
}
