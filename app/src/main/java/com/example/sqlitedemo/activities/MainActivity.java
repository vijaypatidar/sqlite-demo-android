package com.example.sqlitedemo.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.features.Database;
import com.example.sqlitedemo.features.ProductListAdapter;
import com.example.sqlitedemo.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProductListAdapter productListAdapter;
    ArrayList<Product> productArrayList;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database database = Database.getInstance(this);
        db = database.getReadableDatabase();
        productArrayList = new ArrayList<>();

        productListAdapter = new ProductListAdapter(productArrayList, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        registerForContextMenu(recyclerView);
        recyclerView.setAdapter(productListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        productArrayList.clear();
        String query = "select * from product";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            productArrayList.add(new Product(cursor));
        }
        productListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuAdd:
                startActivity(new Intent(this, AddActivity.class));
                break;
            case R.id.menuExit:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
