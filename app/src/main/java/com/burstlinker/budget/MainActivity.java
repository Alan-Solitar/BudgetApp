package com.burstlinker.budget;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity
        implements DatePickerFragment.TheListener,
        AudioFragment.TheListener
{
    DBHandler db;
    Spinner catSpinner;
    Button enterButton;
    Button displayButton;
    EditText purchaseName;
    EditText purchasePrice;
    EditText purchaseDate;
    CheckBox dateCBox, NoteCBox;
    String note;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this,null,null,1);

        note= "";
        //widgets
        enterButton = (Button) findViewById(R.id.enter_button);
        displayButton = (Button) findViewById(R.id.display_button);
        purchaseName = (EditText)findViewById(R.id.Item_EditText);
        purchasePrice = (EditText)findViewById(R.id.Price_EditText);
        purchaseDate = (EditText)findViewById(R.id.Date_EditText);
        dateCBox = (CheckBox)findViewById(R.id.cBox);
        NoteCBox= (CheckBox)findViewById(R.id.audio_cBox);
        catSpinner = (Spinner)findViewById(R.id.cat_spinner);
        //Methods that correspond to various clicks
        insertData();
        displayData();
        displayPicker();
        recordNote();
    }

    public void insertData()
    {
       enterButton.setOnClickListener(
               new Button.OnClickListener()
               {
                   @Override
                   public void onClick(View V)
                   {

                       //check if either field is empty - will only execute if both fields are completed
                       if (!(isEmpty(purchaseName) || isEmpty(purchasePrice)))
                       {
                           Purchase purchase = new Purchase();
                           purchase.setName(purchaseName.getText().toString());
                           try
                           {
                               purchase.setPrice(Float.parseFloat(purchasePrice.getText().toString()));
                           } catch (NumberFormatException error)
                           {
                               purchasePrice.setText("Number format exception");
                           }
                           purchase.setDate(); //timestamp using epoch time

                           if(note!="")
                           {
                               purchase.setNotePath(note);
                           }
                           purchase.setCategory(catSpinner.getSelectedItem().toString());
                           db.addRecord(purchase);
                           //clear both fields
                           purchaseName.setText("");
                           purchasePrice.setText("");

                       }
                   }
               }
       );
    }
    public void displayData()
    {
       displayButton.setOnClickListener(
               new Button.OnClickListener()
               {
                   @Override
                   public void onClick(View v)
                   {
                       Intent go_displayData = new Intent(MainActivity.this,DataDisplay.class);
                        startActivity(go_displayData);
                   }
               }
       );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }
    private boolean isEmpty(EditText t)
    {
        if(t.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    //code to handle display of date picker
    private void displayPicker()
    {

        //If checked, use current date.
        //If not checked, launch datepicker fragment
        dateCBox.setOnClickListener(
                new CheckBox.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (!dateCBox.isChecked())
                        {
                            DatePickerFragment dpFrag = new DatePickerFragment();
                            dpFrag.show(getFragmentManager(), "Choose Date");
                        }
                    }
                }
        );
    }

    void recordNote()
    {
        NoteCBox.setOnClickListener(
                new CheckBox.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (NoteCBox.isChecked())
                        {
                            Fragment audiofrag= new AudioFragment();
                            //set args
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mode",AudioFragment.MODE.PLAY_AND_RECORD);
                            audiofrag.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.add(R.id.fragment_container,audiofrag);
                            transaction.commit();

                        }
                    }
                }
        );
    }

    //This is from the listener interface that must be implemented
    //This callback allows an activity to receive data from a fragment
    @Override
    public void returnDate(String date)
    {
        purchaseDate.setText(date);
        Log.w("<!!!Alan's Tag!!!>",date);
    }
    @Override
    public void returnFile(String file)
    {
        note=file;
    }


}
