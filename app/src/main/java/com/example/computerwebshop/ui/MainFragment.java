package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerwebshop.R;
import com.example.computerwebshop.adapter.ProductAdapter;
import com.example.computerwebshop.model.Product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainFragment extends Fragment {

    private Query query;
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Spinner spinner = view.findViewById(R.id.productSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.sorting_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view1, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString();
                if (item.equals("legolcsóbb elöl")) {
                    query = FirebaseFirestore.getInstance().collection("products").orderBy("price", Query.Direction.ASCENDING);
                } else if (item.equals("legdrágább elöl")) {
                    query = FirebaseFirestore.getInstance().collection("products").orderBy("price", Query.Direction.DESCENDING);
                } else {
                    query = FirebaseFirestore.getInstance().collection("products").whereEqualTo("category", item);
                }

                FirestoreRecyclerOptions<Product> newOptions = new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();
                productAdapter.updateOptions(newOptions);
                RecyclerView recyclerView = view.findViewById(R.id.productsRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        query = FirebaseFirestore.getInstance().collection("products");

        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        productAdapter = new ProductAdapter(options);
        productAdapter.setContext(getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(productAdapter);

        return view;
    }

    @Override
    public void onStart() {
        productAdapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        productAdapter.stopListening();
        super.onStop();
    }
}