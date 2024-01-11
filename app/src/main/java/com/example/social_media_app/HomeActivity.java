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

import com.example.social_media_app.Adapter.PostAdapter;
import com.example.social_media_app.Model.PostModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView  postRv;
    Button back;

    ArrayList<PostModel> list2;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        back=findViewById(R.id.back);
        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        postRv = findViewById(R.id.postRv);
        list2 = new ArrayList<>();
       /* list2.add(new PostModel(R.drawable.man_post, R.drawable.spidy2, R.drawable.more, R.drawable.comment, R.drawable.like, R.drawable.share, "Shithi Roy", "23", "23", "23"));
        list2.add(new PostModel(R.drawable.man_post, R.drawable.spidy2, R.drawable.more, R.drawable.comment, R.drawable.like, R.drawable.share, "Shithi Roy", "23", "23", "23"));
        list2.add(new PostModel(R.drawable.man_post, R.drawable.spidy2, R.drawable.more, R.drawable.comment, R.drawable.like, R.drawable.share, "Shithi Roy", "23", "23", "23"));
        list2.add(new PostModel(R.drawable.man_post, R.drawable.spidy2, R.drawable.more, R.drawable.comment, R.drawable.like, R.drawable.share, "Shithi Roy", "23", "23", "23"));
        list2.add(new PostModel(R.drawable.man_post, R.drawable.spidy2, R.drawable.more, R.drawable.comment, R.drawable.like, R.drawable.share, "Shithi Roy", "23", "23", "23")); */
        PostAdapter adapter2 = new PostAdapter(list2, getBaseContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        postRv.setLayoutManager(linearLayoutManager2);
        postRv.setNestedScrollingEnabled(false);
        postRv.setAdapter(adapter2);

        database.getReference().child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PostModel post = dataSnapshot.getValue(PostModel.class);
                    post.setPostId(dataSnapshot.getKey());
                    list2.add(post);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });

    }
}