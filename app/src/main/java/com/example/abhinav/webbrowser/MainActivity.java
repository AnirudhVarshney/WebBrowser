package com.example.abhinav.webbrowser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {
    private WebView mWebView;
    private EditText mTxtURL;
    ImageView mSearchButton;
    String nUrl;
     ProgressWheel mProgressBar;
    private WebClient mWebClient;
    List<Bookamark_model> mResults;
    String time;
    String url;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        mWebView= (WebView) findViewById(R.id.webView);
        mTxtURL= (EditText) findViewById(R.id.txtURL);
        mProgressBar= (ProgressWheel) findViewById(R.id.progress_wheel);
        mWebClient = new WebClient(mProgressBar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.google.com");
        mWebView.setWebViewClient(mWebClient);
        mSearchButton= (ImageView) findViewById(R.id.search);
        mSearchButton.setOnClickListener(this);
        Intent intent = getIntent();
        nUrl=intent.getStringExtra("url");
        mTxtURL.setOnKeyListener(this);

        if(nUrl!=null ){
            Log.d("@@",nUrl+"hi in on create");
            SimpleDateFormat dateFormat = new SimpleDateFormat( //formating the date according  to the need
                    "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            Date date=new Date();
            time=dateFormat.format(date);
            DatabaseHandler databaseHandler=new DatabaseHandler(this);
            History_model history_model=new History_model(nUrl,time,false);
            databaseHandler.addHistory(history_model);
            mWebClient.shouldOverrideUrlLoading(mWebView, nUrl);
        }
        if(nUrl==null){
            Toast.makeText(this,"isempty",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.settings) {


            return true;
        } else if (id == R.id.history_menu) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.bookmarked) {
            Intent intent = new Intent(this, Bookmark.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.bookmark) {
            int c = 0;
            Intent intent = getIntent();
            nUrl=intent.getStringExtra("url");
            url = mTxtURL.getText().toString();
            Log.d("@@@@", url + "in book");
            Log.d("@@@@", nUrl + "in book nURL");
            if (url.isEmpty() && nUrl==null) {
                Toast.makeText(this, "Please select something to bookmark", Toast.LENGTH_LONG).show();
            }
            else if(url.isEmpty() && nUrl!=null) {
                DatabaseHandler databaseHandler = new DatabaseHandler(this);
                mResults = databaseHandler.getAllBookmarks();
                for (int i = 0; i < mResults.size(); i++) {
                    if (nUrl.equals(mResults.get(i).getUrl())) {
                        c++;
                    }
                }
                Log.d("ani", c + "Hi in urlempty");
                if (c > 0) {
                    Toast.makeText(this, "Already bookmarked", Toast.LENGTH_LONG).show();
                } else {
                    Bookamark_model bookamark_model = new Bookamark_model(nUrl);
                    databaseHandler.addBookmark(bookamark_model);
                    Toast.makeText(this, "Bookmarked succesfully", Toast.LENGTH_LONG).show();
                }
            }
            else if(nUrl==null){
                DatabaseHandler databaseHandler = new DatabaseHandler(this);
                mResults = databaseHandler.getAllBookmarks();
                for (int i = 0; i < mResults.size(); i++) {
                    if (url.equals(mResults.get(i).getUrl())) {
                        c++;
                    }
                }
                Log.d("ani", c + "Hi in url not empty");
                if (c > 0) {
                    Toast.makeText(this, "Already bookmarked", Toast.LENGTH_LONG).show();
                } else {
                    Bookamark_model bookamark_model = new Bookamark_model(url);
                    databaseHandler.addBookmark(bookamark_model);
                    Toast.makeText(this, "Bookmarked succesfully", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(id==R.id.clearhistory){
            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            databaseHandler.clearHistory();
        }
        else if(id==R.id.clearBookmark){
            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            databaseHandler.clearBookmark();
        }
            return super.onOptionsItemSelected(item);
        }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Log.d("ani", mTxtURL.getText().toString() + "hi");
        url = mTxtURL.getText().toString();
        if (!(mTxtURL.getText().toString().startsWith("https://") || mTxtURL.getText().toString().startsWith("http://"))) {
            if (!(mTxtURL.getText().toString().startsWith("www."))) {
                url = "https://" + "www." + mTxtURL.getText().toString();
                Toast.makeText(this, "notcontains" + url, Toast.LENGTH_LONG).show();
            }

        } else if (!(mTxtURL.getText().toString().startsWith("https://") || mTxtURL.getText().toString().startsWith("http://"))) {
            if ((mTxtURL.getText().toString().startsWith("www."))) {
                url = "https://" + mTxtURL.getText().toString();
                Toast.makeText(this, "notcontains http only" + url, Toast.LENGTH_LONG).show();
            }
        }
            else if ((mTxtURL.getText().toString().startsWith("https://") || mTxtURL.getText().toString().startsWith("http://"))) {
                if (!(mTxtURL.getText().toString().contains("www."))) {

                    int i = mTxtURL.getText().toString().indexOf("/", 0);
                    Log.d("ani inde", i + "h");
                    url = mTxtURL.getText().toString().substring(0,i+2)+"www."+ mTxtURL.getText().toString().substring(i+2);
                    Toast.makeText(this, "notcontains http only" + url, Toast.LENGTH_LONG).show();
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat( //formating the date according  to the need
                    "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            time = dateFormat.format(date);
            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            if (!mTxtURL.getText().toString().isEmpty()) {
                History_model history_model = new History_model(url, time,false);
                databaseHandler.addHistory(history_model);
                mWebClient.shouldOverrideUrlLoading(mWebView, url);
            }


    }

    /**
     * Called when a hardware key is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     * <p>Key presses in software keyboards will generally NOT trigger this method,
     * although some may elect to do so in some situations. Do not assume a
     * software input method has to be key-based; even if it is, it may use key presses
     * in a different way than you expect, so there is no way to reliably catch soft
     * input key presses.
     *
     * @param v       The view the key has been dispatched to.
     * @param keyCode The code for the physical key that was pressed
     * @param event   The KeyEvent object containing full information about
     *                the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            }  else {
                super.onBackPressed();
            }
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.d("ani",mTxtURL.getText().toString()+"hi on key preessed");
                url=mTxtURL.getText().toString();
                if (!(mTxtURL.getText().toString().startsWith("https://") || mTxtURL.getText().toString().startsWith("http://"))) {
                    if (!(mTxtURL.getText().toString().startsWith("www."))) {
                        url = "https://" + "www." + mTxtURL.getText().toString();
                        Toast.makeText(this, "notcontains" + url, Toast.LENGTH_LONG).show();
                    }

                } else if (!(mTxtURL.getText().toString().startsWith("https://") || mTxtURL.getText().toString().startsWith("http://"))) {
                    if ((mTxtURL.getText().toString().startsWith("www."))) {
                        url = "https://" + mTxtURL.getText().toString();
                        Toast.makeText(this, "notcontains http only" + url, Toast.LENGTH_LONG).show();
                    }
                }
                else if ((mTxtURL.getText().toString().startsWith("https://") || mTxtURL.getText().toString().startsWith("http://"))) {
                    if (!(mTxtURL.getText().toString().contains("www."))) {

                        int i = mTxtURL.getText().toString().indexOf("/", 0);
                        Log.d("ani inde", i + "h");
                        url = mTxtURL.getText().toString().substring(0,i+2)+"www."+ mTxtURL.getText().toString().substring(i+2);
                        Toast.makeText(this, "notcontains http only" + url, Toast.LENGTH_LONG).show();
                    }
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat( //formating the date according  to the need
                        "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                Date date=new Date();
                time=dateFormat.format(date);
                DatabaseHandler databaseHandler=new DatabaseHandler(MainActivity.this);
                if(!mTxtURL.getText().toString().isEmpty()){
                    History_model history_model=new History_model(url,time,false);
                    databaseHandler.addHistory(history_model);
                    mWebClient.shouldOverrideUrlLoading(mWebView, mTxtURL.getText().toString());
                }
            }
        }
        return true;
        }
}
class WebClient extends WebViewClient{
    ProgressWheel progressWheel;
     WebClient(ProgressWheel progressBar) {
        this.progressWheel=progressBar;

   }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
       // view.findViewById(R.id.progress_wheel).setVisibility(View.VISIBLE);
        progressWheel.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        //super.onPageFinished(view, url);
       // view.findViewById(R.id.progress_wheel).setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
    }
}
