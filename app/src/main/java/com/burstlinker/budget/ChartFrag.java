package com.burstlinker.budget;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alan Solitar on 2016/01/18.
 */
public class ChartFrag extends android.support.v4.app.Fragment
{
    Boolean mapIsEmpty;
    HashMap<String,Integer> map=null;
    //DBHandler db;
    PurchaseHandler purchaseHandler;
    PieChart pie;
    String[] cats;
    Map<String,Float> percents;
    //Labels for the chart
    ArrayList<String> datax;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        percents = new LinkedHashMap<>();
        cats= getActivity().getResources().getStringArray(R.array.category_array);
        datax = new ArrayList<>();
        purchaseHandler = new PurchaseHandler(getActivity().getApplicationContext());

        //map=db.getCategoryOcurrences();
        map = purchaseHandler.getCategoryOccurrences();
        mapIsEmpty = map.isEmpty();
        //we must make sure there is data
        if(!mapIsEmpty)
        {
            //We need to get the total number of purchases
            int total = 0;
            for (HashMap.Entry<String, Integer> ent : map.entrySet()) {
                total += ent.getValue();
            }

            //we need to calculate the percentage for each category.
            //loop through all categories
            String tempCat;
            Float percent;
            for (int i = 0; i < cats.length; i++) {
                tempCat = cats[i];
                Integer value = map.get(tempCat);
                if (value != null) {

                    percent = (Float.valueOf(value) / total) * 100;
                    percents.put(tempCat, percent);
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =null;
        int layoutID = R.layout.chart;
        view = inflater.inflate(layoutID, container, false);
        pie =(PieChart) view.findViewById(R.id.chart);

        //set up pie chart
        pie.setUsePercentValues(true);
        pie.setRotationAngle(0);
        pie.setRotationEnabled(true);
        pie.setDescription("Purchase by Category");
        if(!mapIsEmpty)
        {
            addData();
        }
        return view;
    }

    //method to add data to piedataset

    public void addData()
    {
       ArrayList<Entry> dataset = new ArrayList<>() ;


        String tempCat;
        int i=0;
        for(String cat: cats)
        {
            Float value = percents.get(cat);
            if(value!=null)
            {
                datax.add(cat);
                dataset.add(new Entry(value,i));
                ++i;
            }
        }


        PieDataSet data = new PieDataSet(dataset,"CAT");
        //set colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.MAGENTA);
        data.setColors(colors);


        PieData moreData = new PieData(datax,data);
        moreData.setValueFormatter(new PercentFormatter());
        moreData.setValueTextSize(10f);
        moreData.setValueTextColor(Color.BLACK);
        pie.setData(moreData);




        //undo highlights
        pie.highlightValues(null);

        //update
        pie.invalidate();

    }
}
