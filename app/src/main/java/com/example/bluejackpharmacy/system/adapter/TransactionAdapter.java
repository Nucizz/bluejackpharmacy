package com.example.bluejackpharmacy.system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.activity.main.TransactionFragment;
import com.example.bluejackpharmacy.databinding.ItemMedicineBinding;
import com.example.bluejackpharmacy.databinding.ItemTransactionBinding;
import com.example.bluejackpharmacy.object.Medicine;
import com.example.bluejackpharmacy.object.Transaction;
import com.example.bluejackpharmacy.system.database.MedicineDB;
import com.example.bluejackpharmacy.system.database.TransactionDB;
import com.example.bluejackpharmacy.system.feature.UIHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    private Context ctx;
    private ArrayList<Transaction> data;
    private MedicineDB medicineDB;
    private TransactionDB transactionDB;

    public TransactionAdapter(Context ctx, ArrayList<Transaction> items) {
        this.ctx = ctx;
        this.data = items;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(layoutInflater, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        medicineDB = new MedicineDB(ctx);
        holder.bindView(data.get(position), medicineDB.getMedicineById(data.get(position).getMedicineId()), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemTransactionBinding binding;

        public ViewHolder(@NonNull ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(Transaction data, Medicine med, int pos) {
            binding.name.setText(med.getName());
            binding.date.setText(data.getDate());
            binding.priceQty.setText(String.format("%s • %d items", UIHelper.getCurrencyFormat(med.getPrice()), data.getQuantity()));
            UIHelper.setColorAccent(binding.getRoot().getContext(), binding.totalPrice);
            binding.totalPrice.setText(UIHelper.getCurrencyFormat(med.getPrice() * data.getQuantity()));

            if(med.getImageURL() != null) {
                Picasso.get().load(med.getImageURL()).error(R.drawable.vec_error).into(binding.image);
            }

            AtomicInteger quantity = new AtomicInteger(data.getQuantity());
            binding.numberQty.setText(String.valueOf(quantity));
            binding.decreaseQty.setOnClickListener(n -> {
                quantity.set(updateQuantity(binding.getRoot().getContext(), quantity.get(),-1, true));
            });
            binding.increaseQty.setOnClickListener(n -> {
                quantity.set(updateQuantity(binding.getRoot().getContext(), quantity.get(),1, true));
            });

            binding.numberQty.setImeOptions(EditorInfo.IME_ACTION_DONE);
            binding.numberQty.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    try {
                        if(binding.numberQty.getText().toString().isEmpty()) {
                            quantity.set(updateQuantity(binding.getRoot().getContext(), quantity.get(),0, false));
                        } else {
                            quantity.set(updateQuantity(binding.getRoot().getContext(), quantity.get(),Integer.parseInt(binding.numberQty.getText().toString()), false));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            binding.modify.setOnClickListener(n -> {
                if(binding.expandable.getVisibility() == View.GONE) {
                    transactionDB = new TransactionDB(ctx);
                    binding.expandable.setVisibility(View.VISIBLE);
                    binding.modify.setText(" Cancel");
                } else {
                    binding.expandable.setVisibility(View.GONE);
                    binding.modify.setText(" Modify");
                }
            });

            binding.delete.setOnClickListener(n -> {
                transactionDB.deleteTransaction(data.getId());
                TransactionFragment.transactionList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(0, getItemCount());
                Toast.makeText(binding.getRoot().getContext(), "Successfully deleted transaction!", Toast.LENGTH_SHORT).show();
            });

            binding.save.setOnClickListener(n -> {
                try {
                    if(binding.numberQty.getText().toString().isEmpty()) {
                        quantity.set(updateQuantity(binding.getRoot().getContext(), quantity.get(),0, false));
                    } else {
                        quantity.set(updateQuantity(binding.getRoot().getContext(), quantity.get(),Integer.parseInt(binding.numberQty.getText().toString()), false));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TransactionFragment.transactionList.get(pos).setQuantity(quantity.get());
                transactionDB.updateTransaction(data.getId(), quantity.get());
                notifyItemChanged(pos);
                Toast.makeText(binding.getRoot().getContext(), "Successfully updated quantity!", Toast.LENGTH_SHORT).show();
            });
        }

        private int updateQuantity(Context ctx, int quantity, int value, boolean addToQuantity) {
            quantity = addToQuantity ? (quantity + value) : value;

            if(quantity < 1) {
                quantity = 1;
                Toast.makeText(ctx, "Quantity must be at least 1!", Toast.LENGTH_SHORT).show();
            }

            binding.numberQty.setText(String.valueOf(quantity));
            return quantity;
        }
    }
}
