package com.example.bluejackpharmacy.activity;

import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.databinding.ActivityRegisterBinding;
import com.example.bluejackpharmacy.system.database.UserDB;
import com.example.bluejackpharmacy.system.feature.UIHelper;
import com.example.bluejackpharmacy.object.User;

public class RegisterActivity extends Activity {

    private UserDB userDB;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDB = new UserDB(this);

        UIHelper.setColorAccent(this, binding.title);
        UIHelper.setColorAccent(this, binding.toLogin);

        binding.name.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                if(UIHelper.liveValName(binding.name.getText().toString())){
                    binding.name.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.acc_green));
                    binding.name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    binding.name.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                    binding.name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                }
            }
        });

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

        binding.confirmPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                if(binding.confirmPassword.getText().toString().equals(binding.password.getText().toString())){
                    binding.confirmPassword.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.acc_green));
                    binding.confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    binding.confirmPassword.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                    binding.confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                }
            }
        });

        binding.phone.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                if(UIHelper.liveValPhone(binding.phone.getText().toString())){
                    binding.phone.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.acc_green));
                    binding.phone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    binding.phone.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                    binding.phone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                }
            }
        });

        binding.register.setOnClickListener(n -> {
            if(User.Handler.validateName(this, binding.name.getText().toString())
            && User.Handler.validateEmail(this, binding.email.getText().toString())
            && User.Handler.validatePassword(this, binding.password.getText().toString())
            && binding.confirmPassword.getText().toString().equals(binding.password.getText().toString())
            && User.Handler.validatePhone(this, binding.phone.getText().toString())) {
                
                if(userDB.checkDuplication(binding.name.getText().toString(), binding.email.getText().toString(), binding.phone.getText().toString())) {
                    userDB.addUser(new User(0, binding.name.getText().toString().toLowerCase(), binding.email.getText().toString().toLowerCase(), binding.phone.getText().toString(), false),
                            User.Handler.hashPassword(binding.password.getText().toString()));

                    int id = userDB.checkCredentials(binding.email.getText().toString(), User.Handler.hashPassword(binding.password.getText().toString()));
                    User.Current.login(userDB.getUser(id));

                    Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, OTPActivity.class));
                } else {
                    Toast.makeText(this, "Already registered!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.toLogin.setOnClickListener(n -> {
            finish();
        });

    }

}