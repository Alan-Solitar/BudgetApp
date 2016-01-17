package com.burstlinker.budget;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

/**
 * Created by Alan Solitar on 2016/01/16.
 */

//Class to handle operations related to file naming and saving
public class FileHandler
{
    Context context;

    public FileHandler(Context context)
    {
        this.context =  context;
    }

    public String generateName()
    {
        UUID randID = UUID.randomUUID();
        String fileName = randID.toString();
        return fileName;
    }
    public String getSavedfile()
    {
        String fileName = generateName();
        String fullFilename="";
        //We first need to check whether or not external storage exists
        String state = "";
        state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            File root = Environment.getExternalStorageDirectory();
            fullFilename = root.getAbsolutePath() + "/" +"budgetAudio";
            File directory = new File(fullFilename);

            //we need to check if folder exists
            if(!directory.exists())
            {
                directory.mkdir();
            }
            File file = new File(directory,fileName);
        }
        else
        {
            //Error message if storage is not found
            Toast.makeText(context, "SD Card not found",Toast.LENGTH_LONG);

        }
        Environment.getExternalStorageDirectory().getAbsolutePath();
        return fullFilename + "/" + fileName;
    }

}
