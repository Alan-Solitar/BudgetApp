package com.burstlinker.budget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by Alan Solitar on 2015/12/24.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private LayoutInflater inflater=null;
    private List<Purchase> purchases;
    public MyAdapter(ArrayList<Purchase> dataSet)
    {
        purchases = dataSet;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position)
    {
        //give purchase meta data to the viewholder
        Purchase current = purchases.get(position);
        holder.name.setText(current.getName());
        holder.price.setText('$'+Float.toString(current.getPrice()));
        holder.date.setText(current.getFormattedDate());
    }

    @Override
    public int getItemCount()
    {
        return purchases.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView price;
        public TextView date;


        public ViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.purchase_name);
            price = (TextView)itemView.findViewById(R.id.purchase_price);
            date = (TextView)itemView.findViewById(R.id.purchase_date);

        }
    }


}
