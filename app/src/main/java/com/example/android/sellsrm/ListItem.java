package com.example.android.sellsrm;

import static com.example.android.sellsrm.R.id.bookName;

/**
 * Created by satyam on 16/06/17.
 */

public class ListItem {

   private String bookName;
   private String bookDesp;
   private int price;
   private int year;
   private String imageurl;

    public ListItem(String bookName, String bookDesp, int price, int year, String imageurl) {
        this.bookName = bookName;
        this.bookDesp = bookDesp;
        this.price = price;
        this.year = year;
        this.imageurl = imageurl;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookDesp() {
        return bookDesp;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getImageurl() {
        return imageurl;
    }
}
