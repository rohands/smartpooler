package com.example.rohan.smartpool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    EditText firstName,lastName,usn,phNo,email,password;
    Button signUp;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstName = (EditText) findViewById(R.id.input_first_name);
        lastName = (EditText) findViewById(R.id.input_last_name);
        usn = (EditText) findViewById(R.id.input_usn);
        phNo = (EditText) findViewById(R.id.input_phno);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        dialog = new ProgressDialog(this);
        signUp = (Button) findViewById(R.id.btn_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostData().execute(firstName.getText().toString(), lastName.getText().toString(), usn.getText().toString(),
                        phNo.getText().toString(), email.getText().toString(), password.getText().toString());
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
            Toast.makeText(SignupActivity.this, s, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... params) {

            String text ="";
            try {

                String data = URLEncoder.encode("first_name", "UTF-8") + "=" + params[0];
                data += "&" + URLEncoder.encode("last_name","UTF-8") + "=" + params[1];
                data += "&" + URLEncoder.encode("usn","UTF-8") + "=" + params[2];
                data += "&" + URLEncoder.encode("phno","UTF-8") + "=" + params[3];
                data += "&" + URLEncoder.encode("email","UTF-8") + "=" + params[4];
                data += "&" + URLEncoder.encode("password","UTF-8") + "=" + params[5];
//                String data = URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode("Rohan","UTF-8");
//                data += "&" + URLEncoder.encode("last_name","UTF-8") + "=" + URLEncoder.encode("Doddaiah","UTF-8");
//                data += "&" + URLEncoder.encode("usn","UTF-8") + "=" + "1PI13CS125";
//                data += "&" + URLEncoder.encode("phno","UTF-8") + "=" + "9986180939";
//                data += "&" + URLEncoder.encode("email","UTF-8") + "=" + "rohan0495@gmail.com";
//                data += "&" + URLEncoder.encode("password","UTF-8") + "=" + "madhurima101";

                URL url = new URL(LoginActivity.BASE_URL + "signup/");
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

}
