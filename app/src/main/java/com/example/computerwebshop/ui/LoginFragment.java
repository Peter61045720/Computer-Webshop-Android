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

public class LoginFragment extends Fragment {

    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        auth = FirebaseAuth.getInstance();

        EditText emailET = view.findViewById(R.id.editTextEmailAddress);
        EditText passwordET = view.findViewById(R.id.editTextPassword);

        view.findViewById(R.id.loginButton).setOnClickListener(v -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener((OnCompleteListener<AuthResult>) task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
                                NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainFragment();
                                Navigation.findNavController(view).navigate(action);
                                getActivity().invalidateOptionsMenu();
                            } else {
                                Toast.makeText(getActivity(), "Hiba: Sikertelen bejelentkezés", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "Hiba: Hiányzó email vagy jelszó", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.registerTextView).setOnClickListener(v -> {
            NavDirections action =
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment(emailET.getText().toString(), passwordET.getText().toString());
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}