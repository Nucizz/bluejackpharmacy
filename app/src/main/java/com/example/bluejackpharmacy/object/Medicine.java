package com.example.bluejackpharmacy.object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Medicine implements Parcelable {

    private int id;
    private String name;
    private String manufacturer;
    private double price;
    private String imageURL;
    private String description;

    public Medicine(int id, String name, String manufacturer, double price, String imageURL, String description) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.imageURL = imageURL;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(manufacturer);
        dest.writeDouble(price);
        dest.writeString(imageURL);
        dest.writeString(description);
    }

    protected Medicine(Parcel in) {
        id = in.readInt();
        name = in.readString();
        manufacturer = in.readString();
        price = in.readDouble();
        imageURL = in.readString();
        description = in.readString();
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };
}
