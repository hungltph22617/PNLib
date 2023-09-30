package com.example.pnlib.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.Database.DBHelper;

public class ThuthuDao {
    public Boolean ListTT(Context context, String matt, String matkhau) {
        DBHelper DBHelper = new DBHelper(context);
        Cursor cursor = DBHelper.Getdata("SELECT * FROM THUTHU");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                if (matt.equalsIgnoreCase(cursor.getString(0)) && matkhau.equalsIgnoreCase(cursor.getString(2))) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt", cursor.getString(0));
                    editor.putString("hoten", cursor.getString(1));
                    editor.putString("matkhau", cursor.getString(2));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
    }


    public String updateTT(Context context, String username, String oldPass, String newPass) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATT= ? AND MATKHAU =?", new String[]{username, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = db.update("THUTHU", contentValues, "matt = ?", new String[]{username});
            if (check == -1) {
                return "Cập nhật thất Bại";
            } else {
                return "Thành công";
            }
        }
        return "Mật khẩu không đúng";
    }

    public Boolean Createaccount(Context context, String tk, String pass, String hoten) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", tk);
        contentValues.put("hoten", hoten);
        contentValues.put("matkhau", pass);
        long check = db.insert("THUTHU", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }
}
