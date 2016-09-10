package com.example.abhinav.webbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ABHINAV on 02-07-2016.
 */
public class AdapterBookmark  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater minflater;
    Context con;
    List<Bookamark_model> mResults;
    public AdapterBookmark(Context context) {

        minflater = LayoutInflater.from(context);
        con = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.row_bookmark, parent, false);
        return new BookmarkHol(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Bookamark_model bookamark_model=mResults.get(position);
        BookmarkHol bokHolder = (BookmarkHol) holder;
        bokHolder.mTextBookmark.setText(bookamark_model.getUrl());
    }


    @Override
    public int getItemCount() {
        DatabaseHandler database=new DatabaseHandler(con);

        mResults=  database.getAllBookmarks(); //getting all rows from the table and storing in the arraylist
        return mResults.size();
    }
    private class BookmarkHol extends RecyclerView.ViewHolder {
        TextView mTextBookmark;
        public BookmarkHol(View itemView) {
            super(itemView);
            mTextBookmark= (TextView) itemView.findViewById(R.id.bookmarktext);

        }
    }
}
