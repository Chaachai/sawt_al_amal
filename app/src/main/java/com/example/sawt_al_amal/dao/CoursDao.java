package com.example.sawt_al_amal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.bean.Niveau;
import com.example.sawt_al_amal.dao.helper.AbstractDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;

//CHAACHAI Youssef

public class CoursDao extends AbstractDao<Cours> {
    //creer le cours
    @Override
    public long create(Cours cours) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Cours.C_NOM, cours.getNom());
        contentValues.put(DbStructure.Cours.C_ID_NIVEAU, cours.getNiveau().getId());
        return getDb().insert(DbStructure.Cours.T_NAME, null, contentValues);
    }
//modifier le cours
    @Override
    public long edit(Cours cours) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Cours.C_NOM, cours.getNom());
        contentValues.put(DbStructure.Cours.C_ID_NIVEAU, cours.getNiveau().getId());
        return db.update(DbStructure.Cours.T_NAME, contentValues, DbStructure.Cours.C_ID + " = '" + cours.getId() + "'", null);
    }
//supprimer le cours
    public long remove(Cours cours) {
        open();
        return db.delete(DbStructure.Cours.T_NAME, DbStructure.Cours.C_ID + "=" + cours.getId(), null);
    }
//recuperer le cours
    protected Cours transformeCursorToBean(Cursor cursor) {
        Cours cours = new Cours();
        cours.setId(cursor.getInt(cursor.getColumnIndex(DbStructure.Cours.C_ID)));
        cours.setNom(cursor.getString(cursor.getColumnIndex(DbStructure.Cours.C_NOM)));
        cours.setNiveau(new Niveau(cursor.getInt(cursor.getColumnIndex(DbStructure.Cours.C_ID_NIVEAU))));
        return cours;
    }

    public CoursDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Cours.C_ID,
                DbStructure.Cours.C_NOM,
                DbStructure.Cours.C_ID_NIVEAU
        };
        tableName = DbStructure.Cours.T_NAME;
        idName = DbStructure.Cours.C_ID;
    }
}
