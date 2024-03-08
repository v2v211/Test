package com.example.myapplication3;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class upgrade_cost extends AppCompatActivity {
    private DBHelper helper;
    private EditText et_cost_title;
    private EditText et_cost_money;
    private DatePicker dp_cost_date;
    List<costList> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_cost);
        initView();
    }

    private void initView() {
        helper = new DBHelper(upgrade_cost.this);

        et_cost_title = findViewById(R.id.et_cost_title);
        et_cost_money = findViewById(R.id.et_cost_money);
        dp_cost_date = findViewById(R.id.dp_cost_date);

        // 获取传递的数据
        int itemId = getIntent().getIntExtra("item_id", -1);
        Log.d("DBHelper", "Item id: " + itemId);
        costList item = helper.getCostListById(itemId);
        if(item != null){
            String title = item.getTitle();
            String money = item.getMoney();
            String date = item.getDate();

            // 将数据填充到对应的 EditText 和 DatePicker 控件中
            et_cost_title.setText(title);
            et_cost_money.setText(money);

            // 将日期字符串转换为年月日三个部分
            String[] dateParts = date.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1; // 月份需要减去 1
            int day = Integer.parseInt(dateParts[2]);

            // 设置 DatePicker 的初始日期
            dp_cost_date.init(year, month, day, null);

        }

    }


    public void goback(View view){
        finish();
    }
    public void upgradeButton(View view) {
        int itemId = getIntent().getIntExtra("item_id", -1);
        String titleStr = et_cost_title.getText().toString().trim();
        String moneyStr = et_cost_money.getText().toString().trim();
        String dateStr = dp_cost_date.getYear() + "-" + (dp_cost_date.getMonth() + 1) + "-"
                + dp_cost_date.getDayOfMonth();//这里getMonth会比当前月份少一个月，所以要+1
        if ("".equals(moneyStr)) {//可以不填写Title但是不能不填金额
            Toast toast = Toast.makeText(this, "请填写金额", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Title", titleStr);
            values.put("Money", moneyStr);
            values.put("Date", dateStr);
            int rowsAffected = db.update("account", values, "_id = ?", new String[] {String.valueOf(itemId)});
            if (rowsAffected > 0) {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            } else {
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
            }

            setResult(1);
            finish();
        }

    }
    public void deleteButton(View view){
    // 获取要删除的数据项的行ID，假设从传递的 Intent 中获取
        int itemId = getIntent().getIntExtra("item_id", -1);
        // 获取可写的数据库对象
        SQLiteDatabase db = helper.getWritableDatabase();

        // 执行删除操作
        int rowsAffected = db.delete("account", "_id = ?", new String[] {String.valueOf(itemId)});
        // 删除操作成功后，rowsAffected 的值表示受影响的行数
        if (rowsAffected > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish(); // 删除成功后关闭当前 Activity
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }

        // 关闭数据库连接
        db.close();

    }
}

