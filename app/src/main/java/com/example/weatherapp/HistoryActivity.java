package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    public String zip_OR_city, lat, lon;
    ListView simpleList;
    private static final DecimalFormat df =  new DecimalFormat("0.00");
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);

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

