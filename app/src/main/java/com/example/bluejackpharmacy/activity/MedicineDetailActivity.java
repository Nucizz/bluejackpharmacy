package com.example.bluejackpharmacy.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.databinding.ActivityMedicineDetailBinding;
import com.example.bluejackpharmacy.object.Medicine;
import com.example.bluejackpharmacy.object.Transaction;
import com.example.bluejackpharmacy.object.User;
import com.example.bluejackpharmacy.system.database.TransactionDB;
import com.example.bluejackpharmacy.system.feature.UIHelper;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MedicineDetailActivity extends Activity {

    private ActivityMedicineDetailBinding binding;
    private TransactionDB transactionDB;

    private Medicine item;

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicineDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        transactionDB = new TransactionDB(this);

        binding.back.setOnClickListener(n -> {
            finish();
        });

        item = getIntent().getParcelableExtra("item", Medicine.class);

        binding.name.setText(item.getName());
        UIHelper.setColorAccent(this, binding.price);
        binding.price.setText(UIHelper.getCurrencyFormat(item.getPrice()));
        binding.manufacturer.setText(String.format("Manufactured by %s", item.getManufacturer()));
        binding.description.setText(item.getDescription());

        if(item.getImageURL() != null) {
            Picasso.get().load(item.getImageURL()).error(R.drawable.vec_error).into(binding.image);
        }

        binding.numberQty.setText(String.valueOf(quantity));
        binding.decreaseQty.setOnClickListener(n -> {
            updateQuantity(this, -1, true);
        });
        binding.increaseQty.setOnClickListener(n -> {
            updateQuantity(this, 1, true);
        });
        binding.numberQty.setOnFocusChangeListener((v, hasFocus) -> {
            try {
                if (!hasFocus) {
                    if(binding.numberQty.getText().toString().isEmpty()) {
                        updateQuantity(this, 0, false);
                    } else {
                        updateQuantity(this, Integer.parseInt(binding.numberQty.getText().toString()), false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        ViewGroup.LayoutParams cardParameter = binding.card.getLayoutParams();

        if(binding.description.getText().length() > 100) {
            binding.readMore.setVisibility(View.VISIBLE);
            binding.moreElpisis.setVisibility(View.VISIBLE);
        }
        binding.readMore.setOnClickListener(n -> {
            binding.description.setMaxLines(100);
            cardParameter.height = ViewGroup.LayoutParams.MATCH_PARENT;

            binding.readMore.setVisibility(View.GONE);
            binding.moreElpisis.setVisibility(View.GONE);
            binding.showLess.setVisibility(View.VISIBLE);
        });
        binding.showLess.setOnClickListener(n -> {
            binding.description.setMaxLines(3);
            cardParameter.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 425, getResources().getDisplayMetrics());

            binding.readMore.setVisibility(View.VISIBLE);
            binding.moreElpisis.setVisibility(View.VISIBLE);
            binding.showLess.setVisibility(View.GONE);
        });

        binding.purchase.setOnClickListener(e -> {
            quantity = Integer.parseInt(binding.numberQty.getText().toString());

            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
            Calendar cal = Calendar.getInstance();

            transactionDB.addTransaction(new Transaction(0, item.getId(), User.Current.getInfo().getId(), dateFormat.format(cal.getTime()), quantity));

            Toast.makeText(this, "Item succesfully purchased!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void updateQuantity(Context ctx, int value, boolean addToQuantity) {
        quantity = addToQuantity ? (quantity + value) : value;

        if(quantity < 1) {
            quantity = 1;
            Toast.makeText(ctx, "Quantity must be at least 1!", Toast.LENGTH_SHORT).show();
        }

        binding.numberQty.setText(String.valueOf(quantity));
        if(quantity == 1) {
            binding.purchase.setText("PURCHASE");
        } else {
            binding.purchase.setText(UIHelper.getCurrencyFormat(item.getPrice() * quantity));
        }
    }
}