package com.example.arya.easecentral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by arya's on 4/10/2017.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * This class provide the login functionality of the app
     * It also checks with the database whether the username and the password
     * entered matches or they exist or not and prompts the user for the same
     */
    EditText etUser;
    EditText etPass;
    DbHelper db;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser= (EditText) findViewById(R.id.etUser);
        etPass= (EditText) findViewById(R.id.etPass);
        db= new DbHelper(this);
        session= new Session(this);
        final Button login= (Button) findViewById(R.id.btnLogin);
        Button register= (Button) findViewById(R.id.btnReg);

        //check the logging in status of the application whether someone has logged in or not
        if (session.loggedin()){
            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        //when the logon button is clicked, login() function is called
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //when register button is pressed, it will route to the Register page which allows the user to register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * the login function checks whether the entered username and password is there in the database
     * and accordingly it will allow entry
     * if allowed entry , it will start another activity which will take to the MainActivity.java class which will
     * start the Reddit Reader
     */
    public  void login(){
        String username= etUser.getText().toString();
        String password= etPass.getText().toString();

        if (db.getUser(username,password)){
            session.setLoggedin(true);
            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Wrong credentials",Toast.LENGTH_SHORT).show();
        }
    }
}