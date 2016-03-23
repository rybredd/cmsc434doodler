package com.example.sarah.cmsc434doodler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import dalvik.annotation.TestTargetClass;

/**
 * TODO: document your custom view class.
 */
public class DoodleView extends View {
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Paint> paints = new ArrayList<Paint>();

    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Paint> undonePaints = new ArrayList<Paint>();

    int brushSize = 5;
    int opacity = 255;
    int[] RGB = {0, 0, 0};

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        newAction();
    }

    public Path getPath() {
        return paths.get(paths.size()-1);
    }

    public Paint getPaint() {
        return paints.get(paints.size()-1);
    }

    public int[] getCurrentARGB() {
        return new int[]{opacity, RGB[0], RGB[1], RGB[2]};
    }

    public void clearCanvas() {
        for (Path p : paths) {
            p.reset();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);
            Paint paint = paints.get(i);

            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        Path path = getPath();

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                undonePaints = new ArrayList<Paint>();
                undonePaths = new ArrayList<Path>();
                break;
        }

        invalidate();
        return true;
    }

    public void newAction() {
        Paint paint = new Paint();
        Path path = new Path();

        paint.setARGB(opacity, RGB[0], RGB[1], RGB[2]);
        paint.setStrokeWidth(brushSize);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        paths.add(path);
        paints.add(paint);
    }

    public int[] changeColor(String color) {
        switch (color) {
            case "Red":
                RGB = new int[]{255, 0, 0};
                break;
            case "Green":
                RGB = new int[]{0, 255, 0};
                break;
            case "Blue":
                RGB = new int[]{0, 0, 255};
                break;
        }

        newAction();
        return getCurrentARGB();
    }

    public int[] changeOpacity(int alpha) {
        opacity = alpha;
        newAction();

        return getCurrentARGB();
    }

    public int changeBrushSize(int size) {
        brushSize = size+1;
        newAction();

        return brushSize;
    }

    public int[] setRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt((255-0)+1) + 0;
        int g = rand.nextInt((255-0)+1) + 0;
        int b = rand.nextInt((255-0)+1) + 0;

        RGB = new int[]{r, g, b};
        newAction();

        return getCurrentARGB();
    }

    public void undo() {
        if (!paths.isEmpty() && !paints.isEmpty()) {
            Paint oldPaint = paints.remove(paints.size()-1);
            Path oldPath = paths.remove(paths.size()-1);

            undonePaints.add(oldPaint);
            undonePaths.add(oldPath);

            invalidate();
        }
    }

    public void redo() {
        if (!undonePaths.isEmpty() && !undonePaints.isEmpty()) {
            Paint newPaint = undonePaints.remove(undonePaints.size()-1);
            Path newPath = undonePaths.remove(undonePaths.size()-1);

            paints.add(newPaint);
            paths.add(newPath);

            invalidate();
        }
    }
}
