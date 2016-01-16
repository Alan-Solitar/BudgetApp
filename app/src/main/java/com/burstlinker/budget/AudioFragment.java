package com.burstlinker.budget;
import android.app.Fragment;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;


/**
 * Created by Alan Solitar on 2016/01/16.
 */
public class AudioFragment extends Fragment
{
    private static final String TAG = "AUDIO_TEST";
    private static String fileName ="";

    //Audio Playback related
    private Button playStopButton=null;
    private MediaPlayer mplayer = null;

    //Audio Capture related
    private Button startStopRecordButton=null;
    private MediaRecorder mrecorder = null;

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
        mrecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mrecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mrecorder.setOutputFile(mFileName);
        mrecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
    }



    private void startPlayback()
    {
        mplayer = new MediaPlayer();
        try
        {
            mplayer.setDataSource(fileName);
            mplayer.prepare();
            mplayer.start();
        }
        catch(IOException error)
        {
            Log.e(TAG,"Prepare failed");
        }
    }
    private void stopPlayback()
    {
        //we no longer need the media player;
        mplayer.release();
        mplayer=null;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
