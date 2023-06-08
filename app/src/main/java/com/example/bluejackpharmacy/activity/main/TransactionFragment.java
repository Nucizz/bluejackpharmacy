package com.example.bluejackpharmacy.activity.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluejackpharmacy.R;
import com.example.bluejackpharmacy.databinding.FragmentHomeBinding;
import com.example.bluejackpharmacy.databinding.FragmentTransactionBinding;
import com.example.bluejackpharmacy.object.Transaction;
import com.example.bluejackpharmacy.object.User;
import com.example.bluejackpharmacy.system.adapter.TransactionAdapter;
import com.example.bluejackpharmacy.system.database.TransactionDB;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;
    private TransactionDB transactionDB;
    public static ArrayList<Transaction> transactionList;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        transactionDB = new TransactionDB(getContext());

        transactionList = transactionDB.getTransaction(User.Current.getInfo().getId());

        binding.transactionView.setAdapter(new TransactionAdapter(getContext(), transactionList));
        binding.transactionView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        return binding.getRoot();
    }
}