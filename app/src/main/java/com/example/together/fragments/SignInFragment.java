package com.example.together.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.activities.HomePage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInFragment extends Fragment {

    private View objectSignInFragment;
    private FirebaseAuth objectFirebaseAuth;
    private EditText userEmailET,userPasswordET;
    private Button button;
    private ProgressBar objectProgressBar;

    public SignInFragment() {
        // Required empty public constructor
    }


    private void initializeVariables(){
        try
        {
            objectFirebaseAuth = FirebaseAuth.getInstance();
            userEmailET = objectSignInFragment.findViewById(R.id.signInEmailET);
            userPasswordET = objectSignInFragment.findViewById(R.id.signInPasswordET);
            button = objectSignInFragment.findViewById(R.id.signInBTN);
            objectProgressBar = objectSignInFragment.findViewById(R.id.progressBarSignIp);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInUser();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void signInUser(){
        try
        {
            // we have the details
            if(!userEmailET.getText().toString().isEmpty() && !userPasswordET.getText().toString().isEmpty()){
                if (objectFirebaseAuth!= null){
                    objectProgressBar.setVisibility(View.VISIBLE);
                    button.setEnabled(false);

                    objectFirebaseAuth.signInWithEmailAndPassword(userEmailET.getText().toString(),userPasswordET.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(getActivity().getApplicationContext(), HomePage.class));
                                    objectProgressBar.setVisibility(View.INVISIBLE);
                                    button.setEnabled(true);
                                    getActivity().finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    objectProgressBar.setVisibility(View.INVISIBLE);
                                    button.setEnabled(true);
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
            else {
                Toast.makeText(getContext(),"Pleas fill all fields",Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        objectSignInFragment = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initializeVariables();
        return objectSignInFragment;
    }
}