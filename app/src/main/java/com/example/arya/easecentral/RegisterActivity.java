package com.example.arya.easecentral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by arya's on 4/10/2017.
 */
public class RegisterActivity extends AppCompatActivity {
    DbHelper db;
    EditText etUser;
    EditText etPass;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUser= (EditText) findViewById(R.id.etUser);
        etPass= (EditText) findViewById(R.id.etPass);
        tvLogin= (TextView) findViewById(R.id.tvLogin);
        db= new DbHelper(this);
        Button reg= (Button) findViewById(R.id.btnReg);
        /**
         *  Clicking on the Register button registers the user and fires the register() function which is responsible for the
         *  validity of the entered values as well as registering the user with the database
         */

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        /**
         * Clicking on the "back to Login" text-view fires an intent which takes the user back to login screen
         */
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //starting a intent to take the user back to the login page
                Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

            /**
             * The register() function which validates the entered credentials and interact with the database to register the user
             */
            public void register(){
                    String username= etUser.getText().toString();
                    String password = etPass.getText().toString();

                    //validating the credentials
                    if (username.isEmpty()|| password.isEmpty() ){
                        Toast.makeText(getApplicationContext(), "Empty Fields",Toast.LENGTH_SHORT).show();
                    }else{
                        //registering with the database
                        db.addUser(username,password);
                        Toast.makeText(getApplicationContext(), "User Registered",Toast.LENGTH_SHORT).show();
                    }
                }
            }
