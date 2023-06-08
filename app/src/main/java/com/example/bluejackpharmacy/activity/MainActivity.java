package com.example.bluejackpharmacy.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.activity.main.AboutUsFragment;
import com.example.bluejackpharmacy.activity.main.HomeFragment;
import com.example.bluejackpharmacy.activity.main.TransactionFragment;
import com.example.bluejackpharmacy.databinding.ActivityMainBinding;
import com.example.bluejackpharmacy.object.Medicine;
import com.example.bluejackpharmacy.object.User;
import com.example.bluejackpharmacy.system.database.InitializeDB;
import com.example.bluejackpharmacy.system.database.MedicineDB;

public class MainActivity extends FragmentActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logoutButton.setOnClickListener(n -> {
            User.Current.logout();
            finish();
        });

        binding.navbar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_fragment:
                    binding.layoutName.setText("Home");
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new HomeFragment()).commit();
                    return true;
                case R.id.transcations_fragment:
                    binding.layoutName.setText("Transactions");
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new TransactionFragment()).commit();
                    return true;
                case R.id.about_us_fragment:
                    binding.layoutName.setText("About Us");
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new AboutUsFragment()).commit();
                    return true;
                default:
                    return false;
            }
        });
        binding.navbar.setSelectedItemId(R.id.home_fragment);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }
}