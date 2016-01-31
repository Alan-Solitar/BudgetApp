package com.burstlinker.budget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EnterPurchaseFragment extends Fragment

{
    private Toolbar toolbar;
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
    Context context;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();
        purchaseHandler = new PurchaseHandler(context);
        note = "";

        //widgets

        //Methods that correspond to various clicks
        //displayData();
        // displayStats();
        // networkClick();
        //insertData();
        //displayPicker();
        //recordNote();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_alt, container, false);
        enterButton = (Button) view.findViewById(R.id.enter_button);
        displayButton = (Button) view.findViewById(R.id.display_button);
        statButton = (Button) view.findViewById(R.id.stat_button);
        connect = (Button) view.findViewById(R.id.connect_button);
        purchaseName = (EditText) view.findViewById(R.id.Item_EditText);
        purchasePrice = (EditText) view.findViewById(R.id.Price_EditText);
        purchaseDate = (EditText) view.findViewById(R.id.Date_EditText);
        dateCBox = (CheckBox) view.findViewById(R.id.cBox);
        NoteCBox = (CheckBox) view.findViewById(R.id.audio_cBox);
        catSpinner = (Spinner) view.findViewById(R.id.cat_spinner);
        networkClick();
        insertData();
        displayPicker();
        recordNote();
        return view;
    }

    public void displayStats()
    {
        /*
        statButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent go_displayStats = new Intent(EnterPurchaseFragment.this, StatFragment.class);
                        startActivity(go_displayStats);
                    }
                }
        );
        */
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
                            }
                            catch (NumberFormatException error)
                            {
                                purchasePrice.setText("Number format exception");
                            }
                            if (isEmpty(purchaseDate))
                                purchase.setDate(); //timestamp using epoch time
                            else
                            {
                                Date date;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try
                                {
                                    date = sdf.parse(purchaseDate.getText().toString());
                                    purchase.setDate(date.getTime());
                                }
                                catch (Exception e)
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
                            if (NoteCBox.isChecked())
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
        /*
        displayButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent go_displayData = new Intent(EnterPurchaseFragment.this, HistoryFrag.class);
                        startActivity(go_displayData);
                    }
                }
        );
        */
    }


    private boolean isEmpty(EditText t)
    {
        if (t.getText().toString().isEmpty())
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
                            dpFrag.show(getChildFragmentManager(), "Choose Date");
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
                            audiofrag = new AudioFragment();
                            //set args
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mode", AudioFragment.MODE.PLAY_AND_RECORD);
                            audiofrag.setArguments(bundle);
                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                            transaction.add(R.id.fragment_container, audiofrag);
                            transaction.commit();

                        } else
                        {
                            getChildFragmentManager().beginTransaction().remove(audiofrag).commit();
                        }
                    }
                }
        );
    }


    public void setDate(String date)
    {
        purchaseDate.setText(date);
        Log.w("<!!!Alan's Tag!!!>", date);
    }

    public void setFile(String file)
    {
        note = file;
    }

    //Check network connection - I am including this because I want to use extend my app in the future
    //to upload user data to a server
    public void networkClick()
    {

        connect.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ConnectivityManager cmanager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = cmanager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected())
                        {
                            Toast.makeText(context, "Connection is good to go", Toast.LENGTH_LONG).show();
                        } else
                        {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }


}


