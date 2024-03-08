package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DB_NAME = "account_daily.db";

    public DBHelper(Context context) {
        super(context, DB_NAME ,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table account(_id integer primary key autoincrement," +//主键
                "Title varchar(20)," +//Title
                "Date varchar(20)," +//Date
                "Money varchar(20))";//Money
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @SuppressLint("Range")
    public costList getCostListById(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        costList item = null;

        // 查询指定id的记录
        Cursor cursor = db.query("account", null, "_id = ?", new String[]{String.valueOf(itemId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // 构造costList对象
            item = new costList();
            item.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            item.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
            item.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            item.setMoney(cursor.getString(cursor.getColumnIndex("Money")));

            cursor.close();
        }

        db.close();

        return item;
    }


    @SuppressLint("Range")
    public List<costList> getBillsForDate(String selectedDate) {
        List<costList> billList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 查询 account 表中日期为 selectedDate 的账单数据
        String[] projection = {"_id", "Title", "Date", "Money"};
        String selection = "Date = ?";
        String[] selectionArgs = {selectedDate};
        Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, null);

        // 遍历查询结果，并将每一行数据添加到 billList 中
        if (cursor != null && cursor.moveToFirst()) {
            do {
                costList bill = new costList();
                bill.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                bill.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                bill.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                bill.setMoney(cursor.getString(cursor.getColumnIndex("Money")));
                billList.add(bill);
            } while (cursor.moveToNext());
        }

        // 关闭 cursor 和数据库连接
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return billList;
    }

}


