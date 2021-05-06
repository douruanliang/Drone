package com.dourl.compose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CameraImageView extends AppCompatImageView {
    private Bitmap mBitmap;
    private Paint mPaint;
    private Camera camera = new Camera();
    private Matrix matrix = new Matrix();
    private int mProgress;

    public CameraImageView(Context context) {
        super(context);
        init();
    }
    public CameraImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.supplies);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setProgress(int progress){
        mProgress = progress;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        camera.save();
        canvas.save();
        mPaint.setAlpha(100);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        camera.rotateZ(mProgress);



        int centerX = getWidth()/2/72;
        int centerY = getHeight()/2/72;

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);

        camera.setLocation(centerX,-centerY,camera.getLocationZ());
        camera.getMatrix(matrix);

        canvas.setMatrix(matrix);

        //camera.applyToCanvas(canvas);
        camera.restore();

        super.onDraw(canvas);
        canvas.restore();
    }
}
