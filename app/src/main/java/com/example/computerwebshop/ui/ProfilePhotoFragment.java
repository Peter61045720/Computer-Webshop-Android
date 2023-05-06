package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.computerwebshop.R;
import com.example.computerwebshop.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfilePhotoFragment extends Fragment {

    private ImageView profilePhotoImageView;
    private StorageReference imageRef;
    private String photoItem;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_photo, container, false);

        Spinner spinner = view.findViewById(R.id.photoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.photo_ids, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        photoItem = "android";
        profilePhotoImageView = view.findViewById(R.id.profilePhotoImageView);

        db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("profile_photos");
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

        String uidArg = ProfilePhotoFragmentArgs.fromBundle(getArguments()).getUserUID();
        String usernameArg = ProfilePhotoFragmentArgs.fromBundle(getArguments()).getUsername();
        String phoneArg = ProfilePhotoFragmentArgs.fromBundle(getArguments()).getPhone();
        String countryArg = ProfilePhotoFragmentArgs.fromBundle(getArguments()).getCountry();

        view.findViewById(R.id.choosePhotoButton).setOnClickListener(v -> {
            uploadUserToDB(new User(uidArg, usernameArg, phoneArg, countryArg, photoItem));
            NavDirections action = ProfilePhotoFragmentDirections.actionProfilePhotoFragmentToMainFragment();
            Navigation.findNavController(view).navigate(action);
            getActivity().invalidateOptionsMenu();
        });

        return view;
    }

    private void setImage(StorageReference imgRef) {
        imgRef.getDownloadUrl()
                .addOnSuccessListener(uri -> Glide.with(this).load(uri).into(profilePhotoImageView));
    }

    private void uploadUserToDB(User user) {
        db.collection("users").document(user.getUserUID()).set(user.getUserMap())
                .addOnSuccessListener(documentReference -> Toast.makeText(getActivity(), "Sikeres regisztráció", Toast.LENGTH_SHORT).show());
    }
}