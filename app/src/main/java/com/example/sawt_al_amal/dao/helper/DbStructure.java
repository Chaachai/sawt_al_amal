package com.example.sawt_al_amal.dao.helper;

import android.provider.BaseColumns;

public final class DbStructure {

    public static final String dbName = "sawt_al_amal.db";
    public static final int DB_VERSION = 1;

    public static abstract class User implements BaseColumns {
        public static final String T_NAME = "user";
        public static final String C_LASTNAME = "lastName";
        public static final String C_FIRSTNAME = "firstName";
        public static final String C_PASSWORD = "password";
        public static final String C_USERNAME = "username";
        public static final String C_EMAIL = "email";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + C_LASTNAME + " TEXT, "
                + C_FIRSTNAME + " TEXT, "
                + C_PASSWORD + " TEXT, "
                + C_USERNAME + " TEXT, "
                + C_EMAIL + " TEXT )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

    public static abstract class Geste implements BaseColumns {
        public static final String T_NAME = "geste";
        public static final String C_GIF = "gif";
        public static final String C_IMAGE = "image";
        public static final String C_TEXT = "text";
        public static final String C_ID_COURS = "id_cours";
        public static final String C_ID_CATEGORY = "id_category";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + C_GIF + " BLOB, "
                + C_IMAGE + " BLOB, "
                + C_TEXT + " TEXT, "
                + C_ID_COURS + " TEXT, "
                + C_ID_CATEGORY + " TEXT )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }


}
