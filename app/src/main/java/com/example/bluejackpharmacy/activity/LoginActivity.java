package com.example.bluejackpharmacy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.databinding.ActivityLoginBinding;
import com.example.bluejackpharmacy.system.database.InitializeDB;
import com.example.bluejackpharmacy.system.database.MedicineDB;
import com.example.bluejackpharmacy.system.database.UserDB;
import com.example.bluejackpharmacy.system.feature.UIHelper;
import com.example.bluejackpharmacy.object.User;

public class LoginActivity extends Activity {

    private UserDB userDB;
    private MedicineDB medicineDB;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDB = new UserDB(this);
        medicineDB = new MedicineDB(this);

        if(medicineDB.isEmpty()) {
            InitializeDB _initDB = new InitializeDB(this);
            _initDB.parseMedicine();
        }

        UIHelper.setColorAccent(this, binding.title);
        UIHelper.setColorAccent(this, binding.toRegister);

        binding.email.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                if(UIHelper.liveValEmail(binding.email.getText().toString())){
                    binding.email.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.acc_green));
                    binding.email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    binding.email.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                    binding.email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                }
            }
        });

        binding.password.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                if(UIHelper.liveValPassword(binding.password.getText().toString())){
                    binding.password.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.acc_green));
                    binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    binding.password.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                    binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                }
            }
        });

        binding.login.setOnClickListener(n -> {
            if(User.Handler.validateEmail(this, binding.email.getText().toString())
            && User.Handler.validatePassword(this, binding.password.getText().toString())) {
                int userId = userDB.checkCredentials(binding.email.getText().toString(), User.Handler.hashPassword(binding.password.getText().toString()));
                if(userId != 0) {
                    User.Current.login(userDB.getUser(userId));
                    Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();

                    if(!User.Current.getInfo().isVerified()) {
                        startActivity(new Intent(this, OTPActivity.class));
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                } else {
                    Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.toRegister.setOnClickListener(n -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }
}