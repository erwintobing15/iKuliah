package com.chupakubera.ipkuliah.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor allMatkul() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.MATKUL, DatabaseHelper.SKS,
                DatabaseHelper.INDEKS, DatabaseHelper.SEMESTER, DatabaseHelper.BOBOT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_MATKUL, columns, null, null, null, null, DatabaseHelper.SEMESTER);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor allJadwal() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.MATKUL_JADWAL, DatabaseHelper.HARI,
                DatabaseHelper.WAKTU, DatabaseHelper.RUANGAN};
        Cursor cursor = database.query(DatabaseHelper.TABLE_JADWAL, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor allCatatan() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.DATESTAMP, DatabaseHelper.KETERANGAN, DatabaseHelper.NOTE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_CATATAN, columns, null, null, null, null, DatabaseHelper.DATESTAMP);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insertMatkul(String matkul, int sks, String indeks, String semester) {

        double bobotNilai = getBobot(indeks, sks);

        ContentValues contentValuesMatkul = new ContentValues();
        contentValuesMatkul.put(DatabaseHelper.MATKUL, matkul);
        contentValuesMatkul.put(DatabaseHelper.SKS, sks);
        contentValuesMatkul.put(DatabaseHelper.INDEKS, indeks);
        contentValuesMatkul.put(DatabaseHelper.SEMESTER, semester);
        contentValuesMatkul.put(DatabaseHelper.BOBOT, bobotNilai);
        database.insert(DatabaseHelper.TABLE_MATKUL, null, contentValuesMatkul);
    }



    public int updateMatkul(long _id, String matkul, int sks, String indeks, String semester) {

        double bobotNilai = getBobot(indeks, sks);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MATKUL, matkul);
        contentValues.put(DatabaseHelper.SKS, sks);
        contentValues.put(DatabaseHelper.INDEKS, indeks);
        contentValues.put(DatabaseHelper.SEMESTER, semester);
        contentValues.put(DatabaseHelper.BOBOT, bobotNilai);
        int i = database.update(DatabaseHelper.TABLE_MATKUL, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void deleteMatkul(long _id) {
        database.delete(DatabaseHelper.TABLE_MATKUL, DatabaseHelper._ID + "=" + _id, null);
    }

    public void insertJadwal(String matkul, String hari, String waktu, String ruangan) {
        ContentValues contentValuesJadwal= new ContentValues();
        contentValuesJadwal.put(DatabaseHelper.MATKUL_JADWAL, matkul);
        contentValuesJadwal.put(DatabaseHelper.HARI, hari);
        contentValuesJadwal.put(DatabaseHelper.WAKTU, waktu);
        contentValuesJadwal.put(DatabaseHelper.RUANGAN, ruangan);
        database.insert(DatabaseHelper.TABLE_JADWAL, null, contentValuesJadwal);
    }

    public int updateJadwal(long _id, String matkul, String hari, String waktu, String ruangan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MATKUL_JADWAL, matkul);
        contentValues.put(DatabaseHelper.HARI, hari);
        contentValues.put(DatabaseHelper.WAKTU, waktu);
        contentValues.put(DatabaseHelper.RUANGAN, ruangan);
        int i = database.update(DatabaseHelper.TABLE_JADWAL, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void deleteJadwal(long _id) {
        database.delete(DatabaseHelper.TABLE_JADWAL, DatabaseHelper._ID + "=" + _id, null);
    }

    public void insertCatatan(String keterangan, String note) {

        String datestamp = getDateStamp();

        ContentValues contentValues= new ContentValues();
        contentValues.put(DatabaseHelper.DATESTAMP, datestamp);
        contentValues.put(DatabaseHelper.KETERANGAN, keterangan);
        contentValues.put(DatabaseHelper.NOTE, note);
        database.insert(DatabaseHelper.TABLE_CATATAN, null, contentValues);
    }

    public int updateCatatan(long _id, String keterangan,  String note) {
        ContentValues contentValues = new ContentValues();

        String datestamp = getDateStamp();

        contentValues.put(DatabaseHelper.DATESTAMP, datestamp);
        contentValues.put(DatabaseHelper.KETERANGAN, keterangan);
        contentValues.put(DatabaseHelper.NOTE, note);
        int i = database.update(DatabaseHelper.TABLE_CATATAN, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void deleteCatatan(long _id) {
        database.delete(DatabaseHelper.TABLE_CATATAN, DatabaseHelper._ID + "=" + _id, null);
    }


    // method to get sum value of SKS column
    public int getSumSks() {
        int total = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.SKS + ") as Total FROM " + DatabaseHelper.TABLE_MATKUL, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    // method to get sum value of SKS column
    public int getSumBobot() {
        int total = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.BOBOT + ") as Total FROM " + DatabaseHelper.TABLE_MATKUL, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    // method to count column hari
    public int getCountJadwal() {
        int total = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(" + DatabaseHelper.HARI + ") as Total FROM " + DatabaseHelper.TABLE_JADWAL, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    // method to get index convertion multiply with sks value
    public double getBobot(String nilai, int sks) {
        double bobot;
        if (nilai.equals("A")) {
            bobot =   4.0;
        }
        else if (nilai.equals("B+")) {
            bobot = 3.5;
        }
        else if (nilai.equals("B")) {
            bobot = 3.0;
        }
        else if (nilai.equals("C+")) {
            bobot = 2.5;
        }
        else if (nilai.equals("C")) {
            bobot = 2.0;
        }
        else if (nilai.equals("D+")) {
            bobot = 1.5;
        }
        else if (nilai.equals("D")) {
            bobot = 1.0;
        }
        else if (nilai.equals("E")) {
            bobot = 0;
        } else {
            bobot = 0;
        }
        return bobot * sks;
    }

    // return datestamp with output : 24 Jul 2020
    public static String getDateStamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
        String dateStamp = formatter.format(timestamp);
        return dateStamp;
    }

}
