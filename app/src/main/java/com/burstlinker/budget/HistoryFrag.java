package com.burstlinker.budget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HistoryFrag extends Fragment
{
    private ArrayList<Purchase> purchases=null;
    private MyAdapter adapter;
    //private DBHandler db;
    private PurchaseHandler purchaseHandler;
    private RecyclerView recycle;
    private RecyclerView.LayoutManager myLayoutManager;
    private Context context;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();
        purchaseHandler = new PurchaseHandler(getActivity());
        purchases = purchaseHandler.getRecords(PurchaseHandler.LIMIT.NO_LIMIT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.history,container,false);
        recycle = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        myLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(myLayoutManager);
        adapter = new MyAdapter(purchases,context,this.getActivity().getSupportFragmentManager());
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new DividerItemDecoration(context));
        return view;

    }




}
