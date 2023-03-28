package com.example.book_mad;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mSearchUrl;
    private List<Book> mData;

    BookLoader(Context context, String url) {
        super(context);
        mSearchUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Book> loadInBackground(){
        if (mSearchUrl == null){
            return null;
        }
        return QueryUtils.fetchBooks(mSearchUrl);
    }

    @Override
    public void deliverResult(List<Book> data){
        mData = data;
        super.deliverResult(data);
    }

}
