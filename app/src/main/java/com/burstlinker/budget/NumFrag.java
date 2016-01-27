package com.burstlinker.budget;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

/**
 * Created by Alan Solitar on 2016/01/18.
 */
public class NumFrag extends Fragment
{

TextView avg,sum,mode;
//DBHandler db=null;
PurchaseHandler purchaseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //db = new DBHandler(this.getActivity(),null,null,1);
        purchaseHandler = new PurchaseHandler(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = null;
        int layoutID = R.layout.num;

        view = inflater.inflate(layoutID, container, false);
        if(view!=null)
        {
            sum = (TextView) view.findViewById(R.id.total_value);
            avg = (TextView) view.findViewById(R.id.avg_value);
            mode = (TextView) view.findViewById(R.id.mode_value);
        }

    //we need to format this to dollars
        DecimalFormat moneyFormat = new DecimalFormat(Constants.Format.DOLLAR_FORMAT);


        sum.setText(moneyFormat.format(purchaseHandler.getSum()));
        avg.setText(moneyFormat.format(purchaseHandler.getAverage()));
        mode.setText(purchaseHandler.getMode());

        return view;
    }
}
