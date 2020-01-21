package com.example.sawt_al_amal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sawt_al_amal.bean.User;
import com.example.sawt_al_amal.dao.helper.AbstractDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;

public class UserDao extends AbstractDao<User> {
    @Override
    public long create(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_LASTNAME, user.getLastName());
        contentValues.put(DbStructure.User.C_FIRSTNAME, user.getFirstName());
        return getDb().insert(DbStructure.User.T_NAME, null, contentValues);
    }

    @Override
    public long edit(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_LASTNAME, user.getLastName());
        contentValues.put(DbStructure.User.C_FIRSTNAME, user.getFirstName());
        return db.update(DbStructure.User.T_NAME, contentValues, DbStructure.User._ID + " = '" + user.getId() + "'", null);
    }

    public long remove(User user) {
        open();
        return db.delete(DbStructure.User.T_NAME, DbStructure.User._ID + "=" + user.getId(), null);
    }

    protected User transformeCursorToBean(Cursor cursor) {
        return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    public UserDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.User.C_LASTNAME,
                DbStructure.User.C_FIRSTNAME,
        };
        tableName = DbStructure.User.T_NAME;
        idName = DbStructure.User._ID;
    }
}
