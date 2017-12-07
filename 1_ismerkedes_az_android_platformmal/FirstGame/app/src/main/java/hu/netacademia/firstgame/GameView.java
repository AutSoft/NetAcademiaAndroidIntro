package hu.netacademia.firstgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View {

    private Paint paintBg;
    private Paint paintLine;

    private List<PointF> circles = new ArrayList<PointF>();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        canvas.drawLine(0, 0, getWidth(), getHeight(), paintLine);

        for (PointF circle : circles) {
            canvas.drawCircle(circle.x, circle.y, 70, paintLine);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            circles.add(new PointF(event.getX(), event.getY()));

            if (circles.size() == 5) {
                ((MainActivity)getContext()).changeText("5 Circle Achievement");
            }

            invalidate();
        }

        return true;
    }


    public void clearGameView() {
        circles.clear();
        invalidate();
    }
}
