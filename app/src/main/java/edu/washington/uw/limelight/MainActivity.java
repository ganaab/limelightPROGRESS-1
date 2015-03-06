package edu.washington.uw.limelight;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;


public class MainActivity extends Activity {
    private SpritzerTextView mSpritzerTextView;
    private SeekBar mSeekBarWpm;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String words = "OpenSpritz has nothing to do with Spritz Incorporated. " +
                "This is an open source, community created project, made with love because Spritz is " +
                "such an awesome technique for reading with.";

        mSpritzerTextView = (SpritzerTextView) findViewById(R.id.spritzTV);
        mSpritzerTextView.setSpritzText(words);

        mProgressBar = (ProgressBar) findViewById(R.id.spritz_progress);
        mSpritzerTextView.attachProgressBar(mProgressBar);

        String[] wordsArray = words.split(" ");
        mProgressBar.setMax(wordsArray.length);
        final ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView play = (ImageView) findViewById(R.id.play);
                if(play.getTag() == null || play.getTag().equals("start")) {
                    mSpritzerTextView.play();
                    play.setImageResource(R.drawable.pausebutton);
                    play.setTag("stop");
                } else {
                    mSpritzerTextView.pause();
                    play.setImageResource(R.drawable.playbutton);
                    play.setTag("start");
                }
            }
        });
        mSpritzerTextView.setOnCompletionListener(new Spritzer.OnCompletionListener() {
            @Override
            public void onComplete() {
                mSpritzerTextView.pause();
                play.setImageResource(R.drawable.playbutton);
                play.setTag("start");
            }
        });
        mSeekBarWpm = (SeekBar) findViewById(R.id.seekBarWpm);
        mSeekBarWpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                TextView wpm = (TextView) findViewById(R.id.textView2);
                wpm.setText(mSeekBarWpm.getProgress()*10 + " WPM");
                mSpritzerTextView.setWpm(mSeekBarWpm.getProgress()*10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
