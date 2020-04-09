package com.sanath.mileage.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Database adapter which will store all the user data locally
public class MileageDatabaseAdapter {

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "MILEAGE_DB";   //Database Name
    private static final int DATABASE_VERSION = 1; //Change Version to upgrade db

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public MileageDatabaseAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        //Create table
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL("CREATE TABLE MILEAGE(ID INTEGER NOT NULL PRIMARY KEY," +
                    "DATE TEXT NOT NULL, FUEL_FILLED REAL NOT NULL, DISTANCE_TRAVELLED REAL NOT NULL, MILEAGE REAL NOT NULL)");
        }

        //Upgrades database to new version
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");

            //Admin Table
            db.execSQL("DROP TABLE IF EXISTS MMR_USER");

            onCreate(db);
        }
    }

    //Opens the database for transactions
    public MileageDatabaseAdapter Open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //Close database after all transactions are done
    public void Close()
    {
        DBHelper.close();
    }


    //Insert new value to database
    public void InsertMileage(String DATE,float FUEL_FILLED, float DISTANCE_TRAVELLED, float MILEAGE)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("DATE", DATE);
        initialValues.put("FUEL_FILLED", FUEL_FILLED);
        initialValues.put("DISTANCE_TRAVELLED", DISTANCE_TRAVELLED);
        initialValues.put("MILEAGE", MILEAGE);

        db.insert("MILEAGE", null, initialValues);
    }


    //Delete individual row
    public boolean DeleteRow(int rowId)
    {
        return db.delete("MILEAGE",  "ID" +
                "=" + rowId, null) > 0;
    }

    //Truncate table (delete all record from selected table), if already exists
    public void truncateTable(String TableName)
    {
        db.execSQL("DELETE FROM "+TableName);
    }

    //Execute a custom query
    public Cursor getQueryResult(String query) throws SQLException
    {
        return db.rawQuery(query, null);
    }

    //Returns number of rows in table
    public long getCountOfEntries(String tableName) {
        long count = DatabaseUtils.queryNumEntries(db, tableName);
        return count;
    }
}
