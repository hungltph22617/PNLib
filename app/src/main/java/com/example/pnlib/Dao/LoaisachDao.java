package com.example.pnlib.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.Database.DBHelper;
import com.example.pnlib.model.loaisach;

import java.util.ArrayList;

public class LoaisachDao {
    public ArrayList<loaisach> getLoaiSach(Context context) {
        ArrayList<loaisach> list = new ArrayList<>();
        DBHelper DBHelper = new DBHelper(context);
        Cursor cursor = DBHelper.Getdata("SELECT * FROM LOAISACH");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new loaisach(cursor.getInt(0), cursor.getString(1)));
            }
        }
        return list;
    }

    public boolean addls(Context context, String tenloai) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = db.insert("LOAISACH", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }
    public int deletels(Context context, int id) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SACH WHERE maloai =?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public String updatels(Context context, String maloai, loaisach loaisach) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH WHERE maloai= ?", new String[]{maloai});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tenloai", loaisach.getTentl());
            long check = db.update("LOAISACH", contentValues, "maloai = ?", new String[]{maloai});
            if (check == -1) {
                return "Cập nhật thất bại";
            } else {
                return "Cập nhật thành công";
            }
        }
        return "Thành công";
    }
}
