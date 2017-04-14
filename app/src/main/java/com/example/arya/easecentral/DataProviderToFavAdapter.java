package com.example.arya.easecentral;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arya's on 4/10/2017.
 */
public class DataProviderToFavAdapter extends ArrayAdapter {
    List list= new ArrayList();

    /**
     * this is the Adapter class which sets the data which is selected
     * by the user as “favorites” from the Reddit reader ,in the list-view of the app’s screen
     * this provides the rendering functionality to the same
     * @param context
     * @param resource
     */
    public DataProviderToFavAdapter(Context context, int resource) {
        super(context, resource);
    }

    /**
     * the add() function is the one which the takes the object from displayFavourites() class
     * (the image link, comments, title and URL are bundled as an object and sent to this class.
     * the object is then pushed into the array list for further processing
     * @param object
     */
    public  void add(Object object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row=convertView;
        if (row==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= layoutInflater.inflate(R.layout.favourites_row_layout,parent,false);
        }

        DataProviderToFavs data= (DataProviderToFavs) this.getItem(position);


        String img_url= data.getImage();
        ImageView image= (ImageView) row.findViewById(R.id.t_image);

        // using picasso to render the image into the corresponding image view
        Picasso.with(getContext())
                .cancelRequest(image);
        Picasso.with(getContext())
                .load(img_url)
                .placeholder(R.drawable.noimage)
                .into(image);

        //rendering the titles ,URLs and comments in the respective views
        TextView title=(TextView) row.findViewById(R.id.t_title);
        title.setText(data.getTitle());
        title.setTypeface(null, Typeface.BOLD);

        TextView url=(TextView) row.findViewById(R.id.t_url);
        url.setText(data.getUrl());

        TextView comments=(TextView) row.findViewById(R.id.t_comments);
        comments.setText(data.getComments());
        return row;
    }

}