package com.example.computerwebshop.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.computerwebshop.R;
import com.example.computerwebshop.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ProfileUpdaterFragment extends Fragment {

    private ImageView newPhotoImageView;
    private TextView newUsernameTV;
    private TextView newPhoneTV;
    private TextView newCountryTV;
    private String photoItem;
    private User user;
    private StorageReference imageRef;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_updater, container, false);

        newPhotoImageView = view.findViewById(R.id.newPhotoImageView);
        newUsernameTV = view.findViewById(R.id.newUsernameEditText);
        newPhoneTV = view.findViewById(R.id.newPhoneEditText);
        newCountryTV = view.findViewById(R.id.newCountryEditText);

        Spinner spinner = view.findViewById(R.id.newPhotoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.photo_ids, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        user = null;
        getUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("profile_photos");

        photoItem = "Android";
        imageRef = storageRef.child(photoItem + ".jpg");
        setImage(imageRef);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                photoItem = parent.getItemAtPosition(pos).toString();
                imageRef = storageRef.child(photoItem + ".jpg");
                setImage(imageRef);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        view.findViewById(R.id.updateButton).setOnClickListener(v -> {
            updateUser();
            NavDirections action = ProfileUpdaterFragmentDirections.actionProfileUpdaterFragmentToProfileFragment();
            Navigation.findNavController(view).navigate(action);
            getActivity().invalidateOptionsMenu();
        });

        return view;
    }

    private void updateUser() {
        String username = newUsernameTV.getText().toString();
        String phone = newPhoneTV.getText().toString();
        String country = newCountryTV.getText().toString();
        if (!TextUtils.isEmpty(username)) {
            user.setUsername(username);
        }
        if (!TextUtils.isEmpty(phone)) {
            user.setPhone(phone);
        }
        if (!TextUtils.isEmpty(country)) {
            user.setCountry(country);
        }
        user.setPhoto(photoItem);

        db.collection("users").document(currentUser.getUid()).update(user._getUserMap())
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getActivity(), "Az adatok megváltoztatása sikeres volt.", Toast.LENGTH_SHORT).show();
                });
    }

    private void getUser() {
        db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    user = document.toObject(User.class);
                }
            }
        });
    }

    private void setImage(StorageReference imgRef) {
        imgRef.getDownloadUrl()
                .addOnSuccessListener(uri -> Glide.with(this).load(uri).into(newPhotoImageView));
    }
}