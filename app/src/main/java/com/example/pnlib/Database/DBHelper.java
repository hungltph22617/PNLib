package com.example.pnlib.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String NAME_DB = "DuanmauLTMT";
    public static final int VERSION_DB = 2;
    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbThuThu = "CREATE TABLE THUTHU(" +
                "matt TEXT PRIMARY KEY," +
                "hoten TEXT," +
                "matkhau TEXT)";
        db.execSQL(dbThuThu);

        String dbThanhVien ="CREATE TABLE THANHVIEN(" +
                "matv INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoten TEXT," +
                "namsinh INTEGER)";
        db.execSQL(dbThanhVien);

        String dbLoai ="CREATE TABLE LOAISACH(" +
                "maloai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenloai TEXT)";
        db.execSQL(dbLoai);

        String dbSach ="CREATE TABLE SACH(" +
                "masach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tensach TEXT," +
                "giathue INTEGER," +
                "anh TEXT,"+
                "maloai INTEGER REFERENCES LOAISACH(maloai)," +
                "tenloai TEXT)";
        db.execSQL(dbSach);

        String dbPhieuMuon ="CREATE TABLE PHIEUMUON(" +
                "mapm INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matv INTEGER REFERENCES THANHVIEN(matv)," +
                "matt TEXT REFERENCES THUTHU(matt)," +
                "masach INTEGER REFERENCES SACH(masach)," +
                "ngay TEXT," +
                "trasach INTEGER," +
                "tienthue INTEGER)";
        db.execSQL(dbPhieuMuon);
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Learn Java'),(2,'Learn Python'),(3, 'Learn Web')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Java 1', 2500, 'https://product.hstatic.net/200000343865/product/1_9544a3ba5bd64806ab59f7fd9fafcf13_ea18ba498dbf48458655f34dd7c049e8_master.jpg', 1, 'Learn Java')");
        db.execSQL("INSERT INTO THUTHU VALUES ('admin','Lê Thế Hùng','hung123')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Văn A','2000'),(2,'Nguyễn Văn B','2000')");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'admin', 1, '16/09/2023', 1, 2500)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!= newVersion){
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
    public Cursor Getdata(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
