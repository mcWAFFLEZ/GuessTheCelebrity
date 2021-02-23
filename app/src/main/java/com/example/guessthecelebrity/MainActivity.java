package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    GridLayout myGrid;
    ImageView celebrityImageView;

    public void celebrityPick(View view){

        Log.i("Button","number "+String.valueOf(view.getTag())+" pressed.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGrid = findViewById(R.id.myGridLayout);
        celebrityImageView = findViewById(R.id.celebrityImageView);

            String result = "";

            GetCelebrityNames task = new GetCelebrityNames();

        try {
            result += task.execute("https://www.qssv.net/en/latest-news/top-50-celebrities-who-vape/").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("Website Data",result);
    }

    public class GetCelebrityNames extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            try {
                String result = "";

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream in = connection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            } catch (Exception e) {

                e.printStackTrace();

                return "Failed";
            }
        }
    }
}
