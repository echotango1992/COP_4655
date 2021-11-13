package com.example.weatherapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //OpenWeatherMaps API info
    public static final String WEATHER_API_KEY = "4935d4d97bed7155923e9de5c455e068";
    public static final String URL = "https://api.openweathermap.org/data/2.5/weather?appid=";
    //Intent needed
    public static final String EXTRA_MESSAGE = "com./*EXAMPLE*/.weatherapp.MESSAGE";
    //location GPS part
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    //format for the GPS location to two decimal places
    private static final DecimalFormat df =  new DecimalFormat("0.00");

    //GPS tracker
    GPSTracker gps;
    private ImageView iv_mic;
    private EditText tv_Speech_to_text;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_mic = findViewById(R.id.iv_mic);
        tv_Speech_to_text = findViewById(R.id.user_input);

        //declaration of needed items
        EditText userIN = (EditText) findViewById(R.id.user_input);
        ImageButton go_Button = (ImageButton) findViewById(R.id.goButton);
        ImageButton location_Button = (ImageButton) findViewById(R.id.LocationButton);

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast
                            .makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        // getting user geolocation permission
        try{
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                /*if the permission is not granted by the user, this condition
                 * will execute everytime, otherwise your else part will work */
            }
        } catch (Exception e) {
            Log.e("TAG","Exception is: " + e.getMessage());
        }


        //adding listener to go button
        go_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String City_Zip = userIN.getText().toString();
                //check if user input is empty
                if (City_Zip.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Can not enter and empty value \n - Try again.", Toast.LENGTH_SHORT).show();
                } else { //if not empty then
                    //check if city or zip
                    if (City_Zip.matches("\\d+(?:\\.\\d+)?")){
                        //if number then check length --> it must be 5 numbers
                        Log.e("TAG", "its a Zip-code");
                        if (City_Zip.length() == 5){
                            //correct zip format
                            onZipGO(City_Zip);
                        } else{
                            //incorrect length show message
                            Toast.makeText(getApplicationContext(), "Incorrect Zip format \n - Try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("TAG", "its a City ");
                        onCityGO(City_Zip);
                    }

                }
            }
        });

        //add listener to location button
        location_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(MainActivity.this);

                //check if gps is enabled
                if (gps.canGetLocation()){
                    String lat = df.format(gps.getLatitude());
                    String lon = df.format(gps.getLongitude());
                    Log.e("TAG", "[+] lat is: "  + lat + "\n [+] lon is: " + lon);
                    onLocationGO(lat, lon);
                } else {
                    //cant get location
                    // GPS or network is not enable
                    // Ask user to enable GPS or check settings
                    gps.showSettingsAlert();
                }
            }
        });

    } // end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                tv_Speech_to_text.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }

    public void onZipGO(String value){
        //creating url for ZIP
        String url = URL + WEATHER_API_KEY + "&zip=" + value;
        Log.e("TAG","the url is: " + url);
        sendURL(url);
    } // end of onZipGO

    public void onCityGO(String value){
        //creating the url for City
        String url = URL + WEATHER_API_KEY + "&q=" + value;
        Log.e("TAG", "the url is: " + url);
        sendURL(url);
    }// end of onCityGO

    public void onLocationGO(String lat, String lon){
        //creating url for geolocation
        String url = URL + WEATHER_API_KEY + "&lat=" + lat + "&lon=" + lon;
        Log.e("TAG", "the url is: " + url);
        sendURL(url);
    } //end of onLocationGO

    public void sendURL(String url){
        Toast.makeText(getApplicationContext(), "Getting requested information", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),WeatherData.class);
        intent.putExtra("url", url);
        startActivity(intent);
    } //end of sendURL

} //end of MainActivity