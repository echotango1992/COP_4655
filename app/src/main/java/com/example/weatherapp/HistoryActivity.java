package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    public String zip_OR_city, lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.action_history:
                    Toast.makeText(HistoryActivity.this, "Home Go!", Toast.LENGTH_SHORT).show();
                    Intent historyIntent = new Intent(HistoryActivity.this, HistoryActivity.class);
                    startActivity(historyIntent);
                    break;
                case R.id.action_map:
                    Toast.makeText(HistoryActivity.this, "Map Go!", Toast.LENGTH_SHORT).show();
                    Intent mapIntent = new Intent(HistoryActivity.this, MapsActivity.class);
                    mapIntent.putExtra("latitude", lat);
                    mapIntent.putExtra("longitude", lon);
                    mapIntent.putExtra("city", zip_OR_city);
                    startActivity(mapIntent);
                    break;
                case R.id.action_daily:
                    Toast.makeText(HistoryActivity.this, "Daily", Toast.LENGTH_SHORT).show();
                    Intent dailyIntent = new Intent(HistoryActivity.this, WeatherData.class);
                    startActivity(dailyIntent);
                    break;
            }
            return true;
        });
    }
}

