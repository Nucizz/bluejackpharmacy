package com.example.bluejackpharmacy.system.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bluejackpharmacy.object.Medicine;
import com.example.bluejackpharmacy.object.Transaction;
import com.example.bluejackpharmacy.system.adapter.TransactionAdapter;

import java.util.ArrayList;

public class TransactionDB {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public TransactionDB(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
    }

    public void addTransaction(Transaction newTransaction) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("medicineId", newTransaction.getMedicineId());
        values.put("userId", newTransaction.getUserId());
        values.put("transactionDate", newTransaction.getDate());
        values.put("quantity", newTransaction.getQuantity());

        db.insert("transactions", null, values);
        db.close();
    }

    public ArrayList<Transaction> getTransaction(int userId) {
        ArrayList<Transaction> itemList = new ArrayList<>();

        db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM transactions WHERE userId = ?",
                new String[]{String.valueOf(userId)});

        data.moveToFirst();
        while (!data.isAfterLast()){
            itemList.add(new Transaction(data.getInt(0),
                    data.getInt(1),
                    data.getInt(2),
                    data.getString(3),
                    data.getInt(4)));
            data.move(1);
        }

        db.close();
        return itemList;
    }

    public void updateTransaction(int id, int qty) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", qty);

        db.update("transactions", values, "transactionId = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTransaction(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete("transactions", "transactionId = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
