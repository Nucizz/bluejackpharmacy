package com.example.bluejackpharmacy.system.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "BluejackPharmacyDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE users (" +
                        "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "email TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "phone TEXT NOT NULL," +
                        "isVerified TEXT NOT NULL)"
        );
        db.execSQL(
                "CREATE TABLE medicines (" +
                        "medicineId INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "manufacturer TEXT," +
                        "price FLOAT NOT NULL," +
                        "image TEXT," +
                        "description TEXT)"
        );
        db.execSQL(
                "CREATE TABLE transactions (" +
                        "transactionId INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "medicineId INTEGER NOT NULL," +
                        "userId INTEGER NOT NULL," +
                        "transactionDate TEXT NOT NULL," +
                        "quantity INTEGER NOT NULL," +
                        "FOREIGN KEY (userId) REFERENCES Users(userId)," +
                        "FOREIGN KEY (medicineId) REFERENCES Medicine(medicineId))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS medicines");
        db.execSQL("DROP TABLE IF EXISTS transactions");
    }
}
