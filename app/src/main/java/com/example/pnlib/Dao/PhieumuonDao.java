package com.example.pnlib.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.Database.DBHelper;
import com.example.pnlib.model.phieumuon;

import java.util.ArrayList;

public class PhieumuonDao {
    DBHelper DBHelper;

//    public ArrayList<phieumuon> Listpm(Context context) {
//        ArrayList<phieumuon> list = new ArrayList<>();
//        DBHelper DBHelper = new DBHelper(context);
//        Cursor cursor = DBHelper.Getdata("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue\n" +
//                "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc \n" +
//                "WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.mapm DESC");
//        if (cursor.getCount() != 0) {
//            while (cursor.moveToNext()) {
//                list.add(new phieumuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9)));}
//        }
//        return list;
//    }
    public ArrayList<phieumuon> Listpmm(Context context) {
        ArrayList<phieumuon> list = new ArrayList<>();
        DBHelper DBHelper = new DBHelper(context);
        Cursor cursor = DBHelper.Getdata("SELECT pm.mapm, tv.hoten, sc.tensach, pm.tienthue, pm.ngay, pm.trasach, tt.hoten\n" +
                "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc \n" +
                "WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.mapm DESC");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new phieumuon(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6)));}
        }
        return list;
    }

    public boolean thayDoiTrangThai(Context context, int mapm) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "mapm = ?", new String[]{String.valueOf(mapm)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean Addpm(Context context, phieumuon pm) {
        DBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matv", pm.getMatv());
        contentValues.put("matt", pm.getMatt());
        contentValues.put("masach", pm.getMasach());
        contentValues.put("ngay", pm.getNgay());
        contentValues.put("trasach", pm.getTrasach());
        contentValues.put("tienthue", pm.getTienthue());
        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    public int deletepm(Context context, int id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("PHIEUMUON", "mapm=?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;

        }
    }

    public String udatepm(Context context, int mapm, phieumuon phieumuon) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON WHERE mapm =?", new String[]{String.valueOf(mapm)});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matv", phieumuon.getMatv());
            contentValues.put("matt", phieumuon.getMatt());
            contentValues.put("masach", phieumuon.getMasach());
            contentValues.put("ngay", phieumuon.getNgay());
            contentValues.put("trasach", phieumuon.getTrasach());
            contentValues.put("tienthue", phieumuon.getTienthue());
            long check = db.update("PHIEUMUON", contentValues, "mapm =?", new String[]{String.valueOf(mapm)});
            if (check == -1) {
                return "Cập nhật thất Bại";
            } else {
                return "Cập nhận thành công";
            }
        }
        return "Cập nhận thành công";
    }
}
