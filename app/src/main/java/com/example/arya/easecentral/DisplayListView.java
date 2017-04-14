package com.example.arya.easecentral;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Created by arya's on 4/10/2017.
 */
public class DisplayListView extends AppCompatActivity {
    /**
     * This class parses the JSON data from the given JSON link which is passed by the MainActivity.java class and
     * it shoots the data to the other classes and database for visualization purposes
     */
    String json_URL;
    InformationAdapter informationAdapter;
    ListView listView;
    DbHelper dbFavs;
    ArrayList<String> bundleOfLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listview_layout);
        informationAdapter= new InformationAdapter(this,R.layout.row_layout);
        dbFavs= new DbHelper(this);
        listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(informationAdapter);
        bundleOfLinks= new ArrayList<>();
        json_URL=getIntent().getExtras().getString("json_data");

        /**
         * if the user clicks on any view of the list view, that view's data will be sent to the database for
         * furthering rendering it to the favourites page
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String title = ((TextView) view.findViewById(R.id.tx_title)).getText().toString();
                String url = ((TextView) view.findViewById(R.id.tx_url)).getText().toString();
                String comments = ((TextView) view.findViewById(R.id.tx_comments)).getText().toString();
                if (!dbFavs.ifItemExists(url)){
                    dbFavs.addUserFavs(position,title,url,comments);
                }else{
                    Toast.makeText(getApplicationContext(), "Already Added to Favourites",Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * parsing the JSON data using Android Volley Library
         */
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                json_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject contacts= response.getJSONObject("data");
                    JSONArray children= contacts.getJSONArray("children");

                    for (int i = 0; i < children.length(); i++) {
                        JSONObject eachChildrenObject= children.getJSONObject(i);
                        JSONObject eachData= eachChildrenObject.getJSONObject("data");
                        String thumbnail;
                        thumbnail=eachData.getString("thumbnail");
                        String title=eachData.getString("title");
                        String url=eachData.getString("url");
                        int com=eachData.getInt("num_comments");

                        if (!dbFavs.checkForTables()){
                            bundleOfLinks.add(thumbnail);
                        }
                        String comments=Integer.toString(com);
                        Information information= new Information(thumbnail,title,url,comments);
                        informationAdapter.add(information);
                    }
                    dbFavs.addLinks(bundleOfLinks);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
