package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.computerwebshop.R;
import com.example.computerwebshop.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductFragment extends Fragment {

    private ImageView currentProductImageView;
    private TextView currentNameTV;
    private TextView currentPriceTV;
    private TextView currentCategoryTV;
    private TextView currentDescriptionTV;
    private String productNameArg;
    private FirebaseFirestore db;
    private StorageReference imagesRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        currentProductImageView = view.findViewById(R.id.currentProductImageView);
        currentNameTV = view.findViewById(R.id.currentNameTextView);
        currentPriceTV = view.findViewById(R.id.currentPriceTextView);
        currentCategoryTV = view.findViewById(R.id.currentCategoryTextView);
        currentDescriptionTV = view.findViewById(R.id.currentDescriptionTextView);
        productNameArg = ProductFragmentArgs.fromBundle(getArguments()).getProductName();
        db = FirebaseFirestore.getInstance();
        imagesRef = FirebaseStorage.getInstance().getReference().child("products");

        readProductDataFromDB();

        return view;
    }

    private void readProductDataFromDB() {
        db.collection("products").whereEqualTo("name", productNameArg).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            imagesRef.child(product.getImage() + ".jpg")
                                    .getDownloadUrl()
                                    .addOnSuccessListener(uri -> Glide.with(this)
                                            .load(uri).into(currentProductImageView));
                            currentNameTV.setText(product.getName());
                            String price = product.getPrice() + "Ft";
                            currentPriceTV.setText(price);
                            currentCategoryTV.setText(product.getCategory());
                            currentDescriptionTV.setText(product.getDescription());
                        }
                    }
                });
    }
}