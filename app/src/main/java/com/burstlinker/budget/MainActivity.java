package com.burstlinker.budget;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.content.Intent;

public class MainActivity extends AppCompatActivity
{
    DBHandler db;
    Button enterButton;
    Button displayButton;
    EditText purchaseName;
    EditText purchasePrice;
    EditText purchasDate;
    RadioButton rButton;
    int FRAGMENT_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this,null,null,1);

        //widgets
        enterButton = (Button) findViewById(R.id.enter_button);
        displayButton = (Button) findViewById(R.id.display_button);
        purchaseName = (EditText)findViewById(R.id.Item_EditText);
        purchasePrice = (EditText)findViewById(R.id.Price_EditText);
        insertData();
        displayData();
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
    private void displayPicker()
    {
        //get id of Radio Button
        rButton = (RadioButton)findViewById(R.id.rButton);

        //If checked, use current date.
        //If not checked, launch datepicker fragment
        rButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                       if(rButton.isChecked())
                       {

                       }
                    }
                }
        );
        FragmentManager fmanager = getFragmentManager();
        FragmentTransaction transaction = fmanager.beginTransaction();
        DatePickerFragment dpFrag = new DatePickerFragment();
        transaction.add(FRAGMENT_ID, dpFrag);
        transaction.commit();


    }



}
