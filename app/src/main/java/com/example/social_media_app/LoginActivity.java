package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social_media_app.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    TextView Socialism,login,register;
    Button btn;
    FirebaseAuth auth;

    FirebaseUser currentUser;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Add this code in your LoginActivity's onCreate method or in your Application class.
        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
       currentUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        email = findViewById(R.id.ladd);
        pass = findViewById(R.id.lphnNbr);
        Socialism = findViewById(R.id.ltextView);
        login = findViewById(R.id.ltextView2);
        btn = findViewById(R.id.lbutton);
        register = findViewById(R.id.ltextView3);

        // btn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String password = pass.getText().toString();
               auth.signInWithEmailAndPassword(email1, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                       }
                   }

                    //  @Override
                    // public void onComplete(@NonNull Task<AuthResult> task) {
                    //  if(task.isSuccessful()){
                    // UserModel UserModel = new UserModel(email1, password);
                    // String id = task.getResult().getUser().getUid();
                    //database.getReference().child("User").child(id).setValue(UserModel);
                    //Toast.makeText(LoginActivity.this, "User data saved", Toast.LENGTH_SHORT).show();

                    //  }
                    // }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
    }

        protected void onStart(){
            super.onStart();
            if(currentUser!= null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

}