package com.example.hackernewsreader

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var news = ArrayList<News>()
    lateinit var sqLiteDatabase: SQLiteDatabase
    private lateinit var c: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        this.adapter = RecyclerAdapter(news, object : RecyclerAdapter.NewsClickListener {
            override fun newsClicked(news: News?) {
                Toast.makeText(this@MainActivity, "CLICKED MAIN ${news!!.title}", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = adapter

        try {
            sqLiteDatabase = this.openOrCreateDatabase("News", Context.MODE_PRIVATE, null)
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (title VARCHAR, url VARCHAR, id INTEGER PRIMARY KEY)")
            //sqLiteDatabase.execSQL("DELETE FROM news");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Ege', 23)");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Öykü', 12)");

            c = sqLiteDatabase.rawQuery("SELECT * FROM news", null)
            val idIndex = c.getColumnIndex("id")
            val urlIndex = c.getColumnIndex("url")
            val titleIndex = c.getColumnIndex("title")

            c.moveToFirst()
            while (!c.isAfterLast()) {
                //news.add(c.getString(titleIndex))
                //adapter.notifyItemInserted(news.size-1)
                Log.i("results id", Integer.toString(c.getInt(idIndex)));
                Log.i("results title", c.getString(titleIndex));
                Log.i("results url", c.getString(urlIndex));
                c.moveToNext()
            }

            getNews()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmField
        var sqLiteDatabase: SQLiteDatabase? = null
        var c: Cursor? = null
    }

    private fun getNews() {
        val service = RetrofitClient.instance
        val newsListCall = service.getNewsList()

        newsListCall.enqueue(object : Callback<IntArray>{
            override fun onResponse(call: Call<IntArray>, response: Response<IntArray>) {
                if (response.isSuccessful){
                    for (i in 0..10) {
                        val newsDetailCall = service.getNewsDetail(response.body()!![i])
                        newsDetailCall.enqueue(object : Callback<News>{
                            override fun onResponse(call: Call<News>, response: Response<News>) {
                                if (response.isSuccessful){
                                    //sqLiteDatabase.execSQL("INSERT INTO news (title, url) VALUES ('"+response.body()?.title+"', '"+response.body()?.url+"')");
                                    news.add(response.body()!!)
                                    adapter.notifyItemInserted(news.size-1)
                                }
                            }
                            override fun onFailure(call: Call<News>, t: Throwable) {
                                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }
            override fun onFailure(call: Call<IntArray>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}