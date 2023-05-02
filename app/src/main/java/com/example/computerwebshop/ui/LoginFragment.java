package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.computerwebshop.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        view.findViewById(R.id.registerTextView).setOnClickListener(v -> {
            EditText emailET = view.findViewById(R.id.editTextEmailAddress);
            EditText passwordET = view.findViewById(R.id.editTextPassword);

            NavDirections action =
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment(emailET.getText().toString(), passwordET.getText().toString());

            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}