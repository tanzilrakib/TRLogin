package com.example.rakib.trlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    DbHelper dbhelper = new DbHelper(this);

    RegisterActivity validator = new RegisterActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = (Button) findViewById(R.id.mainButtonLogin);
        Button buttonRegister = (Button) findViewById(R.id.mainButtonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRegister(v);
            }
         });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.main_login_email);
                EditText passwordText = (EditText) findViewById(R.id.main_login_password);
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if(email.length()==0 || password.length()==0){
                    Toast empty_field = Toast.makeText(MainActivity.this,"Fill up the empty fields", Toast.LENGTH_SHORT);
                    empty_field.show();
                } else if(!validator.isValidEmail(email)){
                    Toast invalid_mail = Toast.makeText(MainActivity.this,"Email pattern does not match!", Toast.LENGTH_SHORT);
                    invalid_mail.show();
                }
                else{
                    if(dbhelper.userExists(email,password)){

                        String userName = dbhelper.getUserName(email);
                        String lastLogin = dbhelper.getLastLogin(email);
                        dbhelper.setLastLogin(email,dbhelper.getDateTime());

                        Toast q = Toast.makeText(MainActivity.this, "Logged in Successfully!"  , Toast.LENGTH_SHORT);
                        q.show();

                        authorizedActivity(v, userName, lastLogin);
                    }

                    else{

                            Toast unmatched_cred = Toast.makeText(MainActivity.this, "Credentials do not match"  , Toast.LENGTH_SHORT);
                            unmatched_cred.show();

                    }
                }

            }
         });



    }//onCreateEnds


    public void startRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void authorizedActivity(View view, String UserName, String TimeStamp) {
        Intent intent = new Intent(this, AuthorizedActivity.class);
        intent.putExtra("name", UserName);
        intent.putExtra("time", TimeStamp);
        startActivity(intent);
    }

}
