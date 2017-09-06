package com.pushandmotion.pamservices.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.provider.BaseColumns;

/**
 * Created by heart on 8/31/2017 AD.
 */

public class PAMLocalDataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PAM.db";

    private static PAMLocalDataBase instance= null;

    public static PAMLocalDataBase getInstance(Context context){
        if(instance == null){
            instance = new PAMLocalDataBase(context);
        }
        return instance;
    }

    public PAMLocalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_DUPFH_TABLE =
                "CREATE TABLE updfh (id INTEGER PRIMARY KEY, updfh TEXT)";

        final String SQL_CREATE_MTC_ID_TABLE =
                "CREATE TABLE mtc (id INTEGER PRIMARY KEY, mtc_id TEXT, sid TEXT)";

        db.execSQL(SQL_CREATE_DUPFH_TABLE);
        db.execSQL(SQL_CREATE_MTC_ID_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveMtcID(String mtc_id,String sid){
        String old_mtc_id = getMtcID();

        if(old_mtc_id == null || !mtc_id.equalsIgnoreCase(old_mtc_id) ) {
            ContentValues values = new ContentValues();
            values.put(UPDFHEntry.COLUMN_NAME_MTC_ID, mtc_id);
            values.put(UPDFHEntry.COLUMN_NAME_SID, sid);
            long newRowId = this.getWritableDatabase().insert(UPDFHEntry.TABLE_MTC, null, values);
        }

    }
    public String getMtcID(){
        String mtc_id = null;

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UPDFHEntry.COLUMN_NAME_MTC_ID
        };

        String sortOrder =
                UPDFHEntry.ID + " DESC";

        Cursor cursor = db.query(
                UPDFHEntry.TABLE_MTC,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()){
            mtc_id = cursor.getString(
                    cursor.getColumnIndexOrThrow(UPDFHEntry.COLUMN_NAME_MTC_ID));
        }

        cursor.close();

        return mtc_id;
    }
    public String getSID(){
        String sid = null;

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UPDFHEntry.COLUMN_NAME_SID
        };

        String sortOrder =
                UPDFHEntry.ID + " DESC";

        Cursor cursor = db.query(
                UPDFHEntry.TABLE_MTC,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()){
            sid = cursor.getString(
                    cursor.getColumnIndexOrThrow(UPDFHEntry.COLUMN_NAME_SID));
        }

        cursor.close();

        return sid;
    }

    public void saveUPDFH(String updfh){
        ContentValues values = new ContentValues();
        values.put(UPDFHEntry.COLUMN_NAME_UPDFH, updfh);
        long newRowId = this.getWritableDatabase().insert(UPDFHEntry.TABLE_UPDFH, null, values);
    }

    public String getUPDFH(){
        String updfh = null;

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UPDFHEntry.COLUMN_NAME_UPDFH
        };

        String sortOrder =
                UPDFHEntry.ID + " DESC";

        Cursor cursor = db.query(
                UPDFHEntry.TABLE_UPDFH,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()){
            updfh = cursor.getString(
                    cursor.getColumnIndexOrThrow(UPDFHEntry.COLUMN_NAME_UPDFH));
        }

        cursor.close();

        return updfh;
    }

    private static class UPDFHEntry implements BaseColumns {
        public static final String TABLE_UPDFH = "updfh";
        public static final String TABLE_MTC = "mtc";

        public static final String ID = "id";
        public static final String COLUMN_NAME_UPDFH = "updfh";

        public static final String COLUMN_NAME_MTC_ID = "mtc_id";
        public static final String COLUMN_NAME_SID = "sid";
    }


}


