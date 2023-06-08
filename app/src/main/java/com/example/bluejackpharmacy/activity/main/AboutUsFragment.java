package com.example.bluejackpharmacy.activity.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.databinding.FragmentAboutUsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding binding;
    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            binding.googleMaps.onResume();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            binding.googleMaps.onDestroy();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(getLayoutInflater());

        try {
            binding.googleMaps.onCreate(savedInstanceState);
            binding.googleMaps.getMapAsync(googleMap -> {
                LatLng position = new LatLng(-6.20201, 106.78113);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                googleMap.addMarker(new MarkerOptions().position(position).title("Bluejack Pharmacy"));
            });
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "Map service unavailable!", Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();
    }
}