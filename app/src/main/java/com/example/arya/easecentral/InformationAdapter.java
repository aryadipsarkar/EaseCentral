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
public class InformationAdapter extends ArrayAdapter {
    /**
     * This is the Adapter class which sets the parsed data which from the Reddit JSON in the list view of the app’s screen
     */
    List list = new ArrayList();
    Information information;

    public InformationAdapter(Context context, int resource) {
        super(context,resource);
    }

    public void add(Information object) {
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
    public long getItemId(int i) {
        return i;
    }

    class MyViewHolder{
        ImageView image;
        TextView title;
        TextView url;
        TextView comments;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        MyViewHolder holder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            holder= new MyViewHolder();
            holder.image= (ImageView) row.findViewById(R.id.tx_image);
            holder.title= (TextView) row.findViewById(R.id.tx_title);
            holder.url= (TextView) row.findViewById(R.id.tx_url);
            holder.comments= (TextView) row.findViewById(R.id.tx_comments);
            row.setTag(holder);
        }else{
            holder= (MyViewHolder) row.getTag();
        }

        information = (Information) this.getItem(position);

        // using picasso to render the image into the corresponding image view
        String img_url= information.getImage();
        Picasso.with(getContext())
                .cancelRequest(holder.image);
        Picasso.with(getContext())
                .load(img_url)
                .placeholder(R.drawable.noimage)
                .into(holder.image);

        //rendering the titles ,URLs and comments in the respective views
        holder.title.setText(information.getTitle());
        holder.title.setTypeface(null, Typeface.BOLD);
        holder.url.setText(information.getUrl());
        holder.comments.setText("Comments:" + "\n" + information.getComments());
        return row;
    }
}