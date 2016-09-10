package com.example.abhinav.webbrowser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABHINAV on 02-07-2016.
 */
public class AdapterHistory  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater minflater;
    Context con;
    List<History_model> mResults;
    ArrayList<Integer> Selectedpositions=new ArrayList<>();
ArrayList<String> listurls;
    public AdapterHistory(Context context,List<History_model> Results) {

        minflater = LayoutInflater.from(context);
        mResults=Results;
        con = context;
      //  notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.row_history, parent, false);
        return new HistoryHol(view);
    }
    public void remove(int position,String time){

        DatabaseHandler database=new DatabaseHandler(con);
        database.clearSelectedHistory(time);
//        for(int i=0;i<positions.size();i++){
//            mResults.remove(positions.get(i).intValue());
//
//            Log.d("ani",positions.get(i)+"in remove");
//        }
//        for(int i=0;i<positions.size();i++){
//            notifyItemRemoved(positions.get(i).intValue());
//
          Log.d("ani",position+"in remove");
//        }

mResults.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final History_model history_model=mResults.get(position);
        HistoryHol hisHolder = (HistoryHol) holder;
        hisHolder.mTextView.setText(history_model.getUrl());
        hisHolder.mTime.setText(history_model.getTime());
        hisHolder.checkBox.setChecked(history_model.isSelected());
        hisHolder.checkBox.setTag(history_model);
        hisHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                CheckBox cb= (CheckBox) v;
                History_model his= (History_model) cb.getTag();
                his.setSelected(cb.isChecked());
                history_model.setSelected(cb.isChecked());
                Selectedpositions.add(position);
               // Toast.makeText(v.getContext(),"Clicked on checkbox"+cb.getText()+position,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //DatabaseHandler database=new DatabaseHandler(con);

       // mResults=  database.getAllHistory(); //getting all rows from the table and storing in the arraylist
        return mResults.size();
    }
    private class HistoryHol extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;
        TextView mTime;
        CheckBox checkBox;
        public HistoryHol(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.text_history);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkBox);
            mTime= (TextView) itemView.findViewById(R.id.time);
            mTextView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(con, MainActivity.class);
            intent.putExtra("url",mTextView.getText().toString());
            con.startActivity(intent);
        }
    }
}
