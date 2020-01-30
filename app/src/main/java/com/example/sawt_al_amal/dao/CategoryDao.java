package com.example.sawt_al_amal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sawt_al_amal.bean.Category;
import com.example.sawt_al_amal.dao.helper.AbstractDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;

public class CategoryDao extends AbstractDao<Category> {
    @Override
    public long create(Category category) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Category.C_LIBELLE, category.getLibelle());
        return getDb().insert(DbStructure.Category.T_NAME, null, contentValues);
    }

    @Override
    public long edit(Category category) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Category.C_ID, category.getId());
        contentValues.put(DbStructure.Category.C_LIBELLE, category.getLibelle());
        return db.update(DbStructure.Category.T_NAME, contentValues, DbStructure.Category.C_ID + " = '" + category.getId() + "'", null);
    }

    public long remove(Category category) {
        open();
        return db.delete(DbStructure.Category.T_NAME, DbStructure.Category.C_ID + "=" + category.getId(), null);
    }

    protected Category transformeCursorToBean(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getInt(0));
        category.setLibelle(cursor.getString(1));

        return category;
    }

    public CategoryDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Category.C_ID,
                DbStructure.Category.C_LIBELLE
        };
        tableName = DbStructure.Category.T_NAME;
        idName = DbStructure.Category.C_ID;
    }
}
