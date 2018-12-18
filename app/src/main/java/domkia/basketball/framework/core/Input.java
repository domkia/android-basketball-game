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

            case MotionEvent.ACTION_MOVE:
            {
                TouchInfo t = touches.get(pointerId);
                t.SetCoordinates(e.getX(index), e.getY(index));
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            {
                System.out.println(String.format("touch (id: %d) is UP", pointerId));
                TouchInfo t = touches.get(pointerId);
                t.SetCoordinates(e.getX(index), e.getY(index));
                t.SetState(TouchState.Up);
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
            if(!touchInfo.newFrame)
            {
                touchInfo.newFrame = true;
                if(touchInfo.state == TouchState.Holding)
                    touchInfo.newFrame = false;
            }
            else
            {
                if(touchInfo.state == TouchState.Down)
                    touchInfo.SetState(TouchState.Holding);
                else if(touchInfo.state == TouchState.Up)
                    touches.removeAt(i);
            }
        }
    }

    public class TouchInfo
    {
        Vector2f start;
        Vector2f end;

        private TouchState state;
        private boolean newFrame = false;

        TouchInfo(float startx, float starty)
        {
            this.start = new Vector2f(startx, starty);
            SetState(TouchState.Down);
        }

        private void SetCoordinates(float x, float y)
        {
            end = new Vector2f(x, y);
        }

        public Vector2f GetPosition()
        {
            if(end == null)
                return start;
            return end;
        }

        public void SetState(TouchState newState)
        {
            state = newState;
            newFrame = false;
        }

        public TouchState GetState()
        {
            return state;
        }
    }

    public enum TouchState {
        Down, Holding, Up
    }
}
