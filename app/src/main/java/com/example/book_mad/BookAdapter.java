package com.example.book_mad;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.CardViewHolder> {

    private final String LOG_TAG  = BookAdapter.class.getSimpleName();
    private List<Book> mListOfBooks;

    BookAdapter(List<Book> listOfBooks) {
        this.mListOfBooks = listOfBooks;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card, parent, false);


        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        Book currentBook = mListOfBooks.get(position);


        holder.bookTitle.setText(currentBook.getTitle());

        try {
            String authors = currentBook.getAuthor();


            if (!authors.isEmpty()) {

                holder.bookAuthor.setText(authors);
            }
        } catch (NullPointerException e) {

            Log.v(LOG_TAG, "No information on authors");


            holder.bookAuthor.setVisibility(View.INVISIBLE);
        }
        holder.bookRating.setRating(currentBook.getRating());


        String price = "";
        if (currentBook.getPrice() > 0) {

            price = "$" + currentBook.getPrice();

            holder.bookPrice.setText(price);
        }
    }
    @Override
    public int getItemCount() {
        return mListOfBooks.size();
    }


    void clear() {
        mListOfBooks = new ArrayList<>();
    }


    void addAll(List<Book> data) {

        for (int i = 0; i < data.size(); i++) {

            Book book = data.get(i);

            mListOfBooks.add(book);

            notifyDataSetChanged();
        }
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView bookTitle;


        TextView bookAuthor;


        RatingBar bookRating;


        TextView bookPrice;

        CardViewHolder(View itemView) {
            super(itemView);


            bookTitle = itemView.findViewById(R.id.book_title_text_view);


            bookAuthor = itemView.findViewById(R.id.author_text_view);


            bookRating = itemView.findViewById(R.id.rating_bar);

            bookRating.setMax(5);
            bookRating.setNumStars(5);

            Drawable progress = bookRating.getProgressDrawable();
            DrawableCompat.setTint(progress, Color.YELLOW);


            bookPrice = itemView.findViewById(R.id.retail_price_text_view);
        }
    }


    }
