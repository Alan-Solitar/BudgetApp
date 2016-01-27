package com.burstlinker.budget;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements DatePickerFragment.TheListener,
        AudioFragment.TheListener
{
    //DBHandler db;
    PurchaseHandler purchaseHandler;
    Spinner catSpinner;
    Button enterButton;
    Button displayButton;
    Button statButton;
    EditText purchaseName;
    EditText purchasePrice;
    EditText purchaseDate;
    CheckBox dateCBox, NoteCBox;
    String note;
    Fragment audiofrag;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db = new DBHandler(this,null,null,1);
        purchaseHandler = new PurchaseHandler(getApplicationContext());

        note= "";
        //widgets
        enterButton = (Button) findViewById(R.id.enter_button);
        displayButton = (Button) findViewById(R.id.display_button);
        statButton = (Button)findViewById(R.id.stat_button);
        connect = (Button)findViewById(R.id.connect_button);
        purchaseName = (EditText)findViewById(R.id.Item_EditText);
        purchasePrice = (EditText)findViewById(R.id.Price_EditText);
        purchaseDate = (EditText)findViewById(R.id.Date_EditText);
        dateCBox = (CheckBox)findViewById(R.id.cBox);
        NoteCBox= (CheckBox)findViewById(R.id.audio_cBox);
        catSpinner = (Spinner)findViewById(R.id.cat_spinner);
        //Methods that correspond to various clicks
        networkClick();
        insertData();
        displayData();
        displayStats();
        displayPicker();
        recordNote();

    }

    public void displayStats()
    {
        statButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent go_displayStats = new Intent(MainActivity.this,StatisticsActivity.class);
                        startActivity(go_displayStats);
                    }
                }
        );
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
                           if(isEmpty(purchaseDate))
                               purchase.setDate(); //timestamp using epoch time
                           else
                           {
                               Date date;
                               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                               try {
                                   date = sdf.parse(purchaseDate.getText().toString());
                                   purchase.setDate(date.getTime());
                               }
                               catch(Exception e)
                               {

                               }

                           }


                           purchase.setNotePath(note);

                           purchase.setCategory(catSpinner.getSelectedItem().toString());
                           //db.addRecord(purchase);
                           purchaseHandler.insert(purchase);
                           //resetViews
                           purchaseDate.setText("");
                           dateCBox.setChecked(true);
                           if(NoteCBox.isChecked())
                           {
                               NoteCBox.performClick();
                           }
                           purchaseName.setText("");
                           purchasePrice.setText("");
                           note = "";

                       }
                   }
               }
       );
    }
    //launch displayData activity
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
                            audiofrag= new AudioFragment();
                            //set args
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mode",AudioFragment.MODE.PLAY_AND_RECORD);
                            audiofrag.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.add(R.id.fragment_container,audiofrag);
                            transaction.commit();

                        }

                        else
                        {
                            getFragmentManager().beginTransaction().remove(audiofrag).commit();
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
        Log.w("<!!!Alan's Tag!!!>", date);
    }
    @Override
    public void returnFile(String file)
    {
        note=file;
    }

    //Check network connection - I am including this because I want to use extend my app in the future
    //to upload user data to a server
    public void networkClick()
    {

        connect.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager cmanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = cmanager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected())
                        {
                            Toast.makeText( getApplicationContext(),"Connection is good to go", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );

    }


}


