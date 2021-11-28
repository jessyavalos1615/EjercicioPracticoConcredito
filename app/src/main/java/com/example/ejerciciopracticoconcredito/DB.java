package com.example.ejerciciopracticoconcredito;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper  {
    public static final String DATABASE_NAME = "prospect_db.sqlite";
    public static final String TABLE_NAME = "prospects";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_SECONDLASTNAME = "second_last_name";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_SUBURB = "suburb";
    public static final String COLUMN_ZIP = "zip";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_RFC = "rfc";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_OBSERVACION = "observacion";

    public DB(Context context)
    {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME + "\n" +
                        "(id integer primary key autoincrement, name text," +
                        "last_name text,\n" +
                        "second_last_name text,\n" +
                        "street text,\n" +
                        "number text,\n" +
                        "suburb text,\n" +
                        "zip text,\n" +
                        "phone text,\n" +
                        "rfc text,\n" +
                        "status text, \n" +
                        "observacion text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean addProspect(Prospecto prospecto){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contantValues = new ContentValues();
            contantValues.put(COLUMN_NAME,prospecto.getName());
            contantValues.put(COLUMN_LASTNAME, prospecto.getLast_name());
            contantValues.put(COLUMN_SECONDLASTNAME,prospecto.getSecond_last_name());
            contantValues.put(COLUMN_STREET,prospecto.getStreet());
            contantValues.put(COLUMN_NUMBER, prospecto.getNumber());
            contantValues.put(COLUMN_SUBURB,prospecto.getSuburb());
            contantValues.put(COLUMN_ZIP,prospecto.getZip());
            contantValues.put(COLUMN_PHONE,prospecto.getPhone());
            contantValues.put(COLUMN_RFC,prospecto.getRfc());
            contantValues.put(COLUMN_STATUS,prospecto.getStatus());
            contantValues.put(COLUMN_OBSERVACION, prospecto.getObservacion());

            db.insert(TABLE_NAME, null, contantValues);
            db.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProspect(Integer id, String status, String observacion) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put(COLUMN_STATUS,status);
        contantValues.put(COLUMN_OBSERVACION,observacion);
        db.update(TABLE_NAME, contantValues, "id = ?", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }


    @SuppressLint("Range")
    public Prospecto getProspect(int id){
        Prospecto prospecto = new Prospecto();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where id = " + id + "", null);
        if (cursor.moveToFirst()) {
            do {
                prospecto.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                prospecto.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                prospecto.setLast_name(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)));
                prospecto.setSecond_last_name(cursor.getString(cursor.getColumnIndex(COLUMN_SECONDLASTNAME)));
                prospecto.setStreet(cursor.getString(cursor.getColumnIndex(COLUMN_STREET)));
                prospecto.setNumber(cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER)));
                prospecto.setSuburb(cursor.getString(cursor.getColumnIndex(COLUMN_SUBURB)));
                prospecto.setZip(cursor.getString(cursor.getColumnIndex(COLUMN_ZIP)));
                prospecto.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                prospecto.setRfc(cursor.getString(cursor.getColumnIndex(COLUMN_RFC)));
                prospecto.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                prospecto.setObservacion(cursor.getString(cursor.getColumnIndex(COLUMN_OBSERVACION)));
            } while (cursor.moveToNext());
        }
        return prospecto;
    }

    @SuppressLint("Range")
    public ArrayList<Prospecto> getAllProspects(){
        ArrayList<Prospecto> arraylist= new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("Select * from " + TABLE_NAME,null);

        if (cursor.moveToFirst()) {
            do {
                Prospecto prospecto = new Prospecto();
                prospecto.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                prospecto.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                prospecto.setLast_name(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)));
                prospecto.setSecond_last_name(cursor.getString(cursor.getColumnIndex(COLUMN_SECONDLASTNAME)));
                prospecto.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                arraylist.add(prospecto);
            } while (cursor.moveToNext());
        }
        return arraylist;
    }
}
