package com.example.sawt_al_amal.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnect extends SQLiteOpenHelper {
    public DbConnect(Context context) {
        super(context, DbStructure.dbName, null, DbStructure.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbStructure.User.SQL_CREATE);
        db.execSQL(DbStructure.Geste.SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbStructure.User.SQL_DROP);
        db.execSQL(DbStructure.Geste.SQL_DROP);
        onCreate(db);
    }
}
