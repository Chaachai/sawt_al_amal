package com.example.sawt_al_amal.dao.helper;

import android.provider.BaseColumns;

public final class DbStructure {

    public static final String dbName = "sawt_al_amal.db";
    public static final int DB_VERSION = 1;

    public static abstract class User implements BaseColumns {

        public static final String T_NAME = "user";
        public static final String C_LASTNAME = "lastName";
        public static final String C_FIRSTNAME = "firstName";
        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_LASTNAME + " TEXT, "
                + C_FIRSTNAME + " TEXT )";
        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }
}
