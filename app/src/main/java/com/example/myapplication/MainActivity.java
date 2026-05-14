package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText cityEditText;
    private Button searchButton;
    private TextView resultTextView;

    // OpenWeatherMap API Key updated with your new key
    private final String apiKey = "c7e748c98e4f2669e096d626fc7aebb7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        searchButton = findViewById(R.id.searchButton);
        resultTextView = findViewById(R.id.resultTextView);

        searchButton.setOnClickListener(view -> {
            String city = cityEditText.getText().toString().trim();

            if (city.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                return;
            }

            getWeatherData(city);
        });
    }

    private void getWeatherData(String city) {
        resultTextView.setText("Fetching weather...");

        // Encode city name to handle spaces like "New Delhi"
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + Uri.encode(city) +
                "&appid=" + apiKey.trim() +
                "&units=metric";

        Log.d(TAG, "Requesting URL: " + url);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        String result = String.format(Locale.getDefault(), "Temperature: %.1f°C", temp);
                        resultTextView.setText(result);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Parsing error", e);
                        resultTextView.setText("Error: Data parsing failed");
                    }
                },
                error -> {
                    String message = "Error: ";
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        try {
                            String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            JSONObject data = new JSONObject(body);
                            String apiMessage = data.optString("message", "unknown error");
                            
                            if (statusCode == 401) {
                                message = "Invalid API Key. (Wait 2-3 hours for activation if recently created)";
                            } else if (statusCode == 404) {
                                message = "City not found. Check spelling.";
                            } else {
                                message += apiMessage + " (Code: " + statusCode + ")";
                            }
                        } catch (Exception e) {
                            message += "Status Code " + statusCode;
                        }
                    } else {
                        message += "Network error. Please check your internet connection.";
                    }
                    resultTextView.setText(message);
                    Log.e(TAG, "Volley Error: " + message);
                }
        );

        queue.add(request);
    }
}
