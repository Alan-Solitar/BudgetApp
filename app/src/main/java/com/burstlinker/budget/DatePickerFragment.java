package com.burstlinker.budget;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;

/**
 * Created by BurstLinker 2 on 2016/01/15.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        //
    }
}
