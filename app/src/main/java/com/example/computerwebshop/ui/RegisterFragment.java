package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.computerwebshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        auth = FirebaseAuth.getInstance();

        String emailArg = RegisterFragmentArgs.fromBundle(getArguments()).getEmail();
        String passwordArg = RegisterFragmentArgs.fromBundle(getArguments()).getPassword();
        EditText emailET = view.findViewById(R.id.editTextEmailAddress);
        EditText passwordET = view.findViewById(R.id.editTextPassword);

        if (!TextUtils.isEmpty(emailArg) && !TextUtils.isEmpty(passwordArg)) {
            emailET.setText(emailArg);
            passwordET.setText(passwordArg);
        }

        view.findViewById(R.id.registerButton).setOnClickListener(v -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                EditText confirmPasswordET = view.findViewById(R.id.editTextConfirmPassword);
                String confirmPassword = confirmPasswordET.getText().toString();
                if (password.equals(confirmPassword)) {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener((OnCompleteListener<AuthResult>) task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
                                    NavDirections action =
                                            RegisterFragmentDirections.actionRegisterFragmentToMainFragment();
                                    Navigation.findNavController(view).navigate(action);
                                    getActivity().invalidateOptionsMenu();
                                } else {
                                    Toast.makeText(getActivity(), "Hiba: Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "Hiba: Nem egyeznek a jelszavak", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Hiba: Hiányzó email vagy jelszó", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}