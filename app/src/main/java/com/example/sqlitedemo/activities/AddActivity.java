package com.example.sqlitedemo.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.features.Database;
import com.example.sqlitedemo.model.Product;

public class AddActivity extends AppCompatActivity {

    EditText eName, ePrice, eQuantity;
    Button btnAdd;
    SQLiteDatabase db;
    private boolean isUpdate = false;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Database database = Database.getInstance(this);
        db = database.getWritableDatabase();

        isUpdate = getIntent().getBooleanExtra("update", false);

        eName = findViewById(R.id.pName);
        eQuantity = findViewById(R.id.pQuantity);
        ePrice = findViewById(R.id.pPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eName.getText().toString();
                String price = ePrice.getText().toString();
                String quantity = eQuantity.getText().toString();
                String query;
                if (isUpdate) {
                    query = "update product set name = '" + name + "'" + ",price= " + price + " , quantity=" + quantity + " where id=" + key;
                } else {
                    query = "insert into product(name,price,quantity) values ('" + name + "'," + price + "," + quantity + ")";
                }
                db.execSQL(query);
                Toast.makeText(AddActivity.this, "Data inserted/updated", Toast.LENGTH_SHORT).show();
            }
        });

        if (isUpdate) {
            fetchDetail();
        }
    }

    void fetchDetail() {
        key = getIntent().getStringExtra("key");
        String query = "select * from product where id=" + key;
        Cursor cursor = Database.getInstance(this).getReadableDatabase()
                .rawQuery(query, null);

        if (cursor.moveToNext()) {

            Product product = new Product(cursor);

            final String name = product.getName();
            final String price = +product.getPrice() + "";
            String quantity = product.getQuantity() + "";
            eName.setText(name);
            eQuantity.setText(quantity);
            ePrice.setText(price);
        }
    }
}
