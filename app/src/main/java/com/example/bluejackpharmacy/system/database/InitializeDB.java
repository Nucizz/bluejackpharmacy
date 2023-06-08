package com.example.bluejackpharmacy.system.database;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluejackpharmacy.object.Medicine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InitializeDB {

    private RequestQueue requestQueue;
    private MedicineDB medicineDB;

    public InitializeDB(Context ctx) {
        requestQueue = Volley.newRequestQueue(ctx);
        medicineDB = new MedicineDB(ctx);
    }

    public void parseMedicine() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://mocki.io/v1/ae13b04b-13df-4023-88a5-7346d5d3c7eb",
                null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("medicines");

                        System.out.println(array.length());

                        for(int i=0;i<array.length();i++) {
                            JSONObject object = array.getJSONObject(i);

                            medicineDB.addMedicine(new Medicine(0,
                                    object.getString("name"),
                                    object.getString("manufacturer"),
                                    object.getDouble("price"),
                                    object.getString("image"),
                                    object.getString("description")));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(request);
    }

}
