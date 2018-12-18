package domkia.basketball.framework;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import domkia.basketball.GameActivity;
import domkia.basketball.framework.core.Input;
import domkia.basketball.game.Game;

public class GameView extends GLSurfaceView
{
    private Input input;
    private Game game;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        //set up rendering
        setEGLContextClientVersion(3);
        setPreserveEGLContextOnPause(true);
        setRenderer(new Renderer()
        {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig)
            {
                DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
                game = new Game(metrics);
            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int i, int i1) {

            }

            @Override
            public void onDrawFrame(GL10 gl10)
            {
                input.StartOfFrame();
                game.Update();
                game.Render();
            }
        });

        //input
        setOnTouchListener(input = new Input());
    }
}
