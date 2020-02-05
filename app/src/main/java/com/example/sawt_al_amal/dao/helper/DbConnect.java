package com.example.sawt_al_amal.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbConnect extends SQLiteAssetHelper {

    public DbConnect(Context context) {
        super(context, DbStructure.dbName, null, DbStructure.DB_VERSION);
    }



   @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbStructure.User.SQL_DROP);
        db.execSQL(DbStructure.Geste.SQL_DROP);
        db.execSQL(DbStructure.Cours.SQL_DROP);
        db.execSQL(DbStructure.Niveau.SQL_DROP);
        db.execSQL(DbStructure.Category.SQL_DROP);
        onCreate(db);
    }
}
