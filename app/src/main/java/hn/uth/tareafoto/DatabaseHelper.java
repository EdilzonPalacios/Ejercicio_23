package hn.uth.tareafoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ImageDatabase.db";
    private static final String TABLE_NAME = "images_table";
    public static final String COL_ID = "ID";
    public static final String COL_IMAGE = "IMAGE";
    public static final String COL_DESCRIPTION = "DESCRIPTION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_IMAGE + " BLOB, " +
                COL_DESCRIPTION + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(byte[] image, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMAGE, image);
        contentValues.put(COL_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public String getImageColumnName() {
        return COL_IMAGE;
    }

    // Método público para obtener el nombre de la columna COL_DESCRIPTION
    public String getDescriptionColumnName() {
        return COL_DESCRIPTION;
    }
}

