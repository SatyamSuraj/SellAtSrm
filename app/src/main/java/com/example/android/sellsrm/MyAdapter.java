package com.example.android.sellsrm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.start;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by satyam on 16/06/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ListItem> listItems;
    private Context context;
    private ArrayList<String> names;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);
        holder.textViewBname.setText("Name: "+listItem.getBookName());
        holder.textViewBdesp.setText("Desp: "+listItem.getBookDesp());
        holder.textViewBprice.setText("Price: "+String.valueOf(listItem.getPrice()));
        holder.textViewByear.setText("Year: "+String.valueOf(listItem.getYear()));

        Picasso.with(context)
                .load(listItem.getImageurl())
                .into(holder.imageViewimgUrl);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,product.class);
                i.putExtra("bname",listItem.getBookName());
                i.putExtra("bdesp",listItem.getBookDesp());
                i.putExtra("bprice",String.valueOf(listItem.getPrice()));
                i.putExtra("byear",String.valueOf(listItem.getYear()));
                i.putExtra("burl",listItem.getImageurl());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewBname;
        public TextView textViewBdesp;
        public TextView textViewBprice;
        public TextView textViewByear;
        public ImageView imageViewimgUrl;
        public LinearLayout linearLayout;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewBname = (TextView)itemView.findViewById(R.id.bookName);
            textViewBdesp = (TextView)itemView.findViewById(R.id.bookDescription);
            textViewBprice = (TextView)itemView.findViewById(R.id.bookPrice);
            textViewByear = (TextView)itemView.findViewById(R.id.year);
            imageViewimgUrl = (ImageView)itemView.findViewById(R.id.image);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
            button = (Button)itemView.findViewById(R.id.buyButton);

        }
    }


}
