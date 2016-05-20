package com.example.rohan.smartpool;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

public class JoinActivity extends AppCompatActivity {

    int flag,year,month,day;
    ImageView calendar2;
    Calendar calendar;
    EditText source,destination,baggage;
    String startDate_;
    TimePicker startTime;
    ProgressDialog dialog;
    Button joinRide;
    TextView currentView,startDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPreferences = getSharedPreferences(SmartActivity.MyPREFERENCES, MODE_PRIVATE);
        final String usn = sharedPreferences.getString("usn", "");
        if (usn.equals("")) {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT);
            startActivity(new Intent(JoinActivity.this, LoginActivity.class));
        }
        Log.e("usn",usn);

        source = (EditText) findViewById(R.id.input_src);
        destination = (EditText) findViewById(R.id.input_dest);
        dialog = new ProgressDialog(this);
        baggage = (EditText) findViewById(R.id.input_baggage);
        startDate = (TextView) findViewById(R.id.tvStartDate);
        calendar = Calendar.getInstance();
        calendar2 = (ImageView) findViewById(R.id.calendar2);
        year = calendar.get(Calendar.YEAR);
        joinRide = (Button) findViewById(R.id.btn_joinride);
        month = calendar.get(Calendar.MONTH);
        startTime = (TimePicker) findViewById(R.id.startTimePicker);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                currentView = startDate;
                setDate(startDate);

            }
        });
        joinRide.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String startDateTime = startDate_ + " " + updateTime(startTime.getCurrentHour(), startTime.getCurrentMinute());
                new PostData().execute(source.getText().toString(), destination.getText().toString(), startDateTime, baggage.getText().toString()
                        , usn);
            }
        });
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
            Toast.makeText(JoinActivity.this, s, Toast.LENGTH_LONG).show();
            Log.e("d",s);
            Intent intent = new Intent(JoinActivity.this,ListRoutesActivity.class);
            intent.putExtra("Response",s);
            startActivity(intent);

        }

        @Override
        protected String doInBackground(String... params) {

            String text ="";
            try {

                String data = URLEncoder.encode("source", "UTF-8") + "=" + params[0];
                data += "&" + URLEncoder.encode("destination","UTF-8") + "=" + params[1];
                data += "&" + URLEncoder.encode("startDateTime","UTF-8") + "=" + params[2];
                data += "&" + URLEncoder.encode("baggage","UTF-8") + "=" + params[3];
                data += "&" + URLEncoder.encode("usn","UTF-8") + "=" +params[4] ;


                URL url = new URL(LoginActivity.BASE_URL + "join_ride/");
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

        private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(timeSet).toString();
        return  aTime;
    }
    private void showDate(int year, int month, int day) {
        if(flag == 1)
            startDate_ = new StringBuilder().append(month).append(" ").append(day).append(" ").append(year).toString();
        currentView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

}
