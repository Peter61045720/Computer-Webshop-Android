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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    private ImageView currentPhotoImageView;
    private TextView currentUsername;
    private TextView currentEmail;
    private TextView currentPhone;
    private TextView currentCountry;
    private StorageReference photosRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        photosRef = storage.getReference().child("profile_photos");

        currentPhotoImageView = view.findViewById(R.id.currentPhotoImageView);
        currentUsername = view.findViewById(R.id.currentUsernameTextView);
        currentEmail = view.findViewById(R.id.currentEmailTextView);
        currentPhone = view.findViewById(R.id.currentPhoneTextView);
        currentCountry = view.findViewById(R.id.currentCountryTextView);

        readUserDataFromDB();

        return view;

    }

    private void readUserDataFromDB() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(currentUser.getUid()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            photosRef.child(document.getString("photo") + ".jpg")
                                    .getDownloadUrl()
                                    .addOnSuccessListener(uri -> Glide.with(this)
                                            .load(uri).into(currentPhotoImageView));
                            currentUsername.setText(document.getString("username"));
                            currentEmail.setText(currentUser.getEmail());
                            currentPhone.setText(document.getString("phone"));
                            currentCountry.setText(document.getString("country"));
                        }
                    }
                });
    }
}