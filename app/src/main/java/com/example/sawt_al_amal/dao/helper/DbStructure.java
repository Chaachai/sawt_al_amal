package com.example.sawt_al_amal.dao.helper;

import android.provider.BaseColumns;
//le fichier contrat
//HALI Hossam
public final class DbStructure {

    public static final String dbName = "sawt_al_amal.db";
    public static final int DB_VERSION = 9;
// la table user
    public static abstract class User implements BaseColumns {
        public static final String T_NAME = "user";
        public static final String C_ID = "id";
        public static final String C_LASTNAME = "lastName";
        public static final String C_FIRSTNAME = "firstName";
        public static final String C_PASSWORD = "password";
        public static final String C_USERNAME = "username";
        public static final String C_EMAIL = "email";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + C_LASTNAME + " TEXT, "
                + C_FIRSTNAME + " TEXT, "
                + C_PASSWORD + " TEXT, "
                + C_USERNAME + " TEXT, "
                + C_EMAIL + " TEXT )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }
// la table geste
    public static abstract class Geste implements BaseColumns {
        public static final String T_NAME = "geste";
        public static final String C_ID = "id";
        public static final String C_GIF = "gif";
        public static final String C_IMAGE = "image";
        public static final String C_TEXT = "text";
        public static final String C_ID_COURS = "id_cours";
        public static final String C_ID_CATEGORY = "id_category";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + C_GIF + " BLOB, "
                + C_IMAGE + " BLOB, "
                + C_TEXT + " TEXT, "
                + C_ID_COURS + " INTEGER, "
                + C_ID_CATEGORY + " INTEGER REFERENCES " + Category.T_NAME + "( " + Category.C_ID + " ) )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }
//la table cours
    public static abstract class Cours implements BaseColumns {
        public static final String T_NAME = "cours";
        public static final String C_ID = "id";
        public static final String C_NOM = "nom";
        public static final String C_ID_NIVEAU = "id_niveau";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + C_NOM + " TEXT, "
                + C_ID_NIVEAU + " INTEGER REFERENCES " + Niveau.T_NAME + "( " + Niveau.C_ID + " ) )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }
//la table Niveau
    public static abstract class Niveau implements BaseColumns {
        public static final String T_NAME = "niveau";
        public static final String C_ID = "id";
        public static final String C_NOM = "nom";
        public static final String C_DESCRIPTION = "description";
        public static final String C_ICON = "icon";
        public static final String C_REQPOINTS = "reqpoints";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + C_NOM + " TEXT, "
                + C_DESCRIPTION + " TEXT, "
                + C_ICON + " BLOB, "
                + C_REQPOINTS + " INTEGER )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }

    public static abstract class Category implements BaseColumns {
        public static final String T_NAME = "category";
        public static final String C_ID = "id";
        public static final String C_LIBELLE = "libelle";

        public static final String SQL_CREATE = "create table " + T_NAME + "("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + C_LIBELLE + " TEXT )";

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + T_NAME;
    }



}
