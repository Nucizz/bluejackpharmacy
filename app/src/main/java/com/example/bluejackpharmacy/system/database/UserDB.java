package com.example.bluejackpharmacy.system.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bluejackpharmacy.object.User;

public class UserDB {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserDB(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
    }

    public int checkCredentials(String email, String hashedPassword) {
        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?",
                new String[]{email, hashedPassword});
        if(data.getCount() != 0) {
            data.moveToFirst();
            int userId = data.getInt(0);
            db.close();
            return userId;
        } else {
            db.close();
            return 0;
        }
    }

    public boolean checkDuplication(String name, String email, String phone) {
        db = dbHelper.getReadableDatabase();
        Cursor data1 = db.rawQuery(
                "SELECT * FROM users WHERE name = ?",
                new String[]{name}
        );
        Cursor data2 = db.rawQuery(
                "SELECT * FROM users WHERE email = ?",
                new String[]{email}
        );
        Cursor data3 = db.rawQuery(
                "SELECT * FROM users WHERE phone = ?",
                new String[]{phone}
        );

        if(data1.getCount() + data2.getCount() + data3.getCount() != 0) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public User getUser(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery(
                "SELECT * FROM users WHERE userId = ?",
                new String[]{String.valueOf(id)}
        );
        data.moveToFirst();
        User current = new User(data.getInt(0),
                data.getString(1),
                data.getString(2),
                data.getString(4),
                User.Handler.textToBool(data.getString(5)));

        db.close();
        return current;
    }

    public void addUser(User newUser, String hashedPassword) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newUser.getName());
        values.put("email", newUser.getEmail());
        values.put("password", hashedPassword);
        values.put("phone", newUser.getPhone());
        values.put("isVerified", User.Handler.boolToText(newUser.isVerified()));

        db.insert("users", null, values);
        db.close();
    }

    public void verifyUser(int id) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isVerified", "TRUE");

        db.update("users", values, "userId = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
