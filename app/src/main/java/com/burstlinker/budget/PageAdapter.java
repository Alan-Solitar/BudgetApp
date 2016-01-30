package com.burstlinker.budget;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by Alan Solitar on 2016/01/29.
 */
public class PageAdapter extends FragmentPagerAdapter
{
    List<android.support.v4.app.Fragment> fragList;
    String[] tabs = null;

    public PageAdapter(FragmentManager manager, List<android.support.v4.app.Fragment> fragList, String[] tabs)
    {
        super(manager);
        this.fragList = fragList;
        this.tabs=tabs;

    }

    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        return fragList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabs[position];
    }
}
