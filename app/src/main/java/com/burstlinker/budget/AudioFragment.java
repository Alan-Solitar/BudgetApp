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
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by Alan Solitar on 2016/01/16.
 */

public class AudioFragment extends android.support.v4.app.Fragment
{
    TheListener listener;

    //interface to return recorded files to an activity/fragment
    public interface TheListener
    {
        public void returnFile(String file);
    }
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
    MODE mode;

    //This enum will determine whether this fragment will run headless or inflate a UI
    public enum MODE
    {
        PLAY,PLAY_AND_RECORD,HEADLESS;
    }

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
        if(fileName!=null &&!fileName.isEmpty())
        {
            if (!playing )
            {
                startPlayback();
            } else if (playing ) {
                stopPlayback();
            }
        }
        else
        {
            Toast.makeText(this.getActivity(),"No audio file to play",Toast.LENGTH_SHORT).show();
        }
    }

    private void startRecording()
    {
        mrecorder = new MediaRecorder();
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
        if(listener!=null)
            listener.returnFile(fileName);
    }

    private void startPlayback()
    {

        mplayer = new MediaPlayer();
        if(!fileName.isEmpty()) {
            try {
                mplayer.setDataSource(fileName);
                mplayer.prepare();
                mplayer.start();
            } catch (IOException error) {
                Log.e(TAG, "Prepare failed" + " " + fileName);
            }
        }
    }
    private void stopPlayback()
    {
        //we no longer need the media player;
        if(mplayer!=null)
        {
            mplayer.release();
            mplayer = null;
        }
    }
    @Override
    public void onCreate(Bundle bund)
    {
        super.onCreate(bund);
        Bundle bundle = this.getArguments();
        mode = (MODE)bundle.getSerializable("mode");
        if(mode==MODE.PLAY)
        {
            fileName = bundle.getString("file");
        }
        else
        {
            fileName = "";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=null;
        //we will create an audio file which can be played back in the same view
        if(mode==MODE.PLAY_AND_RECORD)
        {
            listener = (TheListener) getActivity();
            int layoutID = R.layout.audio;
            generator = new FileHandler(getActivity().getApplicationContext());
            view = inflater.inflate(layoutID, container, false);
            playStopButton = (Button) view.findViewById(R.id.play_button);
            startStopRecordButton = (Button) view.findViewById(R.id.record_button);
            playAndRecord();
            return view;
        }
        else if(mode==MODE.PLAY)
        {
            if(mplayer!=null)
            mplayer.reset();
            onPlay(false);
        }

        return view;

    }
    private void playAndRecord()
    {
        playOnClick();
        recordOnClick();

    }
    private void onlyPlay()
    {
        playOnClick();
    }
    public void recordOnClick()
    {
        isRecording = false;
        startStopRecordButton.setOnClickListener(
            new Button.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onRecord(isRecording);
                    if (!isRecording)
                        startStopRecordButton.setText(R.string.stop_text);
                    else
                    {
                        startStopRecordButton.setText(R.string.record_text);
                    }
                    isRecording = !isRecording;
                }
            }
            );
    }
    public void playOnClick()
    {
        isPlaying=false;
        playStopButton.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        onPlay(isPlaying);

                        //update text of the button
                        if (!isPlaying)
                            playStopButton.setText(R.string.stop_text);
                        else
                        {
                            playStopButton.setText(R.string.play_text);
                        }

                        isPlaying = !isPlaying;
                    }
                }
        );
    }
}


