/**
 * Created by Alan Solitar on 2016/01/15.
 */
package com.burstlinker.budget;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
{
    TheListener listener;
    public interface TheListener
    {
        public void returnDate(String date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //The Java calendar uses 0-11 for the month
        //Thus, I do month+1 for human readability
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH));
        int day = cal.get(Calendar.DAY_OF_MONTH);
        listener = (TheListener)getActivity();
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        String date = Integer.toString(year) + "-" +
                Integer.toString(month+1) + "-" + Integer.toString(day);
        if(listener!=null)
            listener.returnDate(date);

    }
}
