package com.example.hackernewsreader

import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewActivity : AppCompatActivity() {
    lateinit var  webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val newsIndex = intent.getIntExtra("newsId", 1)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        val c: Cursor

        c = MainActivity.sqLiteDatabase!!.rawQuery("SELECT * FROM news WHERE id = '"+newsIndex+"'", null)
        c.moveToFirst()

        val webViewDestination = c.getString(c.getColumnIndex("url"))
        webView.loadUrl(webViewDestination)

        c.close()
    }
}