package com.example.admin.like;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LikeActivity extends AppCompatActivity {
    private Boolean mProcessLike = false;
    private DatabaseReference mDBLike;
    private FirebaseAuth mAuth;
    TextView tvLikes;
    ImageButton mLikeButton;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        mDBLike = FirebaseDatabase.getInstance().getReference();
        mDBLike.keepSynced(true);


        tvLikes = (TextView) findViewById(R.id.numLikes);
        mLikeButton = (ImageButton) findViewById(R.id.btnLike);
        mAuth = FirebaseAuth.getInstance();

        mLikeButton.setImageResource(R.drawable.thumbs);

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Likes likes = new Likes();

                if (mProcessLike == false) {

                    //likes.setLike("True");
                    counter++;
                    likes.setNum(counter);

                    mDBLike.child("Likes").child(mAuth.getCurrentUser().getUid()).setValue(likes);
                    mProcessLike = true;

                    mLikeButton.setImageResource(R.drawable.orange);

                } else {

                    if (counter != 0) {
                        counter = counter - 1;
                    }
                    likes.setNum(counter);
                    //likes.setLike("False");
                    mDBLike.child("Likes").child(mAuth.getCurrentUser().getUid()).removeValue();
                    mProcessLike = false;

                   mLikeButton.setImageResource(R.drawable.thumbs);

                }

                // Get a reference to our likes
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Likes");

                // Attach a listener to read the data at our likes reference
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Likes likes = dataSnapshot.getValue(Likes.class);
                if ( likes!=null) {
                    counter = likes.getNum();
                }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                tvLikes.setText(Integer.toString(likes.getNum()));

            }
        });
    }

}
