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

public class RegisterActivity extends AppCompatActivity {
    DbHelper dbhelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonBack =(Button) findViewById(R.id.registerButtonBack);
        Button buttonRegister =(Button) findViewById(R.id.registerButton);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backHome(v);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText = (EditText) findViewById(R.id.registerName);
                EditText emailText = (EditText) findViewById(R.id.registerEmail);
                EditText passwordText = (EditText) findViewById(R.id.registerPassword);
                EditText confirmPasswordText = (EditText) findViewById(R.id.registerPasswordConfirm);

                String name = nameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String passordConfirm = confirmPasswordText.getText().toString();

                if(!isValidEmail(email)){
                    Toast invalid_mail = Toast.makeText(RegisterActivity.this,"Email pattern does not match!", Toast.LENGTH_SHORT);
                    invalid_mail.show();
                } else if(!password.equals(passordConfirm)){
                    Toast pw_unmatched = Toast.makeText(RegisterActivity.this,"Password confirmation does not match", Toast.LENGTH_SHORT);
                    pw_unmatched.show();
                } else if(name.length()==0 || email.length()==0 || password.length()==0){
                    Toast empty_field = Toast.makeText(RegisterActivity.this,"Fill up the empty fields", Toast.LENGTH_SHORT);
                    empty_field.show();
                } else{
                    if(!dbhelper.emailExists(email)) {
                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setTimestamp(dbhelper.getDateTime());

                        dbhelper.insertUser(user);

                        Toast success = Toast.makeText(RegisterActivity.this, "User registration successful!", Toast.LENGTH_SHORT);
                        success.show();

                        Intent intent = new Intent(RegisterActivity.this, AuthorizedActivity.class);

                        intent.putExtra("name", name);
                        intent.putExtra("newUser", "true");
                        startActivity(intent);
                    }
                    else{
                        Toast email_exists = Toast.makeText(RegisterActivity.this,"Email already exists!", Toast.LENGTH_SHORT);
                        email_exists.show();
                    }


                }

            }
        });

    }//onCreateEnds


    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
