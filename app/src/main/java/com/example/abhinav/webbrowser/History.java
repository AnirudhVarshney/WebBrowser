package com.example.abhinav.webbrowser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    RecyclerView mRecycler;
    private Button btnSelection;
    List<History_model> mResults;
    String pos_list;
    ArrayList<Integer> positions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        btnSelection= (Button) findViewById(R.id.remove);
        DatabaseHandler database = new DatabaseHandler(getApplicationContext());
        mResults = database.getAllHistory();
        positions=new ArrayList<Integer>();
        mRecycler= (RecyclerView) findViewById(R.id.rv_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        final AdapterHistory mAdapter = new AdapterHistory(this,mResults);
        mRecycler.setAdapter(mAdapter);
        btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c=0;
                for (int i = mResults.size()-1; i >=0; i--) {
                    History_model history_model = mResults.get(i);
                    if (history_model.isSelected() == true) {
                        c++;
                        Log.d("ani",c+"hi");
                    }

                }
                if(c<1){
                    Toast.makeText(History.this,"Please select the item to delete",Toast.LENGTH_LONG).show();
                }
                else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(History.this);
                    a_builder.setMessage("Do you want to Delete the Selected items !!!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (int i = mResults.size() - 1; i >= 0; i--) {
                                        History_model history_model = mResults.get(i);
                                        if (history_model.isSelected() == true) {
                                            pos_list = mResults.get(i).getTime().toString();
                                            mAdapter.remove(i, pos_list);
                                            Log.d("ani all selected urls", i + "hi");
                                        }

                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Alert !!!");
                    alert.show();

                } }});



        }
    }




