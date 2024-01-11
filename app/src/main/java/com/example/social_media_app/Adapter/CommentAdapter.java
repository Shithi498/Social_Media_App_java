package com.example.social_media_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.Model.CommentModel;
import com.example.social_media_app.Model.UserModel;
import com.example.social_media_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder>{
    Context context;
    ArrayList<CommentModel> list;

    FirebaseDatabase database;

    public CommentAdapter(Context context, ArrayList<CommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.comment_rv,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
             CommentModel comment = list.get(position);

             //holder.comment.setText(comment.getComment());

        FirebaseDatabase.getInstance().getReference()
                .child("User").child(comment.getCommentedBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user = snapshot.getValue(UserModel.class);
                        Picasso.get().load(user.getProfilePhoto())
                                .placeholder(R.drawable.spidy1).into(holder.profile);
                        holder.CommentBy.setText(user.getName());
                        holder.comment.setText(comment.getComment());

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

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView comment,CommentBy,commentedBody;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.FollowImg);
            comment= itemView.findViewById(R.id.commentedBody);
            CommentBy=itemView.findViewById(R.id.CommentBy);


        }
    }
}

