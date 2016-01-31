package com.burstlinker.budget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by Alan Solitar on 2016/01/29.
 */
public class CustomPageAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragList;
    private String[] tabs = null;
     Fragment currentFrag=null;

    public CustomPageAdapter(FragmentManager manager, List<Fragment> fragList, String[] tabs)
    {
        super(manager);
        this.fragList = fragList;
        this.tabs=tabs;
    }
    public Fragment getCurrentFrag()
    {
        return currentFrag;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragList.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object)
    {
        if(currentFrag!=object)
        {
            currentFrag= (Fragment) object;
        }

        super.setPrimaryItem(container, position, object);
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
