package com.example.rohan.smartpool;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

/**
 * Created by rohan on 19/4/16.
 */
public class Signup extends Activity {

    EditText password;
    EditText fname;
    EditText lname;
    EditText usn;
    EditText email;
    EditText phno;
    Button signupButton;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.signup_smart);

        usn = (EditText) findViewById(R.id.input_usn);
        password = (EditText) findViewById(R.id.input_password);
        fname = (EditText) findViewById(R.id.input_first_name);
        lname = (EditText) findViewById(R.id.input_last_name);
        email = (EditText) findViewById(R.id.input_email);
        phno = (EditText) findViewById(R.id.input_phno);
        signupButton = (Button) findViewById(R.id.btn_signup);
        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usn.getText().toString();
                String passwd = password.getText().toString();
                String finame = fname.getText().toString();
                String laname = lname.getText().toString();
                String emai = email.getText().toString();
                String ph = phno.getText().toString();
                new PostData().execute(username,passwd,finame,laname,emai,ph);
            }
        });



    }


    private class PostData extends AsyncTask<String,String,String>
    {

        BufferedReader reader=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String text ="";
            StringBuilder sb;
            try {

                String data = URLEncoder.encode("usn", "UTF-8") + "=" + URLEncoder.encode(params[0],"UTF-8");
                data += "&" + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(params[1],"UTF-8");
                data += "&" + URLEncoder.encode("fname","UTF-8") + "=" + URLEncoder.encode(params[2],"UTF-8");
                data += "&" + URLEncoder.encode("lname","UTF-8") + "=" + URLEncoder.encode(params[3],"UTF-8");
                data += "&" + URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(params[4],"UTF-8");
                data += "&" + URLEncoder.encode("phno","UTF-8") + "=" + URLEncoder.encode(params[5],"UTF-8");
                URL url = new URL("http://192.168.1.101:8000/smartpool/mobile_apptest/");
                Log.e("url", url.toString());
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
            Toast.makeText(Signup.this, s, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}
