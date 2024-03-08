package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements ListAdapter.OnItemClickListener{

    private DBHelper helper;
    private  ListView listView;
    private  ImageButton Add;
    private  ImageButton search;
    private List<costList>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    //初始化
    @SuppressLint("Range")
    private void initData() {
        helper=new DBHelper(MainActivity.this);
        list=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("account",null,null,null,null,
                null,null);
        while (cursor.moveToNext()){
            costList clist=new costList();//构造实例
            clist.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            clist.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
            clist.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            clist.setMoney(cursor.getString(cursor.getColumnIndex("Money")));
            list.add(clist);
        }
        listView = findViewById(R.id.list_view);
        //绑定适配器
        ListAdapter mAdapter = new ListAdapter(this,list);
        mAdapter.setOnItemClickListener(this);
        listView.setAdapter(mAdapter);
        db.close();
    }
    @Override
    public void onItemClick(int position) {
        // 处理点击事件的逻辑
        Log.d("Main", "jump");
        costList item = list.get(position);
        Intent intent = new Intent(MainActivity.this, upgrade_cost.class);
        intent.putExtra("item_id", item.get_id());
        startActivityForResult(intent, 1);
    }

    private void initView() {
        Add=findViewById(R.id.add);
        search=findViewById(R.id.search);
    }



    //事件：添加
    public void addAccount(View view){//跳转
        Intent intent=new Intent(MainActivity.this,new_cost.class);
        startActivityForResult(intent,1);
    }
    public void searchAccount(View view){
        Intent intent=new Intent(MainActivity.this,search_cost.class);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1)
        {
            this.initData();
        }
        if(requestCode==2&&resultCode==2){
            // 获取选择的日期数据
            String selectedDate = null;
            if (data != null) {
                selectedDate = data.getStringExtra("selected_date");
                // 根据选择的日期从数据库中检索相关账单数据并显示在主界面上
                showBillsForSelectedDate(selectedDate);
            }
        }
    }

    private void showBillsForSelectedDate(String selectedDate) {
        // 根据选择的日期从数据库中检索相关账单数据
        List<costList> billList = helper.getBillsForDate(selectedDate); // 假设 DBHelper 中有一个方法可以根据日期检索相关账单数据

        // 使用适配器将账单数据绑定到 ListView 上
        ListAdapter adapter = new ListAdapter(this, billList);
        listView.setAdapter(adapter);
    }

    public void refresh(View view) {
        initView();
        initData();
    }
}
