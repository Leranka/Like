package com.example.admin.like;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {


    Button signOut;
    private FirebaseAuth mAuth;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mAuth = FirebaseAuth.getInstance(); // important call
        signOut = (Button) findViewById(R.id.btn_sign_out);
        username = (TextView) findViewById(R.id.tv_username);


//        again check if the user is already logged in or not
        if (mAuth.getCurrentUser() == null) {

//            User not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


//        fetch and  display the user details

        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();

            username.setText("name  - " + name + "\n"
                    + "email address - " + email
                    + "UID" + uid);
        }


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                finish();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


}
