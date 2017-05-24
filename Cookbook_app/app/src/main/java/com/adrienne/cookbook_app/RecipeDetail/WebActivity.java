package com.adrienne.cookbook_app.RecipeDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebIconDatabase;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.adrienne.cookbook_app.MainActivity;
import com.adrienne.cookbook_app.R;

public class WebActivity extends AppCompatActivity {

    ImageView mImageView;
    public static final String TAG = "----WEBVIEW --- ";

    public static final String KEY4 = "ApiRecipeKey-url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mImageView = (ImageView) findViewById(R.id.to_home_button);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movingOn = new Intent(WebActivity.this, MainActivity.class);
                startActivity(movingOn);
            }
        });

        // Display the full recipe from  the url.

        WebView webView = new WebView(this);
        setContentView(webView);

        Intent intentUrl = getIntent();
        String url = intentUrl.getStringExtra(KEY4);
        Log.d(TAG, "onCreate: " + KEY4);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }
}
