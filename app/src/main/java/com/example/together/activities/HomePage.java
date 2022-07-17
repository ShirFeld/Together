package com.example.together.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private FirebaseAuth objectFirebaseAuth;
    TextView signOutTV , userNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        objectFirebaseAuth = FirebaseAuth.getInstance();
        signOutTV = findViewById(R.id.signOutTV);
        userNameTV = findViewById(R.id.userNameTV);

        if (objectFirebaseAuth != null){
            String currentUser = objectFirebaseAuth.getCurrentUser().getEmail();
            userNameTV.setText(currentUser);
        }

        signOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objectFirebaseAuth!=null){
                    objectFirebaseAuth.signOut();
                    startActivity(new Intent(HomePage.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}