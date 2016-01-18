package com.burstlinker.budget;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
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
        holder.category.setText(current.getCategory().toString());
        final String fileName = current.getNotePath();
        holder.note.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //new audiofrag
                        AudioFragment frag = new AudioFragment();
                        //create a bundle for including args
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("mode", AudioFragment.MODE.PLAY);
                        bundle.putString("file",fileName );
                        //set args
                        frag.setArguments(bundle);
                    }
                }
        );
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
            name = (TextView)itemView.findViewById(R.id.purchase_name);
            price = (TextView)itemView.findViewById(R.id.purchase_price);
            date = (TextView)itemView.findViewById(R.id.purchase_date);
            category = (TextView)itemView.findViewById(R.id.purchase_cat);
            note = (Button)itemView.findViewById(R.id.p_button);
        }
    }


}
