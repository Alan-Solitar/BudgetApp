package com.burstlinker.budget;

import java.util.Date;
/**
 * Created by BurstLinker 2 on 2015/11/28.
 */
public class Purchase
{
    private int ID;
    private String name;
    private float price;
    private long date;

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate()
    {
        this.date = System.currentTimeMillis();
    }
    public void setDate(long date)
    {
        this.date = date;
    }




}
