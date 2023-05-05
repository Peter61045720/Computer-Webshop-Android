package com.example.computerwebshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.computerwebshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView loginStatusTV = (TextView) view.findViewById(R.id.textView);
        loginStatusTV.setText(currentUser != null ? "Bejelentkezett felhasználó" : "Nincs bejelentkezett felhasználó");

        return view;
    }
}