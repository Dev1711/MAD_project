package com.example.book_mad;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class QueryResultsActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Book>> {

    private String REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    private static final String API_KEY = "AIzaSyCaNgg0GLoPlz75osYA3mDIYG0rWAZo01s";

    private BookAdapter mAdapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private ProgressBar mProgressSpinner;

    private TextView mEmptyStateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_of_books);
        BookRecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        int orientation = this.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        mAdapter = new BookAdapter(new ArrayList<Book>());


        recyclerView.setAdapter(mAdapter);


        mEmptyStateView = findViewById(R.id.empty_text_view);
        recyclerView.setEmptyView(mEmptyStateView);

        mProgressSpinner = findViewById(R.id.progress_spinner1);

        mProgressSpinner.setIndeterminate(true);


        Intent queryIntent = getIntent();

        String searchText = getIntent().getStringExtra("topic");

        String processedQuery = "";

        String title = queryIntent.getStringExtra("title");

        String author = queryIntent.getStringExtra("author");

        String isbn = queryIntent.getStringExtra("isbn");
        if (title != null) {

            processedQuery = searchText + "&" + title + searchText;
        } else if (author != null) {

            processedQuery = searchText + "&" + author + searchText;
        } else if (isbn != null) {

            processedQuery = searchText + "&" + isbn + searchText;
        } else {

            processedQuery = searchText;
        }
        REQUEST_URL += processedQuery + "&maxResults=40" + "&key=" + API_KEY;


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {

            android.app.LoaderManager loaderManager = getLoaderManager();


            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {

            mProgressSpinner.setVisibility(View.GONE);

            mEmptyStateView.setText(R.string.no_interest_connection);
        }
    }


    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(QueryResultsActivity.this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mProgressSpinner.setVisibility(View.GONE);
        mEmptyStateView.setText(R.string.no_books);
        mAdapter.clear();
        if(books != null && !books.isEmpty()){
            mAdapter.addAll(books);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();

    }
}
