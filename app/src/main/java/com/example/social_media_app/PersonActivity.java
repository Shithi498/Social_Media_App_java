package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social_media_app.Adapter.FriendsAdapter;
import com.example.social_media_app.Model.FriendsModel;
import com.example.social_media_app.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonActivity extends AppCompatActivity {
    RecyclerView friendRv;
    ImageView saveImage;
    ImageView saveImage1;
    ImageView coverImg;
    ImageView profileImg;

    ImageView signout;
    ArrayList<FriendsModel> list1;

     FirebaseAuth auth;
     FirebaseStorage storage;
FirebaseDatabase database;
TextView name;
TextView bio,friendsCount;
Button back;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
       friendRv = findViewById(R.id.friendRv);
        saveImage=findViewById(R.id.saveImg2);
        saveImage1=findViewById(R.id.saveImg1);
        coverImg=findViewById(R.id.coverImg);
        profileImg=findViewById(R.id.FollowImg);
        name= findViewById(R.id.Name);
        bio = findViewById(R.id.bio);
        friendsCount=findViewById(R.id.textView16);
        signout = findViewById(R.id.signout);
        back=findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        database= FirebaseDatabase.getInstance();
        database.getReference().child("User").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserModel user = snapshot.getValue(UserModel.class);
                    Picasso.get().load(user.getCoverPhoto())
                            .placeholder(R.drawable.spidy1)
                            .into(coverImg);

                    Picasso.get().load(user.getProfilePhoto())
                            .placeholder(R.drawable.spidy1)
                            .into(profileImg);

                    name.setText(user.getName());
                    bio.setText(user.getBio());
                    friendsCount.setText(user.getFriendCount()+"");



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       list1 = new ArrayList<>();
        /*list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));
        list1.add(new FriendsModel(R.drawable.spidy1));*/
        FriendsAdapter adapter1 = new FriendsAdapter(list1, getBaseContext());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        friendRv.setLayoutManager(linearLayoutManager1);
        friendRv.setNestedScrollingEnabled(false);
        friendRv.setAdapter(adapter1);

        database.getReference().child("User")
                        .child(auth.getUid())
                                .child("friends").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                           FriendsModel friend = dataSnapshot.getValue(FriendsModel.class);
                           list1.add(friend);
                       }
                       adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,23);
            }

        });

        saveImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

               intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }

        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(PersonActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });







    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 23 || requestCode == 22) {
                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    ImageView imageViewToUpdate;

                    if (requestCode == 23) {
                        imageViewToUpdate = coverImg;
                    } else {
                        imageViewToUpdate = profileImg;
                    }

                    imageViewToUpdate.setImageURI(uri);

                    final StorageReference reference = storage.getReference().child("cover_photo")
                            .child(FirebaseAuth.getInstance().getUid());

                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getBaseContext(), "Photo saved", Toast.LENGTH_SHORT).show();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if (requestCode == 23) {
                                        database.getReference().child("User").child(auth.getUid()).child("coverPhoto")
                                                .setValue(uri.toString());
                                    } else {
                                        database.getReference().child("User").child(auth.getUid()).child("profilePhoto")
                                                .setValue(uri.toString());
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }
    }


}
