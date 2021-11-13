package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryData extends AppCompatActivity {

    public String zip_OR_city, lat, lon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    Toast.makeText(HistoryData.this, "Home Go!", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(HistoryData.this, MainActivity.class);
                    startActivity(homeIntent);
                    break;
                case R.id.action_map:
                    Toast.makeText(HistoryData.this, "Map Go!", Toast.LENGTH_SHORT).show();
                    Intent mapIntent = new Intent(HistoryData.this, MapsActivity.class);
                    mapIntent.putExtra("latitude", lat);
                    mapIntent.putExtra("longitude", lon);
                    mapIntent.putExtra("city", zip_OR_city);
                    startActivity(mapIntent);
                    break;
                case R.id.action_daily:
                    Toast.makeText(HistoryData.this, "Daily", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }
}
