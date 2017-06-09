package com.adrienne.cookbook_app.DetailViewofRecipe.SavedAPIRecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.R;

public class WebActivity extends AppCompatActivity {


    public static final String TAG = "----WEBVIEW --- ";

    public static final String KEY4 = "ApiRecipeKey-url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        /* Web view for the recipe when user searches through the api*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_web);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mom's Cookbook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        // Display the full recipe from  the url.

        WebView webView = new WebView(this);
        setContentView(webView);

        Intent intentUrl = getIntent();
        String url = intentUrl.getStringExtra(KEY4);
        Log.d(TAG, "onCreate: " + KEY4);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean mState = false;
        switch(item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(WebActivity.this, MainActivity.class));
                return true;
            case R.id.menu_delete:
                if(mState == false){
                    item.setVisible(false);
                }
                return true;
            case R.id.menu_bookmark:
                if(mState == false){
                    item.setVisible(false);
                }
                return true;
            case R.id.menu_searchapi:
                if(mState == false){
                    item.setVisible(false);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
