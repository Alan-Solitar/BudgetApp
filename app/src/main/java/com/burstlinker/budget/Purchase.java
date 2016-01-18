package com.burstlinker.budget;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alan Solitar on 2015/11/28.
 */
public class Purchase
{
    public enum CATEGORY
    {
        FOOD,FAMILY,HEALTH, LEISURE,TRANSPORTATION,EDUCATION
    }

    private int ID;
    private String name;
    private float price;
    private long date;
    private String notePath;  //This is the path to the audio note

    public String getNotePath()
    {
        return notePath;
    }

    public void setNotePath(String notePath)
    {
        this.notePath = notePath;
    }

    public CATEGORY getCategory()
    {
        return category;
    }

    public void setCategory(CATEGORY category)
    {
        this.category = category;
    }
    public void setCategory(String category)
    {
        this.category = CATEGORY.valueOf(category);
    }


    private CATEGORY category;
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
    public String getFormattedDate()
    {
        Date d = new Date(date);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(myFormat);
        String formattedDate = format.format(d);
        return formattedDate;
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
