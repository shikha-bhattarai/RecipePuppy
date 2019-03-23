package com.example.homework4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*Homework 4, */

public class MainActivity extends AppCompatActivity {
    EditText inputFirst = null;
    EditText dishName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dishName   = (EditText)findViewById(R.id.editTextDish);
        inputFirst   = (EditText)findViewById(R.id.editText1);
        findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    new GetDataAsync().execute("https://www.recipepuppy.com/api/?i=onions,garlic&q="+dishName+"&q="+inputFirst);
                } else {
                    Toast.makeText(MainActivity.this, "Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Ingredients>> {
        @Override
        protected ArrayList<Ingredients> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Ingredients> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                Log.d("demo:", String.valueOf(url));
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONObject message = root.getJSONObject("results");
//                    JSONObject body = message.getJSONObject("body");
//                    JSONArray musicList = body.getJSONArray("track_list");
                    for (int i = 0; i < message.length(); i++) {
//                        JSONObject newJson = musicList.getJSONObject(i);
//                        JSONObject newJson2 = newJson.getJSONObject("track");
//                        track = new Track();
//                        track.track_name = newJson2.getString("track_name");
//                        track.album_name = newJson2.getString("album_name");
//                        track.artist_name = newJson2.getString("artist_name");
//                        track.updated_time = newJson2.getString("updated_time");
//                        track.track_share_url = newJson2.getString("track_share_url");
//                        result.add(track);
                    }
                    Log.d("array", result.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

//        @Override
//        protected void onPostExecute(ArrayList<Track> result) {
//            items.clear();
//            if (result.size() > 0) {
//                items.addAll(result);
//                displayTrack(items);
//            } else {
//                Log.d("Empty List", "empty result");
//            }
//        }
//
//        private void displayTrack(final ArrayList<Track> result) {
//            progressBar.setVisibility(View.INVISIBLE);
//            ListView listView = (ListView) findViewById(R.id.listView);
//            TrackAdapter adapter = new TrackAdapter(MainActivity.this, R.layout.track_list, result);
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.d("track.track_share_url",track.track_share_url);
//                    String url = track.track_share_url;
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    startActivity(i);
//                }
//            });
//        }
    }
}
