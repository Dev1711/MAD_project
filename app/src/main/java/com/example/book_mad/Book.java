package com.example.book_mad;

public class Book {
    private String mTitle;
    private String mAuthors;
    private float mRating;
    private float mPrice;

    Book(String title, String authors, float rating, float price){
        this.mTitle = title;
        this.mAuthors = authors;
        this.mRating = rating;
        this.mPrice = price;
    }

    String getTitle() {return mTitle;}
    String getAuthor() {return mAuthors;}
    float getRating() {return mRating;}
    float getPrice() {return mPrice;}


}
