package com.cookandroid.newnike;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class NikeActivity3 extends AppCompatActivity {
    TextView result;
    Button btn_index, btn_list;
    SQLiteDatabase sqlDB;
    Db db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nike3);


        btn_list = findViewById(R.id.btn_list);
        result = findViewById(R.id.result);
        btn_index = findViewById(R.id.btn_index);

        db = new Db(this);

        btn_list.setOnClickListener(new View.OnClickListener() {

       /*     @Override
            public void onClick(View view) {
                Context context = view.getContext();

                db = new Db(view.getContext());
                sqlDB = db.getWritableDatabase();

                int position = getAdapterPosition();
               int position;
                if(position != RecyclerView.NO_POSITION) {
                    Log.d("tag", position + "클릭");
                    Cursor cursor = sqlDB.rawQuery("select run_num, run_away, run_time, time from myMemo order by mTime DESC", null);
                    cursor.move(position+1);
                    Intent intent = new Intent(view.getContext(), NikeActivity2.class);
                    intent.putExtra("title",cursor.getString(1));
                    intent.putExtra("content",cursor.getString(2));
                    intent.putExtra("time", cursor.getString(3));
                    intent.putExtra("position", cursor.getInt(0));
                    context.startActivity(intent);
                    ((Activity)context).finish();
                    cursor.close();
                }
            }*/








            @Override
            public void onClick(View v) {
                sqlDB = db.getWritableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM test1",null);

                String run_num = null;
                String run_away = null;
                String run_time = null;
                String time = null;

                int[] b = new int[5];
                String a = null;

                while (cursor.moveToNext()) {
                    run_num = cursor.getString(0) + ".\t\t\t\t\t\r";
                    run_away = "  " + cursor.getString(1) + "m\t\t\t\t\t\r";
                    run_time = "  " + cursor.getString(2) + "\t\t\t\t\t\r ";
                    time = "  " + cursor.getString(3) + "\t\t\t\t\t\n";


                    a = a + (run_num + run_away + run_time + time + "\r\n");

                }

                result.setText(a);
                cursor.close();
                sqlDB.close();

                print("selectList 성공!");

            }

        });

        btn_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NikeActivity.class);
                startActivity(intent);
            }
        });



    }

    public void print(String result){
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }
}
