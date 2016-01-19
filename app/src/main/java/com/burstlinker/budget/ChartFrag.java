package com.burstlinker.budget;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Alan Solitar on 2016/01/18.
 */
public class ChartFrag extends Fragment
{
    HashMap<String,Integer> map=null;
    DBHandler db;
    PieChart pie;
    String[] cats;
    HashMap<Float,String> percents;
    ArrayList<String> datax;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        percents = new HashMap<Float,String>();
        cats= getActivity().getResources().getStringArray(R.array.category_array);
        datax = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        db = new DBHandler(this.getActivity(),null,null,1);
        map=db.getCategoryOcurrences();


        //We need to get the total number of purchases
        int total=0;
        for(HashMap.Entry<String,Integer> ent:map.entrySet())
        {
            total+=ent.getValue();
        }

        //we need to calculate the percentage for each category.
        for(int i=0; i<cats.length;i++)
        {
           Integer value = map.get(cats[i]);
            if (value!=null&cats[i]!=null)
            {
                datax.add(cats[i]);
                Float f = Float.valueOf(map.get(cats[i])) / total;
                percents.put((Float.valueOf(map.get(cats[i])) / total), cats[i]);
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
        addData();
        return view;
    }

    //method to add data to piedataset

    public void addData()
    {
       ArrayList<Entry> dataset = new ArrayList<Entry>() ;
        int i=0;
        for(HashMap.Entry<Float,String> ent:percents.entrySet())
        {
            if(ent.getValue()!=null);
            {
                dataset.add(new Entry(ent.getKey(),i));
                i++;
            }
        }
        PieDataSet data = new PieDataSet(dataset,"chart");
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
