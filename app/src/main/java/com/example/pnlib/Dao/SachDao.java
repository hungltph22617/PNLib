package com.example.pnlib.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.Database.DBHelper;
import com.example.pnlib.model.sach;

import java.util.ArrayList;

public class SachDao {
    public ArrayList<sach> Listsach(Context context) {
        ArrayList<sach> list = new ArrayList<>();
        DBHelper DBHelper = new DBHelper(context);
        Cursor cursor = DBHelper.Getdata("SELECT * FROM SACH");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new sach(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            }
        }
        return list;
    }
    public ArrayList<sach> Listsach_tenloai(Context context) {
        ArrayList<sach> list = new ArrayList<>();
        DBHelper DBHelper = new DBHelper(context);
        Cursor cursor = DBHelper.Getdata("SELECT sc.masach,sc.tensach,sc.giathue, sc.anh,ls.maloai,ls.tenloai FROM SACH sc,LOAISACH ls WHERE sc.maloai=ls.maloai;");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new sach(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5)));
            }
        }
        return list;
    }
    public boolean adds(Context context, String tensach, int giathue, String anh, int maloai) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", giathue);
        contentValues.put("anh", anh);
        contentValues.put("maloai", maloai);
        long check = db.insert("SACH", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }
    public int deletes(Context context, int id) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  PHIEUMUON WHERE masach =?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("SACH", "masach = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }
    public String updates(Context context, String masach, sach sach) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SACH WHERE masach =?", new String[]{masach});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tensach", sach.getTens());
            contentValues.put("giathue", sach.getGts());
            contentValues.put("anh", sach.getAnh());
            contentValues.put("maloai", sach.getMls());
            long check = db.update("SACH", contentValues, "masach =?", new String[]{masach});
            if (check == -1) {
                return "Cập nhật thất Bại";
            } else {
                return "Cập nhận thành công";
            }
        }
        return "Cập nhận thành công";
    }
}
