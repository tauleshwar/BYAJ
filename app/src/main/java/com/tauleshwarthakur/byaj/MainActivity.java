package com.tauleshwarthakur.byaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton siButton =  findViewById(R.id
                .siButton);
        siButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSimpleInterest();
            }
        });

        ImageButton ciButton =  findViewById(R.id
                .ciButton);
        ciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                openCompondInterest();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    public void openSimpleInterest() {
        Intent intent = new Intent(this, SimpleInterest.class);
        startActivity(intent);
    }
    public void openCompondInterest() {

        Intent intent = new Intent(this, CompoundInterest.class);
        startActivity(intent);
    }
}