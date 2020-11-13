package com.example.hackernewsreader;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        int newsIndex = intent.getIntExtra("newsId", 1);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Cursor c;
        c = MainActivity.sqLiteDatabase.rawQuery("SELECT * FROM news WHERE id = '"+newsIndex+"'", null);
        c.moveToFirst();

        String webViewDestination = c.getString(c.getColumnIndex("url"));
        webView.loadUrl(webViewDestination);
    }
}