package com.animpriselayout;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;


public class RoundAngleImageView extends ImageView {

    private Paint paint;
    private int ringWidth = 0;
    private int ringColor = Color.WHITE;
    private boolean isCircle = false;
    private Paint paint2;
    private Paint paint_ring;
    private int radius_LB, radius_LT, radius_RB, radius_RT, radius;

    public RoundAngleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RoundAngleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundAngleImageView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView);
            radius = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radius, radius);
            radius_LB = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radius_LB, radius);
            radius_LT = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radius_LT, radius);
            radius_RB = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radius_RB, radius);
            radius_RT = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radius_RT, radius);

            isCircle = a.getBoolean(R.styleable.RoundAngleImageView_isCircle, false);
            ringWidth = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_ringWidth, ringWidth);
            ringColor = a.getColor(R.styleable.RoundAngleImageView_ringColor, Color.WHITE);


        } /*else {
            float density = context.getResources().getDisplayMetrics().density;
            roundWidth = (int) (roundWidth * density);
            roundHeight = (int) (roundHeight * density);
            ringWidth = (int) (ringWidth * density);
        }*/

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);

        paint_ring = new Paint();
        paint_ring.setAntiAlias(true);
        paint_ring.setStyle(Paint.Style.STROKE);
        paint_ring.setStrokeWidth(ringWidth);
        paint_ring.setColor(ringColor);


    }

    Bitmap mRealBitmap;
    Canvas mRealCanvas;

    @Override
    public void draw(Canvas canvas) {
        if (getWidth() <= 0 || getHeight() <= 0) {
            super.draw(canvas);
            return;
        }
        if (mRealBitmap == null) {
            mRealBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        }
        if (mRealCanvas == null) {
            mRealCanvas = new Canvas(mRealBitmap);
        }
        super.draw(mRealCanvas);
        if (isCircle) {
            radius = (int) (getWidth() / 2.0f);
            radius_LB = radius;
            radius_LT = radius;
            radius_RB = radius;
            radius_RT = radius;
        }

        drawLiftUp(mRealCanvas);
        drawRightUp(mRealCanvas);
        drawLiftDown(mRealCanvas);
        drawRightDown(mRealCanvas);

        if (isCircle && ringWidth >= 1) {
            mRealCanvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, getWidth() / 2.0f - ringWidth / 2.0f, paint_ring);
        }
        canvas.drawBitmap(mRealBitmap, 0, 0, paint2);
        try {
//			bitmap.recycle();
        } catch (Exception e) {

        }
    }


    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, radius_LT);
        path.lineTo(0, 0);
        path.lineTo(radius_LT, 0);
        path.arcTo(new RectF(
                        0,
                        0,
                        radius_LT * 2,
                        radius_LT * 2),
                -90,
                -90);
        path.close();
        canvas.drawPath(path, paint);

    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - radius_LB);
        path.lineTo(0, getHeight());
        path.lineTo(radius_LB, getHeight());
        path.arcTo(new RectF(
                        0,
                        getHeight() - radius_LB * 2,
                        0 + radius_LB * 2,
                        getHeight()),
                90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - radius_RB, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radius_RB);
        path.arcTo(new RectF(
                getWidth() - radius_RB * 2,
                getHeight() - radius_RB * 2,
                getWidth(),
                getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), radius_RT);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radius_RT, 0);
        path.arcTo(new RectF(
                        getWidth() - radius_RT * 2,
                        0,
                        getWidth(),
                        0 + radius_RT * 2),
                -90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        if (mRealBitmap!=null){
//            mRealBitmap.recycle();
//            mRealBitmap = null;
//        }
//        super.onDetachedFromWindow();
//    }
}
