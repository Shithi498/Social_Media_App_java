package com.example.social_media_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.Model.FriendsModel;

import com.example.social_media_app.Model.UserModel;
import com.example.social_media_app.R;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.viewHolder>{

    ArrayList<FriendsModel> list;
    Context context;


    public FriendsAdapter( ArrayList<FriendsModel> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_rv,parent,false);
        return new viewHolder(view);
    }



    // @Override
    //// public void onBindViewHolder(@NonNull PostAdapter.viewHolder holder, int position) {
    //PostModel model =list.get(position);
    //holder.profile.setImageResource(model.getProfile());
    //holder.post.setImageResource(model.getProfile());
    //holder.more.setImageResource(model.getMore());
    //holder.comment.setImageResource(model.getProfile());
    //holder.like.setImageResource(model.getLike());
    //holder.share.setImageResource(model.getShare());
    // holder.name.setText(model.getName());
    // holder.commenttxt.setText(model.getCommenttxt());
    //  holder.liketxt.setText(model.getLiketxt());
    ///holder.sharetxt.setText(model.getSharetxt());

    // }

    // PostAdapter.java
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FriendsModel model = list.get(position);
        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(model.getFollowedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user = snapshot.getValue(UserModel.class);
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .placeholder(R.drawable.spidy1)
                                .into(holder.friend);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView friend;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
          friend=itemView.findViewById(R.id.FollowImg);






        }
    }

}
