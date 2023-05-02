package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.computerwebshop.R;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        String email = RegisterFragmentArgs.fromBundle(getArguments()).getEmail();
        String password = RegisterFragmentArgs.fromBundle(getArguments()).getPassword();
        if (email != null && !email.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
            EditText emailET = view.findViewById(R.id.editTextEmailAddress);
            EditText passwordET = view.findViewById(R.id.editTextPassword);
            emailET.setText(email);
            passwordET.setText(password);
        }

        return view;
    }
}