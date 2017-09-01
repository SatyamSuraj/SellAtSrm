package com.example.android.sellsrm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by satyam on 29/06/17.
 */

public class MyAdapter_user extends RecyclerView.Adapter<MyAdapter_user.ViewHolder> {

    private List<ListItem_user> listItems_user;
    private Context context;

    public MyAdapter_user(List<ListItem_user> listItems_user, Context context) {
        this.listItems_user = listItems_user;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListItem_user listItem = listItems_user.get(position);
        holder.textViewBname.setText(listItem.getBookName());
        holder.textViewBdesp.setText(listItem.getBookDesp());
//        holder.textViewBprice.setText(listItem.getPrice());
//        holder.textViewByear.setText(listItem.getYear());
        holder.textViewBprice.setText(String.valueOf(listItem.getPrice()));
        holder.textViewByear.setText(String.valueOf(listItem.getYear()));

        Picasso.with(context)
                .load(listItem.getImageurl())
                .into(holder.imageViewimgUrl);
    }

    @Override
    public int getItemCount() {
        return listItems_user.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewBname;
        public TextView textViewBdesp;
        public TextView textViewBprice;
        public TextView textViewByear;
        public ImageView imageViewimgUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewBname = (TextView)itemView.findViewById(R.id.bookName);
            textViewBdesp = (TextView)itemView.findViewById(R.id.bookDescription);
            textViewBprice = (TextView)itemView.findViewById(R.id.bookPrice);
            textViewByear = (TextView)itemView.findViewById(R.id.year);
            imageViewimgUrl = (ImageView)itemView.findViewById(R.id.image);

        }

    }


}


