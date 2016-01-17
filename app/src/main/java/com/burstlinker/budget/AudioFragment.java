package com.burstlinker.budget;
import android.app.Fragment;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.media.MediaPlayer;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by Alan Solitar on 2016/01/16.
 */
public class AudioFragment extends Fragment
{
    private FileHandler generator=null;
    private static final String TAG = "AUDIO_TEST";
    private static String fileName ="";
    //Audio Playback related
    private Button playStopButton=null;
    private MediaPlayer mplayer = null;

    //Audio Capture related
    private Button startStopRecordButton=null;
    private MediaRecorder mrecorder = null;

    private boolean isRecording;
    private boolean isPlaying;


    private void onRecord(boolean recording)
    {
        if (!recording)
        {
            startRecording();
        }
        else
        {
            stopRecording();
        }
    }
    private void onPlay(boolean playing)
    {
        if(!playing)
        {
            startPlayback();
        }
        else
        {
            stopPlayback();
        }
    }

    private void startRecording()
    {
        mrecorder = new MediaRecorder();
        mrecorder.reset();
        mrecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mrecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        fileName=generator.getSavedfile("3pg");
        mrecorder.setOutputFile(fileName);
        mrecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
       try
       {
           mrecorder.prepare();
           mrecorder.start();
       }
       catch(Exception e)
        {
            Log.e("Recorder_Test","Error");
        }

    }
    private void stopRecording()
    {
        mrecorder.release();
        mrecorder = null;
    }




    private void startPlayback()
    {
        try
        {
            FileInputStream stream = new FileInputStream(fileName);
        }
        catch(Exception e)
        {
            Log.e("Stream error", "could not find file");
        }
        mplayer = new MediaPlayer();
        try
        {
            mplayer.setDataSource(fileName);
            mplayer.prepare();
            mplayer.start();
        }
        catch(IOException error)
        {
            Log.e(TAG,"Prepare failed" +" " + fileName);
        }
    }
    private void stopPlayback()
    {
        //we no longer need the media player;
        mplayer.release();
        mplayer=null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int layoutID = R.layout.audio;
        generator = new FileHandler(getActivity().getApplicationContext());
        View view = inflater.inflate(layoutID,container,false);
        playStopButton = (Button)view.findViewById(R.id.play_button);
        startStopRecordButton = (Button)view.findViewById(R.id.record_button);
        setListeners();
        return view;
    }
    private void setListeners()
    {
        isPlaying=false;
        isRecording=false;
        playStopButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        onPlay(isPlaying);

                        //update text of the button
                        if(!isPlaying)
                            playStopButton.setText(R.string.stop_text);
                        else
                        {
                            playStopButton.setText(R.string.play_text);
                        }

                        isPlaying = !isPlaying;
                    }
                }
        );
        startStopRecordButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        onRecord(isRecording);
                        if(!isRecording)
                        startStopRecordButton.setText(R.string.stop_text);
                        else
                        {
                            startStopRecordButton.setText(R.string.record_text);
                        }
                        isRecording=!isRecording;
                    }
                }
        );
    }

}
