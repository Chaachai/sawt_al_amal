package com.example.sawt_al_amal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sawt_al_amal.bean.User;
import com.example.sawt_al_amal.dao.helper.AbstractDao;
import com.example.sawt_al_amal.dao.helper.DbStructure;

//AZALMAD Ilham
public class UserDao extends AbstractDao<User> {
    //creer un utilisateur
    @Override
    public long create(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_LASTNAME, user.getLastName());
        contentValues.put(DbStructure.User.C_FIRSTNAME, user.getFirstName());
        contentValues.put(DbStructure.User.C_PASSWORD, user.getPassword());
        contentValues.put(DbStructure.User.C_USERNAME, user.getUsername());
        contentValues.put(DbStructure.User.C_EMAIL, user.getEmail());
        return getDb().insert(DbStructure.User.T_NAME, null, contentValues);
    }
//Modifier les données d'utlisateur
    @Override
    public long edit(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_LASTNAME, user.getLastName());
        contentValues.put(DbStructure.User.C_FIRSTNAME, user.getFirstName());
        contentValues.put(DbStructure.User.C_PASSWORD, user.getPassword());
        contentValues.put(DbStructure.User.C_USERNAME, user.getUsername());
        contentValues.put(DbStructure.User.C_EMAIL, user.getEmail());
        return db.update(DbStructure.User.T_NAME, contentValues, DbStructure.User.C_ID + " = '" + user.getId() + "'", null);
    }
//supprimer un utilisateur
    public long remove(User user) {
        open();
        return db.delete(DbStructure.User.T_NAME, DbStructure.User.C_ID + "=" + user.getId(), null);
    }
//recuperer l'utilisateur
    protected User transformeCursorToBean(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setFirstName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        user.setUsername(cursor.getString(4));
        user.setEmail(cursor.getString(5));
        return user;
    }

    public UserDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.User.C_ID,
                DbStructure.User.C_LASTNAME,
                DbStructure.User.C_FIRSTNAME,
                DbStructure.User.C_PASSWORD,
                DbStructure.User.C_USERNAME,
                DbStructure.User.C_EMAIL
        };
        tableName = DbStructure.User.T_NAME;
        idName = DbStructure.User.C_ID;
    }

    public boolean check(String col,String val) {
        open();
        Cursor cursor = db.rawQuery("select * from "+tableName+" where " + col + " = '" + val + "'",null);
        int cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    //recuperer tout les lignes
    public Cursor all() {
        open();
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        return cursor;
    }
    //verifier le login
    public Cursor checklogin(String val1,String val2) {
        open();
        Cursor cursor = db.rawQuery("select * from "+tableName+" where "
                +DbStructure.User.C_USERNAME + " = '" + val1 + "' AND " + DbStructure.User.C_PASSWORD + " = '" + val2 + "'",null);
        return cursor;
    }


}
