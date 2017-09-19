package com.example.rakib.trlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AuthorizedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);
        Intent intent = getIntent();

        TextView greetingsText =  (TextView) findViewById(R.id.authorized_tv_hello_user);
        TextView timestampText =  (TextView) findViewById(R.id.authorized_tv_timestamp);
        Button buttonHome =  (Button) findViewById(R.id.buttonAuthorizedGoHome);

        String user = intent.getStringExtra("name");

        String hellouser = "Hello\n" + user + "!";
        greetingsText.setText(hellouser);

        if(intent.hasExtra("newUser")){
            timestampText.setText("Registration completed successfully!");
        }
        else {

            String time = intent.getStringExtra("time");
            String lastloggedin = "Your last log in was on\n" + time;
            timestampText.setText(lastloggedin);
        }


        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backHome(v);
            }
        });


    }//onCreate


    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
