package com.example.social_media_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.Model.FriendsModel;
import com.example.social_media_app.Model.NotiModel;
import com.example.social_media_app.Model.UserModel;
import com.example.social_media_app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{
    Context context;
    ArrayList<UserModel> list;

    ImageView FollowImg;


    public UserAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.search_rv,parent,false);
       return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        UserModel user = list.get(position);
        Picasso.get().load(user.getProfilePhoto())
                .placeholder(R.drawable.spidy1)
                .into(holder.FollowImg);
        holder.Sname.setText(user.getName());

        FirebaseDatabase.getInstance().getReference()
                        .child("User")
                   .child(user.getUserID())
                .child("friends")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.button.setText("Following");
                            holder.button.setEnabled(false);
                        }else{
                            holder.button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FriendsModel friend = new FriendsModel();
                                    friend.setFollowedBy(FirebaseAuth.getInstance().getUid());
                                    friend.setFollowedAt(new Date().getTime());

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("User").child(user.getUserID())
                                            .child("friends")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("User").child(user.getUserID())
                                                            .child("friendCount")
                                                            .setValue(user.getFriendCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    holder.button.setText("Following");
                                                                    holder.button.setEnabled(false);
                                                                    Toast.makeText(context,"Send request",Toast.LENGTH_LONG).show();

                                                                    NotiModel noti = new NotiModel();
                                                                    noti.setNotiBy(FirebaseAuth.getInstance().getUid());

                                                                    noti.setType("follow");

                                                                    FirebaseDatabase.getInstance().getReference()
                                                                            .child("notification")
                                                                            .child(user.getUserID())
                                                                            .push()
                                                                            .setValue(noti);
                                                                }
                                                            });

                                                }
                                            });
                                }
                            });
                        }
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
        public ImageView FollowImg;
        public TextView Sname;
         public Button button;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            FollowImg = itemView.findViewById(R.id.FollowImg);
            Sname= itemView.findViewById(R.id.Sname);
            button=itemView.findViewById(R.id.button);
        }
    }
}
