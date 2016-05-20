package com.example.rohan.smartpool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRoutesActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    public static ArrayList<String> names = new ArrayList();
    public static  ArrayList<String> sources = new ArrayList<>();
    public static ArrayList<String> dests = new ArrayList<>();
    public static ArrayList<String> dists = new ArrayList<>();
    public static ArrayList<String> src_lat = new ArrayList<>();
    public static ArrayList<String> dest_lat = new ArrayList<>();
    public static ArrayList<String> src_lng = new ArrayList<>();
    public static ArrayList<String> dest_lng = new ArrayList<>();
    public static ArrayList<String> usns = new ArrayList<>();

    ListView listView;
    List<RowItem> rowItems;
    ProgressDialog dialog;
    int clicked_position;
    String src_lat_,src_lng_,dest_lat_,dest_lng_;NotificationManager manager;
    Notification myNotication;
    String usn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_routes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final SharedPreferences sharedPreferences = getSharedPreferences(SmartActivity.MyPREFERENCES,MODE_PRIVATE);
        usn = sharedPreferences.getString("usn","");




        JSONObject jsonObject = null;
        //String s = "{\"1\": {\"lname\": \"Doddaiah\", \"source\": \"Koramangala\", \"destination\": \"Whitefield, Bangalore\", \"fname\": \"Rohan\", \"distance\": 19.7}}";
        String s = getIntent().getStringExtra("Response");
        try {
            jsonObject = new JSONObject(s.trim());
            Iterator<?> keys = jsonObject.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                if ( jsonObject.get(key) instanceof JSONObject ) {
                    Log.e("d", String.valueOf(jsonObject.get(key)));
                    JSONObject temp = new JSONObject(String.valueOf(jsonObject.get(key)));
                    names.add(temp.getString("fname"));
                    sources.add(temp.getString("source"));
                    dests.add(temp.getString("destination"));
                    dists.add(temp.getString("distance"));
                    src_lat.add(temp.getString("src_lat"));
                    dest_lat.add(temp.getString("dest_lat"));
                    src_lng.add(temp.getString("src_lng"));
                    dest_lng.add(temp.getString("dest_lng"));
                    usns.add(temp.getString("offer_usn"));
                    src_lat_ = temp.getString("poolee_src_lat");
                    src_lng_ = temp.getString("poolee_src_lng");
                    dest_lat_ = temp.getString("poolee_dest_lat");
                    dest_lng_ = temp.getString("poolee_dest_lng");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < names.size(); i++) {
            RowItem item = new RowItem(sources.get(i),dests.get(i),dists.get(i),names.get(i));
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.item, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);




    }

    private class PostData extends AsyncTask<String,String,String>
    {
        BufferedReader reader = null;
        StringBuilder sb;

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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(ListRoutesActivity.this, s, Toast.LENGTH_LONG).show();
            PendingIntent pending=PendingIntent.getActivity(getApplicationContext(), 0, new Intent(),0);
            Notification.Builder builder = new Notification.Builder(ListRoutesActivity.this);

            builder.setAutoCancel(false);
            builder.setTicker("this is ticker text");
            builder.setContentTitle("Share Update");
            if(Integer.valueOf(s.charAt(s.length() - 1)) > 0)
            {
                if(Integer.valueOf(s.charAt(s.length() - 1)) == 1)
                {
                    builder.setContentText("Say HI to your co-passenger " + s.substring(0,s.length()-2));
                }
                else
                {
                    builder.setContentText("Say HI to your co-passengers " + s.substring(0,s.length()-2));
                }
            }
            else
                builder.setContentText("Congratulations you have successfully shared a ride!");
            builder.setSmallIcon(R.drawable.ic_logo);
            builder.setContentIntent(pending);
            builder.setOngoing(true);
            builder.setSubText("Navigate yourself, have fun!");   //API level 16
            builder.setNumber(100);
            builder.build();

            myNotication = builder.getNotification();
            manager.notify(11, myNotication);

            String uri = "http://maps.google.com/maps?saddr="+src_lat.get(clicked_position)+","+src_lng.get(clicked_position)+"&daddr="+src_lat_+","+src_lng_+
                    "+to:"+dest_lat.get(clicked_position)+","+dest_lng.get(clicked_position);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);

            /*http://maps.google.com/maps?saddr=[lat 1],[lon 1]&daddr=[lat 2],
  [lon 2]+to:[lat 3],[lon 3]+to:[lat 4],[lon 4]*/

        }

        @Override
        protected String doInBackground(String... params) {

            String text ="";
            try {

                String data = URLEncoder.encode("usn", "UTF-8") + "=" + params[0];
                data += "&" + URLEncoder.encode("src_lat","UTF-8") + "=" + params[1];
                data += "&" + URLEncoder.encode("src_lng","UTF-8") + "=" + params[2];

                data += "&" + URLEncoder.encode("dest_lat","UTF-8") + "=" + params[3];
                data += "&" + URLEncoder.encode("dest_lng","UTF-8") + "=" + params[4];
                data += "&" + URLEncoder.encode("myUsn","UTF-8") + "=" + params[5];

                URL url = new URL(LoginActivity.BASE_URL + "add_waypoints/");
                Log.e("url", url.toString());
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
                text = "";
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String line;

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
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.e("position", String.valueOf(position));
        clicked_position = position;

        new PostData().execute(usns.get(position), src_lat_, src_lng_, dest_lat_, dest_lng_,usn);
    }
}
