package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreateAdvert = findViewById(R.id.btn_create_advert);
        btnCreateAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, fragmentCreateAdvert.class);
                startActivity(intent);
            }
        });

        Button btnShowAdvert = findViewById(R.id.btn_show_advert);
        btnShowAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, fragmentShowAdvert.class);
                startActivity(intent);
            }
        });



    }
}