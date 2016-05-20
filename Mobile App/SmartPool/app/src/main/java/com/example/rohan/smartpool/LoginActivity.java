package com.example.rohan.smartpool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    TextView linkToSignUp;
    EditText usn,password;
    Button loginButton;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    public static final String BASE_URL = "http://192.168.43.49:8080/smartpool/";
    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences =  getSharedPreferences(SmartActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        usn = (EditText) findViewById(R.id.input_usn);
        password = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        linkToSignUp = (TextView) findViewById(R.id.link_signup);
        dialog = new ProgressDialog(this);
        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostData().execute(usn.getText().toString(), password.getText().toString());
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
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();

            if(s.contains("User logged in"))
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SmartActivity.logIn, true);
                editor.putString("usn", usn.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this,SmartActivity.class);
                startActivity(intent);
            }
            else
            {
                //Toast.makeText(LoginActivity.this,"Please enter the right credentials",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            String text ="";
            try {

                String data = URLEncoder.encode("usn", "UTF-8") + "=" + params[0];
                data += "&" + URLEncoder.encode("password","UTF-8") + "=" + params[1];

                URL url = new URL(BASE_URL + "login/");
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
