package com.cookandroid.newnike;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {

    //final  String DB_PATH = "/data/data/com.cookandroid.newnike/databases";
    public static String DBNAME = "test1";//디비명

    //private static String DB_NAME = "megadb";

    public Db(Context context){
        //super(context, "ljpdb", null, 1);
        super(context, DBNAME, null, 1);
        // onCreate();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //table을 만들때!
        String createTable = "create table if not exists test1("//테이블명
                + " run_num integer PRIMARY KEY autoincrement,"
                + " run_away text, "
                + " run_time text, "
                + " time datetime)";

        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //table을 시작할때!

    }


}
