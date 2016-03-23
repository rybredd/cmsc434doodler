package com.example.sarah.cmsc434doodler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int[] currARGB; // current ARGB value of Paint object
    private int brushSize; // current brush size on canvas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DoodleView dv = (DoodleView) findViewById(R.id.view);

        SeekBar opacityBar = (SeekBar) findViewById(R.id.seekBarOpacity);
        opacityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currARGB = dv.changeOpacity(progress);
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar brushSizeBar = (SeekBar) findViewById(R.id.seekBarBrushSize);
        brushSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushSize = dv.changeBrushSize(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void eraseCanvas(View v) {
//        Toast.makeText(getApplicationContext(), "Erase Button Clicked", Toast.LENGTH_SHORT).show();
        DoodleView dv = (DoodleView) findViewById(R.id.view);
        dv.clearCanvas();
    }

    private void updateColor() {
        Button btn = (Button) findViewById(R.id.currentColor);
        int color = Color.argb(currARGB[0], currARGB[1], currARGB[2], currARGB[3]);
        btn.setBackgroundColor(color);
    }

    public void changeColor(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();

        DoodleView dv = (DoodleView) findViewById(R.id.view);
        currARGB = dv.changeColor(buttonText);

//        Toast.makeText(getApplicationContext(), "Changed color to " + buttonText, Toast.LENGTH_SHORT).show();
        updateColor();
    }

    public void randomColor(View v) {
        DoodleView dv = (DoodleView) findViewById(R.id.view);
        currARGB = dv.setRandomColor();
        updateColor();
    }

    public void undo(View v) {
        DoodleView dv = (DoodleView) findViewById(R.id.view);
        dv.undo();
    }

    public void redo(View v) {
        DoodleView dv = (DoodleView) findViewById(R.id.view);
        dv.redo();
    }
}
