package domkia.basketball.game;

import android.util.DisplayMetrics;
import domkia.basketball.framework.core.Scene;

public class Game
{
    //display dimensions
    public static int WIDTH;
    public static int HEIGHT;

    public static GameState state;

    public static Scene activeScene;
    public static final int maxBalls = 5;
    public static int remainingBalls;

    private final float introDuration = 1f;
    private final float nextBallDuration = 1.5f;
    private final float gameOverDuration = 2f;
    private float timer;

    public Game(DisplayMetrics displayMetrics)
    {
        WIDTH = displayMetrics.widthPixels;
        HEIGHT = displayMetrics.heightPixels;

        state = GameState.INTRO;
        timer = 0f;
        activeScene = new PracticeScene();
        remainingBalls = maxBalls;
    }

    public static float deltaTime()
    {
        return 1f/60f;
    }

    public void Update()
    {
        float deltaTime = deltaTime();
        switch (state){
            case INTRO:
                timer += deltaTime;
                if(timer >= introDuration)
                {
                    timer = 0f;
                    state = GameState.BALL_LAUNCHER;
                }
                break;
            case MISS:
                timer += deltaTime;
                if(timer >= nextBallDuration)
                {
                    timer = 0f;
                    remainingBalls--;
                    if(remainingBalls > 0)
                        state = GameState.BALL_LAUNCHER;
                    else
                        state = GameState.GAME_OVER;
                }
                break;
            case GOAL:
                timer += deltaTime;
                if(timer >= 2f){
                    timer = 0f;
                    remainingBalls++;
                    state = GameState.BALL_LAUNCHER;
                }
                break;
            case GAME_OVER:
                timer += deltaTime;
                if(timer >= gameOverDuration)
                {
                    timer = 0f;
                    //TODO: exit to main menu or something
                }
                break;
        }
        activeScene.Update(deltaTime);
    }

    public static void SetState(GameState newState)
    {
        if(state == GameState.GOAL && newState == GameState.MISS)
            return;

        state = newState;
    }

    public enum GameState
    {
        INTRO,
        BALL_LAUNCHER,
        BALL_FOLLOWING,
        MISS,
        GOAL,
        GAME_OVER
    }

    public void Render()
    {
        activeScene.Render();
    }
}


