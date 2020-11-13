package com.example.hackernewsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView newsListView;
    ArrayList<String> news = new ArrayList<>();
    static SQLiteDatabase sqLiteDatabase;
    static Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            sqLiteDatabase = this.openOrCreateDatabase("News", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (title VARCHAR, url VARCHAR, id INTEGER PRIMARY KEY)");
            //sqLiteDatabase.execSQL("DELETE FROM news");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Ege', 23)");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Öykü', 12)");

            c = sqLiteDatabase.rawQuery("SELECT * FROM news", null);
            int idIndex = c.getColumnIndex("id");
            int urlIndex = c.getColumnIndex("url");
            int titleIndex = c.getColumnIndex("title");

            c.moveToFirst();

            while(!c.isAfterLast()) {
                news.add(c.getString(titleIndex));
                //Log.i("results id", Integer.toString(c.getInt(idIndex)));
                //Log.i("results title", c.getString(titleIndex));
                //Log.i("results url", c.getString(urlIndex));

                c.moveToNext();
            }

            //GetNewsTask getNewsTask = new GetNewsTask();
            //getNewsTask.execute("https://hacker-news.firebaseio.com/v0/topstories.json");

        } catch (Exception e) {
            e.printStackTrace();
        }

        newsListView = findViewById(R.id.newsListView);
        ArrayAdapter newsListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, news);
        newsListView.setAdapter(newsListAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("newsId", i+1);

                startActivity(intent);
            }
        });

    }

    public class GetNewsTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String topNewsResult = "";
            String newsDetailResult;
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    topNewsResult += current;
                    data = reader.read();
                }

                JSONArray itemArray= new JSONArray(topNewsResult);

                for (int i = 0; i < itemArray.length(); i++) {
                    newsDetailResult = "";
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + itemArray.getString(i) + ".json");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    in = urlConnection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();

                    while (data != -1) {
                        char current = (char) data;
                        newsDetailResult += current;
                        data = reader.read();
                    }

                    JSONObject jsonObject = new JSONObject(newsDetailResult);

                    //jsonObject.isNull("url");

                    String newsTitle = jsonObject.getString("title").replace("'", "`");

                    String newsUrl;
                    try{
                        newsUrl = jsonObject.getString("url");
                    } catch (Exception e) {
                        e.printStackTrace();
                        newsUrl = "noURL";
                    }

                    //sqLiteDatabase.execSQL("INSERT INTO news (title, url) VALUES ('"+newsTitle+"', '"+newsUrl+"')");
                }

                return "ege";

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}