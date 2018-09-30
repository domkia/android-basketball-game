package domkia.basketball.game;

import android.util.DisplayMetrics;

import domkia.basketball.framework.core.Scene;
import domkia.basketball.framework.core.Time;

public class Game
{
    //display dimensions
    public static int WIDTH;
    public static int HEIGHT;

    private Scene activeScene;

    public Game(DisplayMetrics displayMetrics)
    {
        activeScene = new DemoScene();
        WIDTH = displayMetrics.widthPixels;
        HEIGHT = displayMetrics.heightPixels;
    }

    public void Update()
    {
        float deltaTime = Time.deltaTime();
        activeScene.Update(deltaTime);
    }

    public void Render()
    {
        activeScene.Render();
    }
}
