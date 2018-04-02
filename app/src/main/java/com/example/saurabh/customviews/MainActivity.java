package com.example.saurabh.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarSpeed;
    SeekBar seekBarSize;
    Polygons polygons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        polygons = findViewById(R.id.polygons);
        seekBarSpeed = findViewById(R.id.seekbar_speed);
        seekBarSize = findViewById(R.id.seekbar_size);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    switch (seekBar.getId()) {
                        case R.id.seekbar_speed:
                            polygons.setSpeed(i);
                            break;
                        case R.id.seekbar_size:
                            polygons.setWidth(i);
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
        seekBarSpeed.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
