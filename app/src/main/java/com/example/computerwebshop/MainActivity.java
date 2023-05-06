package com.example.computerwebshop;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.computerwebshop.ui.MainFragmentDirections;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private String photoID;
    private String username;
    private String email;
    private NavController navController;
    private NavigationView navView;
    private AppBarConfiguration appBarConfiguration;
    private FirebaseAuth auth;
    private StorageReference photosRef;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            auth.signOut();
        }
        db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        photosRef = storage.getReference().child("profile_photos");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        navView = findViewById(R.id.navView);
        navView.getMenu().findItem(R.id.logoutItem).setOnMenuItemClickListener(menuItem -> {
            Toast.makeText(getApplicationContext(), "Sikeres kijelentkezés", Toast.LENGTH_SHORT).show();
            auth.signOut();
            NavDirections action = MainFragmentDirections.actionMainFragmentToLoginFragment();
            navController.navigate(action);
            assert appBarConfiguration.getOpenableLayout() != null;
            appBarConfiguration.getOpenableLayout().close();
            invalidateOptionsMenu();
            return true;
        });

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser currentUser = auth.getCurrentUser();
        Menu navMenu = navView.getMenu();
        boolean isUserSignedIn = currentUser != null;
        photoID = "android";
        username = "Felhasználónév";
        email = "Email-cím";

        navMenu.findItem(R.id.loginFragment).setVisible(!isUserSignedIn);
        navMenu.findItem(R.id.profileFragment).setVisible(isUserSignedIn);
        navMenu.findItem(R.id.logoutItem).setVisible(isUserSignedIn);

        ShapeableImageView navImageView = findViewById(R.id.navImageView);
        TextView navUsernameTV = findViewById(R.id.navUsernameTextView);
        TextView navEmailTV = findViewById(R.id.navEmailTextView);

        if (isUserSignedIn) {
            db.collection("users").document(currentUser.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                photoID = document.getString("photo");
                                username = document.getString("username");
                                photosRef.child(photoID + ".jpg").getDownloadUrl()
                                        .addOnSuccessListener(uri -> Glide.with(this).load(uri).into(navImageView));
                                navUsernameTV.setText(username);
                            }
                        }
                    });
            email = currentUser.getEmail();
        } else {
            photosRef.child(photoID + ".jpg").getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide.with(this).load(uri).into(navImageView));
            navUsernameTV.setText(username);
        }
        navEmailTV.setText(email);

        return super.onPrepareOptionsMenu(navMenu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}