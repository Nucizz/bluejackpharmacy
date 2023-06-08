package com.example.bluejackpharmacy.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bluejackpharmacy.databinding.ActivityOtpBinding;
import com.example.bluejackpharmacy.object.User;
import com.example.bluejackpharmacy.system.database.UserDB;
import com.example.bluejackpharmacy.system.feature.UIHelper;

import java.util.Random;

public class OTPActivity extends Activity {

    private ActivityOtpBinding binding;
    private UserDB userDB;

    int[] otpCode;
    int index = 0;
    CountDownTimer cooldown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userDB = new UserDB(this);

        otpCode = sendOTP(User.Current.getInfo().getPhone());

        binding.backButton.setOnClickListener(n -> {
            finish();
        });

        binding.code1.addTextChangedListener(autoNext);
        binding.code2.addTextChangedListener(autoNext);
        binding.code3.addTextChangedListener(autoNext);
        binding.code4.addTextChangedListener(autoNext);
        binding.code5.addTextChangedListener(autoNext);
        binding.code6.addTextChangedListener(autoNext);

        UIHelper.setColorAccent(this, binding.phone);
        binding.phone.setText(User.Current.getInfo().getPhone());
        UIHelper.setColorAccent(this, binding.resendCode);

        binding.verify.setOnClickListener(n -> {
            if(binding.code1.getText().toString().isEmpty()
            || binding.code2.getText().toString().isEmpty()
            || binding.code3.getText().toString().isEmpty()
            || binding.code4.getText().toString().isEmpty()
            || binding.code5.getText().toString().isEmpty()
            || binding.code6.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill in the OTP Code", Toast.LENGTH_SHORT).show();
                return;
            }

            int[] codeIn = {
                    Integer.parseInt(binding.code1.getText().toString()),
                    Integer.parseInt(binding.code2.getText().toString()),
                    Integer.parseInt(binding.code3.getText().toString()),
                    Integer.parseInt(binding.code4.getText().toString()),
                    Integer.parseInt(binding.code5.getText().toString()),
                    Integer.parseInt(binding.code6.getText().toString())
            };

            for(int i=0;i<6;i++) {
                if(codeIn[i] != otpCode[i]) {
                    Toast.makeText(this, "Invalid OTP Code", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if(User.Current.getInfo().getId() == 0) {
                int id = userDB.checkCredentials(User.Current.getInfo().getEmail(), getIntent().getStringExtra("password"));
                User.Current.login(userDB.getUser(id));
            }

            userDB.verifyUser(User.Current.getInfo().getId());
            User.Current.getInfo().setVerified(true);

            Toast.makeText(this, "Successfully verified!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        });

        binding.resendCode.setOnClickListener(n -> {
            binding.resendCode.setText("Resend Code");
            otpCode = sendOTP(User.Current.getInfo().getPhone());
        });

    }

    private final TextWatcher autoNext = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() == 6) {
                String codeIn = binding.code1.getText().toString();

                binding.code1.setText(codeIn.substring(0, 1));
                binding.code2.setText(codeIn.substring(1, 2));
                binding.code3.setText(codeIn.substring(2, 3));
                binding.code4.setText(codeIn.substring(3, 4));
                binding.code5.setText(codeIn.substring(4, 5));
                binding.code6.setText(codeIn.substring(5));

                binding.verify.requestFocus();
            } else if (s.length() > 0) {
                switch (index) {
                    case 0:
                        binding.code2.requestFocus();
                        index = 1;
                        break;
                    case 1:
                        binding.code3.requestFocus();
                        index = 2;
                        break;
                    case 2:
                        binding.code4.requestFocus();
                        index = 3;
                        break;
                    case 3:
                        binding.code5.requestFocus();
                        index = 4;
                        break;
                    case 4:
                        binding.code6.requestFocus();
                        index = 5;
                        break;
                    case 5:
                        binding.verify.requestFocus();
                        index = 0;
                        break;
                }
            }
        }
    };

    private int[] sendOTP(String phone) {
        int[] num = {0, 0, 0, 0, 0, 0};

        Random rand = new Random();
        for(int i=0;i<6;i++) {
            int curr = rand.nextInt() % 10;
            num[i] = curr < 0 ? curr * -1 : curr;
        }

        String message = "[#BP] Your Bluejack Pharmacy code is: " + num[0] +  num[1] + num[2] + num[3] + num[4] + num[5] +". Enter this code in to Bluejack Pharmacy application to verify your phone number.";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},100);
            binding.resendCode.setText("Send Code");
            Toast.makeText(this, "Permission is needed to send code!", Toast.LENGTH_SHORT).show();
        } else {
            SmsManager sms = getSystemService(SmsManager.class);
            sms.sendTextMessage(phone, null, message, null, null);
            startTimer();
        }
        return num;
    }

    private void startTimer() {
        binding.resendCode.setEnabled(false);

        cooldown = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.resendCooldown.setText(String.format("Resend code in %d seconds.", millisUntilFinished / 1000));
            }

            public void onFinish() {
                binding.resendCooldown.setText("Didn't receive a code?");
                binding.resendCode.setEnabled(true);
            }

        };

        cooldown.start();
    }
}