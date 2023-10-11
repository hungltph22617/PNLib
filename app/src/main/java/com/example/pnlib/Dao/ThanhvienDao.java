package com.example.pnlib.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.Database.DBHelper;
import com.example.pnlib.model.thanhvien;

import java.util.ArrayList;

public class ThanhvienDao {
    DBHelper DBHelper;

    public ArrayList<thanhvien> ListTV(Context context){
        ArrayList<thanhvien> list=new ArrayList<>();
        DBHelper DBHelper = new DBHelper(context);
        Cursor cursor= DBHelper.Getdata("SELECT * FROM THANHVIEN");
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()){
                list.add(new thanhvien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)));
            }
        }
        return list;
    }
    public boolean addTV(Context context,String hoten,int namsinh){
        DBHelper dbHelper=new DBHelper(context);
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("hoten",hoten);
        contentValues.put("namsinh",namsinh);
        long check =  sqLiteDatabase.insert("THANHVIEN",null,contentValues);;
        if(check==-1){
            return false;
        } else {
            return true;
        }
    }

    public int deleteTV(Context context, int id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE matv =?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = sqLiteDatabase.delete("THANHVIEN", "matv =?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public String updateTV(Context context, int id, thanhvien thanhVien) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN WHERE matv= ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            ContentValues contentValues=new ContentValues();
            contentValues.put("hoten",thanhVien.getTentv());
            contentValues.put("namsinh",thanhVien.getNamsinh());
            long check = sqLiteDatabase.update("THANHVIEN", contentValues, "matv = ?", new String[]{String.valueOf(id)});
            if (check == -1) {
                return "Cập nhật thất Bại";
            } else {
                return "Thành công";
            }
        }
        return "Thành công";
    }

}
