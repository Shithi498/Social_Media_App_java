package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social_media_app.Adapter.CommentAdapter;
import com.example.social_media_app.Model.CommentModel;
import com.example.social_media_app.Model.NotiModel;
import com.example.social_media_app.Model.PostModel;
import com.example.social_media_app.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    RecyclerView cmntRv;
    ArrayList<CommentModel> list = new ArrayList<>();
    Intent intent;
    String postId,postedBy;

    FirebaseAuth auth;
    FirebaseDatabase database;

    ImageView postImage,FollowImg, commentSend;
    TextView postDes,likeText,name1,commentBody,commentCount;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        cmntRv = findViewById(R.id.cmntRv);
        auth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        postImage=findViewById(R.id.post);
        postDes=findViewById(R.id.postDes);
        likeText= findViewById(R.id.likeText);
        FollowImg= findViewById(R.id.FollowImg);
        commentSend= findViewById(R.id. commentSend);
        name1= findViewById(R.id.name);
        commentBody=findViewById(R.id.commentBody);
        commentCount=findViewById(R.id.commenttxt);
        intent = getIntent();

        postId=intent.getStringExtra("postId");
        postedBy=intent.getStringExtra("postedBy");

        database.getReference().child("post")
                .child(postId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PostModel model = snapshot.getValue(PostModel.class);
                        Picasso.get()
                                .load(model.getPostImage())
                                .placeholder(R.drawable.spidy1)
                                .into(postImage);
                        postDes.setText(model.getWritePost());
                        likeText.setText(model.getPostLike()+"");
                        commentCount.setText(model.getCommentCount()+"");



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        database.getReference()
                .child("User")
                .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user  = snapshot.getValue(UserModel.class);
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .into(FollowImg);
                        name1.setText(user.getName());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentModel comment = new CommentModel();
                comment.setComment(commentBody.getText().toString());
                comment.setCommentedBy(FirebaseAuth.getInstance().getUid());

                database.getReference().child("post")
                        .child(postId)
                        .child("comment")
                        .push()
                        .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("post")
                                        .child(postId)
                                        .child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                int commentCount =0;
                                                if(snapshot.exists()){
                                                    commentCount = snapshot.getValue(Integer.class);
                                                }

                                                database.getReference()
                                                        .child("post")
                                                        .child(postId)
                                                        .child("commentCount")
                                                        .setValue(commentCount+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                commentBody.setText("");
                                                                Toast.makeText(CommentActivity.this,"Commented",Toast.LENGTH_SHORT).show();

                                                                NotiModel notification = new NotiModel();
                                                                notification.setNotiBy(FirebaseAuth.getInstance().getUid());
                                                                notification.setPostId(postId);
                                                                notification.setPostedBy(postedBy);

                                                                FirebaseDatabase.getInstance().getReference()
                                                                        .child("notification")
                                                                        .child(postedBy)
                                                                        .push()
                                                                        .setValue(notification);
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        });
            }
        });
        CommentAdapter adapter = new CommentAdapter(this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cmntRv.setLayoutManager(layoutManager);
        cmntRv.setAdapter(adapter);
        database.getReference().child("post")
                .child(postId)
                .child("comment").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            CommentModel comment = dataSnapshot.getValue(CommentModel.class);
                            list.add(comment);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}