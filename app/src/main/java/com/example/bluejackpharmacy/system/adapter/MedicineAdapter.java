package com.example.bluejackpharmacy.system.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.activity.MedicineDetailActivity;
import com.example.bluejackpharmacy.databinding.ItemMedicineBinding;
import com.example.bluejackpharmacy.object.Medicine;
import com.example.bluejackpharmacy.system.feature.UIHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Medicine> data;

    public MedicineAdapter(Context ctx, ArrayList<Medicine> items) {
        this.ctx = ctx;
        this.data = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMedicineBinding binding = ItemMedicineBinding.inflate(layoutInflater, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMedicineBinding binding;

        public ViewHolder(@NonNull ItemMedicineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(Medicine data) {
            binding.name.setText(data.getName());
            binding.manufacturer.setText(data.getManufacturer());
            binding.price.setText(UIHelper.getCurrencyFormat(data.getPrice()));

            if(data.getImageURL() != null) {
                Picasso.get().load(data.getImageURL()).error(R.drawable.vec_error).into(binding.image);
            }

            binding.onClick.setOnClickListener(n -> {
                Intent intent = new Intent(ctx, MedicineDetailActivity.class);
                intent.putExtra("item", data);
                ctx.startActivity(intent);
            });

        }
    }

}
