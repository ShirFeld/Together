package com.example.together.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.fragments.SignInFragment;
import com.example.together.fragments.SignUpFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView objectBottomNavigationView;
    // 2 Fragments
    SignUpFragment objectSignUpFragment;
    SignInFragment objectSignInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializerObjects();
        replaceFragments(objectSignUpFragment);

        objectBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.itemSignUp:
                        replaceFragments(objectSignUpFragment);
                        return true;
                    case R.id.itemSignIn:
                        replaceFragments(objectSignInFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void replaceFragments(Fragment objectFragment){
        try
        {
            FragmentTransaction objectFragmentTransaction = getSupportFragmentManager().beginTransaction();
            objectFragmentTransaction.replace(R.id.container,objectFragment).commit();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
        }
    }

    private void initializerObjects(){
        try
        {
            objectBottomNavigationView = findViewById(R.id.BNV);
            objectSignUpFragment = new SignUpFragment();
            objectSignInFragment = new SignInFragment();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
        }
    }

}