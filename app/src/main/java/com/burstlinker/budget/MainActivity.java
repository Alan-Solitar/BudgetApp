package com.burstlinker.budget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan Solitar on 2016/01/28.
 */
public class MainActivity extends AppCompatActivity
{
    private String[] tabs;
    private Toolbar toolbar = null;
    private ViewPager pager =null;
    private TabLayout tabLayout=null;
    List<Fragment> fragList= null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tabs = getResources().getStringArray(R.array.tab_names);
        setContentView(R.layout.main_activity);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        pager = (ViewPager) findViewById(R.id.pager);

        tabLayout= (TabLayout) findViewById(R.id.tabs);
        fragList = new ArrayList<>();
        fragList.add(new ChartFrag());
        fragList.add(new QuickFrag());
        fragList.add(new HistoryFrag());
        fragList.add(new StatFragment());
        PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(),fragList,tabs);
        pager.setAdapter(pagerAdapter);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);

/*

        QuickFrag quickFrag = new QuickFrag();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container, quickFrag, "qfrag");
        transaction.commit();
*/
    }
}
