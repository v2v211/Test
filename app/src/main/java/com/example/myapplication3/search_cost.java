package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class search_cost extends AppCompatActivity {
    private DatePicker datePicker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cost);

        datePicker = findViewById(R.id.datePicker);

        // 设置确定按钮的点击事件监听器
        Button btnConfirm = findViewById(R.id.button);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取选择的年、月、日
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1; // 月份从0开始，需要加1
                int day = datePicker.getDayOfMonth();

                // 构造选择的日期字符串
                String selectedDate = year + "-" + month + "-" + day;

                // 创建一个带有选择的日期数据的 Intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_date", selectedDate);

                // 将选择的日期数据返回给 MainActivity
                setResult(2, resultIntent);

                // 结束当前活动
                finish();
            }
        });
    }
    public void goback(View view){
        finish();
    }
}

