package com.example.sawt_al_amal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.sawt_al_amal.bean.Category;
import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.dao.helper.AbstractDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;

public class GesteDao extends AbstractDao<Geste> {

    @Override
    public long create(Geste geste) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Geste.C_GIF, geste.getGif());
        contentValues.put(DbStructure.Geste.C_IMAGE, geste.getImage());
        contentValues.put(DbStructure.Geste.C_TEXT, geste.getText());
        contentValues.put(DbStructure.Geste.C_ID_COURS, geste.getCours().getId());
        contentValues.put(DbStructure.Geste.C_ID_COURS, geste.getCours().getId());
        return getDb().insert(DbStructure.Geste.T_NAME, null, contentValues);
    }

    @Override
    public long edit(Geste geste) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Geste.C_GIF, geste.getGif());
        contentValues.put(DbStructure.Geste.C_IMAGE, geste.getImage());
        contentValues.put(DbStructure.Geste.C_TEXT, geste.getText());
        contentValues.put(DbStructure.Geste.C_ID_COURS, geste.getCours().getId());
        contentValues.put(DbStructure.Geste.C_ID_COURS, geste.getCours().getId());
        return db
                .update(DbStructure.Geste.T_NAME, contentValues, DbStructure.Geste._ID + " = '" + geste.getId() + "'",
                        null);
    }

    public long remove(Geste geste) {
        open();
        return db.delete(DbStructure.Geste.T_NAME, DbStructure.Geste._ID + "=" + geste.getId(), null);
    }

    protected Geste transformeCursorToBean(Cursor cursor) {
        Geste geste = new Geste();
        geste.setId(cursor.getInt(0));
        geste.setGif(cursor.getBlob(1));
        geste.setImage(cursor.getBlob(2));
        geste.setText(cursor.getString(3));
        geste.setCours(new Cours(cursor.getInt(4)));
        geste.setCategory(new Category(cursor.getInt(5)));
        return geste;
    }

    public GesteDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Geste.C_GIF,
                DbStructure.Geste.C_IMAGE,
                DbStructure.Geste.C_TEXT,
                DbStructure.Geste.C_ID_COURS,
                DbStructure.Geste.C_ID_CATEGORY
        };
        tableName = DbStructure.Geste.T_NAME;
        idName = DbStructure.Geste._ID;
    }
}
