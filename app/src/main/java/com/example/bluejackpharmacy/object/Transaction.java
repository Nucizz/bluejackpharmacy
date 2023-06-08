package com.example.bluejackpharmacy.object;

public class Transaction {

    private int medicineId;
    private int userId;

    private int id;
    private String date;
    private int quantity;

    public Transaction( int id, int medicineId, int userId, String date, int quantity) {
        this.id = id;
        this.medicineId = medicineId;
        this.userId = userId;
        this.date = date;
        this.quantity = quantity;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
