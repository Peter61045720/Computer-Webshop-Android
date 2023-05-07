package com.example.computerwebshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.computerwebshop.R;
import com.example.computerwebshop.model.Product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductAdapter extends FirestoreRecyclerAdapter<Product, ProductAdapter.ProductHolder> {

    private Context context;

    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Product model) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child("products").child( model.getImage() + ".jpg");
        holder.productNameTV.setText(model.getName());
        imageRef.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(this.context).load(uri).into(holder.productImageView));
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card, parent, false);
        return new ProductHolder(view);
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {

        private TextView productNameTV;
        private ImageView productImageView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productNameTV = itemView.findViewById(R.id.productNameTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
        }
    }
}
