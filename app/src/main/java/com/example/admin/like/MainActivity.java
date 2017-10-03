package com.example.admin.like;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email;
    private EditText password;
    private Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);

        signIn = (Button) findViewById(R.id.btn_sign_in);
        signUp = (Button) findViewById(R.id.btn_sign_up);


//        Checks if user is already logged in
        if (mAuth.getCurrentUser() != null) {

//            User not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), LikeActivity.class));
        }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();

                callSignIn(getEmail, getPassword);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getEmail = email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();

                callSignUp(getEmail, getPassword);
            }
        });

    }


    public void callSignUp(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //Now start sign in process

    public void callSignIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "auth_failed", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), LikeActivity.class));
                        }

                    }
                });
    }
}