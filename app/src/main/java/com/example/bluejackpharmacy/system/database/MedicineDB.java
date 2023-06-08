package com.example.bluejackpharmacy.system.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bluejackpharmacy.object.Medicine;

import java.util.ArrayList;

public class MedicineDB {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MedicineDB(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
    }

    public void addMedicine(Medicine newMedicine) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newMedicine.getName());
        values.put("manufacturer", newMedicine.getManufacturer());
        values.put("price", String.valueOf(newMedicine.getPrice()));
        values.put("image", newMedicine.getImageURL());
        values.put("description", newMedicine.getDescription());

        db.insert("medicines", null, values);
        db.close();
    }

    public ArrayList<Medicine> getMedicine() {
        ArrayList<Medicine> itemList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM medicines", null);

        data.moveToFirst();
        while (!data.isAfterLast()){
            itemList.add(new Medicine(data.getInt(0), data.getString(1), data.getString(2), data.getDouble(3), data.getString(4), data.getString(5)));
            data.move(1);
        }

        db.close();
        return itemList;
    }

    public Medicine getMedicineById(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM medicines WHERE medicineId = ?",
                new String[]{String.valueOf(id)});
        data.moveToFirst();
        Medicine item = new Medicine(data.getInt(0),
                data.getString(1),
                data.getString(2),
                data.getDouble(3),
                data.getString(4),
                data.getString(5));

        db.close();
        return item;
    }

    public boolean isEmpty() {
        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM medicines LIMIT 1",
                null);
        int count = data.getCount();
        db.close();

        return count == 0;
    }
}
