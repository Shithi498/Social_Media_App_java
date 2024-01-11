package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    EditText Name,email,pass,Cpass;
    TextView healthcare,register;
    Button btn,back;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Name=findViewById(R.id.rName);
        email=findViewById(R.id.rEmail);
        pass=findViewById(R.id.rPass);
        Cpass=findViewById(R.id.rCPass);
        healthcare=findViewById(R.id.rtextView);

        btn=findViewById(R.id.rbutton);
        back=findViewById(R.id.button4);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String email1 = email.getText().toString();
                String password=pass.getText().toString();
                auth.createUserWithEmailAndPassword(email1,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserModel user = new UserModel(name,email1, password);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("User").child(id).setValue(user);
                            Toast.makeText(RegistrationActivity.this, "User data saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        }
                    }
                });

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


       // btn.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View v) {

                //String name = Name.getText().toString();
               // String Email = email.getText().toString();
                //String Pass = pass.getText().toString();
               // String cPass = Cpass.getText().toString();


               // if(name.length()==0 || Email.length()==0 || Pass.length()==0 || cPass.length()==0){
                    //Toast.makeText(RegistrationActivity.this, "Please Enter name,email,password & Confirm password", Toast.LENGTH_SHORT).show();
              //  }
               // else if(Pass.compareTo(cPass)==0){
                   // if(isValid(Pass)){


                        //Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                  //  }
                  //  else{
                       // Toast.makeText(RegistrationActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                   // }
               // }
               // else{
                   // Toast.makeText(RegistrationActivity.this, "Password & Confirm password aren't matched", Toast.LENGTH_SHORT).show();
               // }


            //}
       // });


    }
    //public static boolean isValid(String pass){
        //int f1=0,f2=0,f3=0;
       // if(pass.length()<8){
           // return false;
       // }
       // else{
            //for(int p=0;p<pass.length();p++){
               // if(Character.isLetter(pass.charAt(p))){
                    //f1=1;
               // }
            //}
           // for(int p=0;p<pass.length();p++){
               // if(Character.isDigit(pass.charAt(p))){
                //    f2=1;
               // }
           // }
           // for(int p=0;p<pass.length();p++){
              //  char c = pass.charAt(p);
              // if(c>=33&&c<=46||c==64){
                    //f3=1;
               // }
           // }
           // if(f1==1 && f2==1 && f3==1){
              //  return true;
           // }
           // return false;
        //}
    }
