package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.social_media_app.Adapter.UserAdapter;
import com.example.social_media_app.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
     RecyclerView user_rv;
    ArrayList<UserModel> list = new ArrayList<>();
     FirebaseAuth auth;
     FirebaseDatabase database;

     Button back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_noti);
        setContentView(R.layout.activity_search);

        user_rv = findViewById(R.id.user_rv);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        back = findViewById(R.id.button7);

        UserAdapter adapter = new UserAdapter(getBaseContext(),list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());

        user_rv.setLayoutManager(layoutManager);
        user_rv.setAdapter(adapter);

        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    user.setUserID(dataSnapshot.getKey());
                    if(!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(user);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}