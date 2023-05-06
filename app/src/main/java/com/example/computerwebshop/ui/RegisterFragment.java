package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.computerwebshop.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private String country;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        auth = FirebaseAuth.getInstance();

        String emailArg = RegisterFragmentArgs.fromBundle(getArguments()).getEmail();
        String passwordArg = RegisterFragmentArgs.fromBundle(getArguments()).getPassword();

        progressBar = view.findViewById(R.id.registerProgressBar);
        EditText usernameET = view.findViewById(R.id.editTextUserName);
        EditText emailET = view.findViewById(R.id.editTextEmailAddress);
        EditText passwordET = view.findViewById(R.id.editTextPassword);
        EditText confirmPasswordET = view.findViewById(R.id.editTextConfirmPassword);
        EditText phoneET = view.findViewById(R.id.editTextPhone);
        EditText countryET = view.findViewById(R.id.editTextCountry);

        if (!TextUtils.isEmpty(emailArg) && !TextUtils.isEmpty(passwordArg)) {
            emailET.setText(emailArg);
            passwordET.setText(passwordArg);
        }

        view.findViewById(R.id.registerButton).setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            username = usernameET.getText().toString();
            email = emailET.getText().toString();
            password = passwordET.getText().toString();
            confirmPassword = confirmPasswordET.getText().toString();
            phone = phoneET.getText().toString();
            country = countryET.getText().toString();
            if (validateForm()) {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                String uid = auth.getCurrentUser().getUid();
                                NavDirections action =
                                        RegisterFragmentDirections.actionRegisterFragmentToProfilePhotoFragment(uid, username, phone, country);
                                Navigation.findNavController(view).navigate(action);
                                getActivity().invalidateOptionsMenu();
                            } else {
                                Toast.makeText(getActivity(), "Hiba: Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(username)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)
                || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(country)) {
            Toast.makeText(getActivity(), "Hiba: Minden mező kitöltése kötelező", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Hiba: Nem egyeznek a jelszavak", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }
}