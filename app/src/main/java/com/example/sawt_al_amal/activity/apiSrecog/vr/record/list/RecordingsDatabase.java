package com.example.sawt_al_amal.activity.apiSrecog.vr.record.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
///le ficher Helper
//FEKRANE Zakaria
public class RecordingsDatabase {

    public class DbHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_IMAGE + " BLOB, "
                        + COLUMN_TIMESTAMP + " LONG);";

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
        }
    }

    private static final String TAG = "recordings_db";

    public static final String DATABASE_NAME = "recordings_db";

    public static final String TABLE_NAME = "recordings";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String COLUMN_IMAGE = "image";

    private static DbHelper dbHelper;
/// recuperer l'image
    public static Bitmap findImage(String sound) {
        Log.d(TAG, "saveRecording " + 2);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Log.d(TAG, "saveRecording " + 2);
        byte[] column1 = null;
        Cursor c = db.rawQuery("SELECT image FROM  recordings WHERE name LIKE ? ", new String[]{sound + ""});
        if (c.moveToFirst()) {
            do {
                int id1 = c.getColumnIndex("image");
                column1 = c.getBlob(id1);

                if (column1 == null) {
                    Log.d("OUCH", "Row   is null");
                }

                Log.d(TAG, "saveRecording " + column1.length);

            } while (c.moveToNext());

        }
        return getImage(column1);

    }
///covertir l'image de type byte a bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public RecordingsDatabase(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    public boolean deleteRecording(Long timestamp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = COLUMN_TIMESTAMP + " LIKE ?";
        String[] selectionArgs = {String.valueOf(timestamp)};

        return db.delete(TABLE_NAME, selection, selectionArgs) > 0;
    }
//recuperer la list des audio
    public List<Recording> getAllRecordings() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME, COLUMN_TIMESTAMP
        };

        String sortOrder = COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, sortOrder);

        List<Recording> recordings = new LinkedList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                Long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                recordings.add(new Recording(name, timestamp));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return recordings;
    }
//enregistrer  audio
    public void saveRecording(Recording recording) {
        Log.d(TAG, "saveRecording " + recording.getName() + " timestamp " + recording.getTimestamp());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, recording.getName());
        values.put(COLUMN_TIMESTAMP, recording.getTimestamp());
        values.put(COLUMN_IMAGE, recording.getImage());
        db.insert(TABLE_NAME, null, values);
    }
}
