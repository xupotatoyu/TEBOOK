package com.tebook.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DBOpenHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "horizon.db"; //数据库名称
        private static final int version = 1; //数据库版本

        public DBOpenHelper(Context context) {
            super(context, DB_NAME, null, version);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //创建书籍信息表
            String sql = "create table tb_bookInfo(_id integer primary key autoincrement,name text NOT NULL,address text UNIQUE," +
                    "cover integer NOT NULL);";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }