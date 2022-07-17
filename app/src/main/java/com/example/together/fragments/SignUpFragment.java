package com.example.together.fragments;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;


public class SignUpFragment extends Fragment {

    View objectSignUpFragment;
    private EditText userEmail,userPassword;
    private Button button;
    private FirebaseAuth objectFirebaseAuth;
    private ProgressBar objectProgressBar;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public void createUser(){
        try
        {
            if (!userEmail.getText().toString().isEmpty() && !userPassword.getText().toString().isEmpty()){
                objectProgressBar.setVisibility(View.VISIBLE); // we will see the loading
                button.setEnabled(false);

                objectFirebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(),"User created",Toast.LENGTH_SHORT).show();
                                objectProgressBar.setVisibility(View.INVISIBLE);

                                button.setEnabled(true);
                                userEmail.setText("");
                                userPassword.setText("");

                                if (objectFirebaseAuth.getCurrentUser()!= null){
                                    objectFirebaseAuth.signOut();
                                }

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
            else {
                Toast.makeText(getContext(),"Please fill the fields first",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private void checkUserExists(){
        try
        {
            if (objectFirebaseAuth!= null && !userEmail.getText().toString().isEmpty()){
                objectProgressBar.setVisibility(View.VISIBLE);
                button.setEnabled(false);
                objectFirebaseAuth.fetchSignInMethodsForEmail(userEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean checkResult = !task.getResult().getSignInMethods().isEmpty();
                                // if there is not email address that already on firebase
                                if (!checkResult)
                                    createUser();

                                else {
                                    Toast.makeText(getContext(),"User already been created",Toast.LENGTH_SHORT).show();
                                    objectProgressBar.setVisibility(View.INVISIBLE);
                                    button.setEnabled(true);
                                }
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
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void attachToXml(){
        try
        {
            userEmail=objectSignUpFragment.findViewById(R.id.emailET);
            userPassword=objectSignUpFragment.findViewById(R.id.passwordET);

            objectFirebaseAuth =FirebaseAuth.getInstance();
            button= objectSignUpFragment.findViewById(R.id.btnCreate);

            objectProgressBar = objectSignUpFragment.findViewById(R.id.progressBarSignUp);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkUserExists();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectSignUpFragment = inflater.inflate(R.layout.fragment_sign_up,container,false);
        attachToXml();
        return objectSignUpFragment;
    }
}