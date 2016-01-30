package com.burstlinker.budget;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan Solitar on 2015/12/24.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    DecimalFormat moneyFormat;
    private LayoutInflater inflater = null;
    private List<Purchase> purchases;
    Context context;
    FragmentManager fmanager=null;

    public MyAdapter(ArrayList<Purchase> dataSet, Context context, FragmentManager theManager)
    {
        this.fmanager=theManager;
        this.context = context;
        purchases = dataSet;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        moneyFormat = new DecimalFormat(Constants.Format.DOLLARS_ONLY);
        View view = LayoutInflater.from(parent.getContext())
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
        holder.price.setText(moneyFormat.format(current.getPrice()));
        holder.date.setText(current.getFormattedDate());
        holder.category.setText(current.getCategory().toString());
        final String fileName = current.getNotePath();
        if (!fileName.isEmpty() && fileName != null)
        {
            holder.note.setOnClickListener(
                    new Button.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            //we need to make sure there is a file to play

                            {

                                AudioFragment frag = new AudioFragment();
                                //create a bundle for including args
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("mode", AudioFragment.MODE.PLAY);
                                bundle.putString("file", fileName);
                                //set args
                                frag.setArguments(bundle);
                                android.support.v4.app.FragmentTransaction transaction = fmanager.beginTransaction();
                                transaction.add(frag, "headless");
                                transaction.commit();
                            }
                        }
                    }
            );
        } else
        {
            holder.note.setVisibility(View.GONE);
            holder.note.setClickable(false);
        }
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
        public TextView category;
        public Button note;

        public ViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.purchase_name);
            price = (TextView) itemView.findViewById(R.id.purchase_price);
            date = (TextView) itemView.findViewById(R.id.purchase_date);
            category = (TextView) itemView.findViewById(R.id.purchase_cat);
            note = (Button) itemView.findViewById(R.id.p_button);
        }
    }


}
