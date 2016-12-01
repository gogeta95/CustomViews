package com.example.saurabh.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarPitch;
    SeekBar seekBarSize;
    SineView sineView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sineView = (SineView) findViewById(R.id.sineView);
        seekBarPitch = (SeekBar) findViewById(R.id.seekbar_pitch);
        seekBarSize = (SeekBar) findViewById(R.id.seekbar_size);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    switch (seekBar.getId()) {
                        case R.id.seekbar_pitch:
                            sineView.setPitch((float) (i * 0.5));
                            break;
                        case R.id.seekbar_size:
                            sineView.setWaveWidth(i);
                            break;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBarSize.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarPitch.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
