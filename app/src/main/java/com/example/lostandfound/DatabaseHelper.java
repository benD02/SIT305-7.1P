package com.example.lostandfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LostFoundDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "adverts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_STATUS = "status";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_LOCATION + " TEXT,"
            + COLUMN_STATUS + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addAdvert(String name, String phone, String description, String date, String location, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_STATUS, status);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<AdvertItem> getItemsByStatus(String status) {
        List<AdvertItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_PHONE,
                COLUMN_DESCRIPTION,
                COLUMN_DATE,
                COLUMN_LOCATION,
                COLUMN_STATUS
        };
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = { status };

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
            int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);
            int statusIndex = cursor.getColumnIndex(COLUMN_STATUS);

            // Check if any of the column indices are invalid
            if (idIndex != -1 && nameIndex != -1 && phoneIndex != -1 && descriptionIndex != -1 && dateIndex != -1 && locationIndex != -1 && statusIndex != -1) {
                do {
                    AdvertItem item = new AdvertItem(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(phoneIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getString(dateIndex),
                            cursor.getString(locationIndex),
                            cursor.getString(statusIndex)
                    );
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return itemList;
    }

    // Utility method to get all lost items
    public List<AdvertItem> getLostItems() {
        return getItemsByStatus("Lost");
    }

    // Utility method to get all found items
    public List<AdvertItem> getFoundItems() {
        return getItemsByStatus("Found");
    }
}
