package com.example.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");

            sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Ege', 23)");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Öykü', 12)");

            sqLiteDatabase.execSQL("DELETE FROM newUsers WHERE name = 'Ege' AND id = 5");

            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM newUsers", null);
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            c.moveToFirst();

            while(!c.isAfterLast()) {
                Log.i("results id", Integer.toString(c.getInt(idIndex)));
                Log.i("results name", c.getString(nameIndex));
                Log.i("results age", Integer.toString(c.getInt(ageIndex)));

                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}