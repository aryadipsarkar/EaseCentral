package com.example.arya.easecentral;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import static com.example.arya.easecentral.R.id.list_view;
/**
 * Created by arya's on 4/10/2017.
 */
public class DisplayFavourites extends AppCompatActivity {
    /**
     * This class provides all the data (selected by the user as “favorites” ) and shoots
     * the data to the other classes and database for visualization purposes
     */
    ListView listView;
    DbHelper db;
    SQLiteDatabase sqlLiteDatabase;
    Cursor cursor;
    DataProviderToFavAdapter  listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_favourites_layout);
        listView= (ListView) findViewById(list_view);
        listAdapter= new DataProviderToFavAdapter(getApplicationContext(),R.layout.display_favourites_layout);
        listView.setAdapter(listAdapter);
        db=new DbHelper(getApplicationContext());
        sqlLiteDatabase=db.getReadableDatabase();
        /**
         * reading the data from the database which the user has selected as favourites and
         * pushing that data to 'DataProviderToFavs.java' class as an object which in turn takes the data,
         * and sets the image, title, comments and URl to the respective positions
          */
        cursor= db.getInformation(sqlLiteDatabase);
        if (cursor.moveToFirst()){
            do{
                String image ,title, url, comments;
                image=cursor.getString(0);
                title=cursor.getString(1);
                url=cursor.getString(2);
                comments=cursor.getString(3);
                DataProviderToFavs dataProvider = new DataProviderToFavs(image,title, url, comments);
                listAdapter.add(dataProvider);
            }while (cursor.moveToNext());
        }
    }
}
