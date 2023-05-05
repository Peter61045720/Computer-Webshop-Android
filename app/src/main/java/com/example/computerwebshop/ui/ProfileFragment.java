package com.example.computerwebshop.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.computerwebshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView emailTV = view.findViewById(R.id.emailTextView);

        if (currentUser != null) {
            emailTV.setText(currentUser.getEmail());
        }

        return view;
    }
}