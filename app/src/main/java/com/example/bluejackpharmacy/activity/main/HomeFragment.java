package com.example.bluejackpharmacy.activity.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.databinding.FragmentHomeBinding;
import com.example.bluejackpharmacy.object.Medicine;
import com.example.bluejackpharmacy.system.adapter.MedicineAdapter;
import com.example.bluejackpharmacy.system.database.InitializeDB;
import com.example.bluejackpharmacy.system.database.MedicineDB;
import com.example.bluejackpharmacy.system.feature.UIHelper;
import com.example.bluejackpharmacy.object.User;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private MedicineDB medicineDB;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        medicineDB = new MedicineDB(getContext());

        String name = User.Current.getInfo().getName();
        binding.greeting.setText(String.format("%s %s%s,", UIHelper.getTimeGreetings(), name.substring(0, 1).toUpperCase(), name.substring(1)));

        ArrayList<Medicine> medicineList = medicineDB.getMedicine();

        binding.medicineView.setAdapter(new MedicineAdapter(getContext(), medicineList));
        binding.medicineView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        return view;
    }
}