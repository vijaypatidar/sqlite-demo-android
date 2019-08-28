package com.example.sqlitedemo.features;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static Database database = null;

    private Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static Database getInstance(Context context) {
        if (database == null)
            database = new Database(context, "my_db", null, 1);
        return database;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table product(id integer primary key autoincrement ," +
                "name varchar(20),quantity int,price float)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
