package com.example.sawt_al_amal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sawt_al_amal.bean.Niveau;
import com.example.sawt_al_amal.dao.helper.AbstractDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;

public class NiveauDao extends AbstractDao<Niveau> {
    @Override
    public long create(Niveau niveau) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Niveau.C_NOM, niveau.getNom());
        contentValues.put(DbStructure.Niveau.C_DESCRIPTION, niveau.getDescription());
        contentValues.put(DbStructure.Niveau.C_ICON, niveau.getIcon());
        contentValues.put(DbStructure.Niveau.C_REQPOINTS, niveau.getReqPoints());
        return getDb().insert(DbStructure.Niveau.T_NAME, null, contentValues);
    }

    @Override
    public long edit(Niveau niveau) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Niveau.C_NOM, niveau.getNom());
        contentValues.put(DbStructure.Niveau.C_DESCRIPTION, niveau.getDescription());
        contentValues.put(DbStructure.Niveau.C_ICON, niveau.getIcon());
        contentValues.put(DbStructure.Niveau.C_REQPOINTS, niveau.getReqPoints());
        return db.update(DbStructure.Niveau.T_NAME, contentValues, DbStructure.Niveau.C_ID + " = '" + niveau.getId() + "'", null);
    }

    public long remove(Niveau niveau) {
        open();
        return db.delete(DbStructure.Niveau.T_NAME, DbStructure.Niveau.C_ID + "=" + niveau.getId(), null);
    }

    protected Niveau transformeCursorToBean(Cursor cursor) {
        Niveau niveau = new Niveau();
        niveau.setId(cursor.getInt(0));
        niveau.setNom(cursor.getString(1));
        niveau.setDescription(cursor.getString(2));
        niveau.setIcon(cursor.getBlob(3));
        niveau.setReqPoints(cursor.getInt(4));

        return niveau;
    }

    public NiveauDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Niveau.C_ID,
                DbStructure.Niveau.C_NOM,
                DbStructure.Niveau.C_DESCRIPTION,
                DbStructure.Niveau.C_ICON,
                DbStructure.Niveau.C_REQPOINTS
        };
        tableName = DbStructure.Niveau.T_NAME;
        idName = DbStructure.Niveau.C_ID;
    }
}
