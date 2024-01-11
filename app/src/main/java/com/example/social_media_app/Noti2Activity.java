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

import com.example.social_media_app.Adapter.FriendsAdapter;
import com.example.social_media_app.Adapter.NotiAdapter;
import com.example.social_media_app.Model.FriendsModel;
import com.example.social_media_app.Model.NotiModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Noti2Activity extends AppCompatActivity {

    RecyclerView notiRv;
    Button back1;
    ArrayList<NotiModel> list;

    FirebaseDatabase  database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti2);

        notiRv = findViewById(R.id.Noti_rv);
        back1=findViewById(R.id.back1);

        database= FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        NotiAdapter adapter= new NotiAdapter(list, getBaseContext());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        notiRv.setLayoutManager(linearLayoutManager);
       notiRv.setNestedScrollingEnabled(false);
       notiRv.setAdapter(adapter);

       database.getReference().child("notification")
               .child(FirebaseAuth.getInstance().getUid())
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                           NotiModel notification =dataSnapshot.getValue(NotiModel.class);
                           notification.setNotificationId(dataSnapshot.getKey());
                           list.add(notification);

                       }
                       adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Noti2Activity.this,MainActivity.class));
            }
        });



    }
}