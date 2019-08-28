package com.example.sqlitedemo.features;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.activities.AddActivity;
import com.example.sqlitedemo.model.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;

    public ProductListAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Product product = productArrayList.get(position);
        final String name = product.getName();
        final String price = "Price : " + product.getPrice();
        String quantity = "Quantity : " + product.getQuantity();
        String id = "Id : " + product.getId();
        holder.pName.setText(name);
        holder.pPrice.setText(price);
        holder.pQuantity.setText(quantity);
        holder.pId.setText(id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(view.getContext());
                ab.setTitle("Update Product");
                ab.setMessage("Do you want to update product detail?");
                ab.setNegativeButton("No", null);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, AddActivity.class);
                        intent.putExtra("update", true);
                        intent.putExtra("key", product.getId());
                        context.startActivity(intent);
                    }
                });
                ab.create().show();

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(view.getContext());
                ab.setTitle("Remove product");
                ab.setMessage("Do you want to delete " + name);
                ab.setNegativeButton("No", null);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        productArrayList.remove(product);
                        Database.getInstance(context).getWritableDatabase()
                                .execSQL("delete from product where id = " + product.getId());
                        notifyDataSetChanged();
                    }
                });
                ab.create().show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView pName, pId, pPrice, pQuantity;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            pId = itemView.findViewById(R.id.pId);
            pName = itemView.findViewById(R.id.pName);
            pPrice = itemView.findViewById(R.id.pPrice);
            pQuantity = itemView.findViewById(R.id.pQuantity);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            new MenuInflater(view.getContext()).inflate(R.menu.main_menu, contextMenu);
        }


    }
}