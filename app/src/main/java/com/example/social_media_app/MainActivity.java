package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    // Declare the constant resource IDs
    public static final int HOME_ID=R.id.home;;

    public static final int NOTI_ID = R.id.noti;
    public static final int ADD_ID = R.id.add;
    public static final int SEARCH_ID = R.id.search;
    public static final int PERSON_ID = R.id.person;


    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);


           //button.setOnClickListener(new View.OnClickListener() {
   // @Override
    //public void onClick(View view) {
       // startActivity(new Intent(MainActivity.this,RegistrationActivity.class));

   // }
//});
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int icon=item.getItemId();
                if (icon == HOME_ID) {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();

                } else if (icon == NOTI_ID) {
                    startActivity(new Intent(MainActivity.this,Noti2Activity.class));
                    Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();

                }
                if (icon == ADD_ID) {
                    startActivity(new Intent(MainActivity.this,AddActivity.class));
                    Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();

                }
                if (icon == SEARCH_ID) {
                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
                    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();

                }
                if (icon == PERSON_ID) {
                    startActivity(new Intent(MainActivity.this,PersonActivity.class));
                    Toast.makeText(MainActivity.this, "Person", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });



    }
}
