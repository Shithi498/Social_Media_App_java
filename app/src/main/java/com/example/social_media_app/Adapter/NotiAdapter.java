package com.example.social_media_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.social_media_app.CommentActivity;
import com.example.social_media_app.Model.NotiModel;
import com.example.social_media_app.Model.UserModel;
import com.example.social_media_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.viewHolder>{

    ArrayList<NotiModel> list;
    Context context;


    public NotiAdapter( ArrayList<NotiModel> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.noti_rv,parent,false);
        return new viewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NotiModel notification = list.get(position);
        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(notification.getNotiBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user = snapshot.getValue(UserModel.class);
                        Picasso.get().load(user.getProfilePhoto())
                                .placeholder(R.drawable.spidy1)
                                .into(holder.FollowImg);

                        holder.notiBy.setText("Notification by "+user.getName());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.openNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId",notification.getPostId());
                intent.putExtra("postedBy",notification.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
          ImageView FollowImg;
          TextView notiBy,notiTxt;
        ConstraintLayout openNoti;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            FollowImg=itemView.findViewById(R.id.FollowImg);
            notiBy = itemView.findViewById(R.id.notiBy);
            notiTxt= itemView.findViewById(R.id.notiTxt);
            openNoti = itemView.findViewById(R.id.openNoti);





        }
    }

}

