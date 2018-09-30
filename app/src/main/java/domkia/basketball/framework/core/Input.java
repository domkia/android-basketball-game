package domkia.basketball.framework.core;

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import org.joml.Vector2f;

public class Input implements View.OnTouchListener
{
    private static SparseArray<TouchInfo> touches;

    public Input()
    {
        touches = new SparseArray<>();
    }

    @Override
    public boolean onTouch(View view, MotionEvent e)
    {
        int index = e.getActionIndex();
        int pointerId = e.getPointerId(index);
        int action = e.getActionMasked();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                System.out.println(String.format("touch (id: %d) is DOWN", pointerId));
                TouchInfo t = new TouchInfo(e.getX(index), e.getY(index));
                touches.put(pointerId, t);
                break;
            }

            /*
            case MotionEvent.ACTION_MOVE:
            {
                touches.get(pointerId).SetEndCoordinates(e.getX(index), e.getY(index));
            }
            */

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            {
                System.out.println(String.format("touch (id: %d) is UP", pointerId));
                TouchInfo t = touches.get(pointerId);
                t.SetEndCoordinates(e.getX(index), e.getY(index));
                t.state = TouchState.Up;
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            {
                touches.removeAt(pointerId);
                break;
            }
        }
        return true;
    }

    public static TouchInfo GetTouch(int index)
    {
        return touches.valueAt(index);
    }

    public static int touchCount()
    {
        return touches.size();
    }

    public void StartOfFrame()
    {
        for(int i = 0; i < touchCount(); i++)
        {
            TouchInfo touchInfo = touches.valueAt(i);
            if (touchInfo.state == TouchState.Down && !touchInfo.nextFrame)
                touchInfo.nextFrame = true;
            else
                touchInfo.state = TouchState.Holding;
        }
    }

    public void EndOfFrame()
    {
        for(int i = 0; i < touchCount(); i++)
        {
            TouchInfo touchInfo = touches.valueAt(i);
            if (touchInfo.state == TouchState.Up && touchInfo.nextFrame)
                touches.removeAt(i);
        }
    }

    public class TouchInfo
    {
        Vector2f start;
        Vector2f end;

        private TouchState state;
        private boolean nextFrame = false;              //carry the touch to the next frame if it occured at the end of the frame
                                                        //so there's no input skipping

        TouchInfo(float startx, float starty)
        {
            this.start = new Vector2f(startx, starty);
            this.state = TouchState.Down;
        }

        private void SetEndCoordinates(float x, float y)
        {
            end = new Vector2f(x, y);
        }

        public Vector2f GetDelta()
        {
            return end.sub(start);
        }

        public Vector2f GetPosition()
        {
            if(end == null)
                return start;
            return end;
        }

        public TouchState getState()
        {
            return state;
        }
    }

    public enum TouchState
    {
        Down,
        Holding,
        Up
    }
}
