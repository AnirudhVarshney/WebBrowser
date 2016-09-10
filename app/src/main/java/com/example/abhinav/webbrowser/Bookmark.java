package com.example.abhinav.webbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Bookmark extends AppCompatActivity {
    RecyclerView mRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        mRecycler= (RecyclerView) findViewById(R.id.rv_recycler_bookmark);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        AdapterBookmark mAdapter = new AdapterBookmark(this);
        mRecycler.setAdapter(mAdapter);
    }
}
