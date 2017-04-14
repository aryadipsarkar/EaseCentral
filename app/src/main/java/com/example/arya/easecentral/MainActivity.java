package com.example.arya.easecentral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by arya's on 4/10/2017.
 */
public class MainActivity extends AppCompatActivity {

    Button btnLogout;
    Session session;
    String json_url="https://www.reddit.com/hot.json";
    DbHelper dbfav;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbfav=new DbHelper(this);
        setTitle("My new title");
        //when the user leaves the main page, call the logout()function to end his/her session
        session= new Session(this);
        if(!session.loggedin()){
            logout();
        }

        //Clicking on the Logout button starts the logout function
        btnLogout= (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        // Clicking on the Favourites button starts a separate intent which take to another page which display the Favorites
        Button btn= (Button) findViewById(R.id.btnFav);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,DisplayFavourites.class);
                startActivity(intent);
            }
        });

    }

    /**
     * the logout function which is the interact with the database to clear the tables in the database when the user logs out
     * to avoid redundancy and unnecessary storage of information
     * Also it sets the 'session' as false as the ongoing session is only meant for the user currently logged in
     * It also starts an intent which takes the user back to the login page
     */
    private void logout() {
        session.setLoggedin(false);
        dbfav.deleteAll();
        finish();
        Intent intent= new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    /**
     * the parse function which lands the user to the display page (intent is fired)
     * parameter -> link to the JSON which is passed an extra with the intent
     */
    public void parseJSON(View view){
        Intent intent= new Intent(this,DisplayListView.class);
        intent.putExtra("json_data",json_url);
        startActivity(intent);
    }
}
