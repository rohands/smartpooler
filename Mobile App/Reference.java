package com.example.rohan.smartpool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rohan on 19/4/16.
 */
public class JoinRide extends Activity{

    EditText source;
    EditText destination;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText baggage;
    ProgressBar progressBar;
    String baggage_weight;
    String starttime,endtime;

    String startDate, endDate;
    Button joinRideButton;
    double lat,lng;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_ride);
        source = (EditText) findViewById(R.id.input_src);
        destination = (EditText) findViewById(R.id.input_dest);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        joinRideButton = (Button) findViewById(R.id.btn_joinride);
        baggage = (EditText) findViewById(R.id.input_baggage);
        joinRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PostData().execute(source.getText().toString(),destination.getText().toString(),baggage.getText().toString());
                }

        });

    }

    private class PostData extends AsyncTask<String,String,String>
    {

        BufferedReader reader=null;

        StringBuilder sb;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String text ="";
            try {

                String data = URLEncoder.encode("source", "UTF-8") + "=" + params[0];
                data += "&" + URLEncoder.encode("dest","UTF-8") + "=" + params[1];
                data += "&" + URLEncoder.encode("baggage","UTF-8") + "=" + params[2];


                URL url = new URL("http://192.168.43.49:8000/smartpool/join_ride/");
                Log.e("url",url.toString());
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
                text = "";
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                text = sb.toString();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(JoinRide.this, s, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }



    private class DataLongOperationAsyncTask extends AsyncTask<String,String,String>
    {
        ProgressDialog dialog = new ProgressDialog(JoinRide.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");

                Log.d("latitude", "" + lat);
                Log.d("longitude", "" + lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }




        @Override
        protected String doInBackground(String... params) {
            String response;
            try {
                response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address=mumbai&sensor=false");
                Log.d("response", "" + response);
                return response;
            } catch (Exception e) {
                return "error";
            }
        }
    }

    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
