package domkia.basketball.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import domkia.basketball.framework.core.Input;
import domkia.basketball.framework.core.Node;
import domkia.basketball.framework.core.Scene;

public class BallLauncher extends Node
{
    private final float clampDragRadius = 2f;

    private Vector2f startPosition;
    private Vector2f endPosition;
    private Vector2f offset;
    private Ball currentBall = null;
    private boolean hasBall;
    private Trajectory trajectory;

    public BallLauncher(Ball ball, Trajectory trajectory)
    {
        this.currentBall = ball;
        this.offset = new Vector2f();
        this.trajectory = trajectory;
    }

    @Override
    public void Update(float dt)
    {
        super.Update(dt);

        if(Game.state == Game.GameState.BALL_LAUNCHER)
        {
            if(!hasBall)
            {
                Reset();
            }
            if (Input.touchCount() > 0)
            {
                Input.TouchInfo touch = Input.GetTouch(0);
                float distToCamera = Math.abs(Scene.activeCamera.GetPosition().z);
                Vector3f screen2World = Scene.activeCamera.Screen2World(touch.GetPosition(), distToCamera);
                switch (touch.GetState()) {
                    case Down:
                        startPosition = new Vector2f(screen2World.x, screen2World.y);
                        Vector3f ballPosition = currentBall.GetPosition();
                        offset.x = startPosition.x - ballPosition.x;
                        offset.y = startPosition.y - ballPosition.y;
                        currentBall.state = Ball.BallState.AIMING;
                        break;
                    case Holding:
                        endPosition = new Vector2f(screen2World.x, screen2World.y);
                        Aiming();
                        trajectory.DisplayPositions(new Vector2f(startPosition.x - offset.x, startPosition.y - offset.y),
                                new Vector2f(endPosition.x - offset.x, endPosition.y - offset.y));
                        break;
                    case Up:
                        LaunchBall();
                        break;
                }
            }
        }
    }

    private void Reset()
    {
        hasBall = true;
        offset = new Vector2f();

        //TODO: every time a random position
        //TODO: set random camera position
        currentBall.Reset();
        currentBall.SetPosition(new Vector3f(0f, 3f, 0f));
    }

    private void LaunchBall()
    {
        trajectory.Hide();
        Vector2f force = new Vector2f(endPosition.x - startPosition.x, endPosition.y - startPosition.y);
        currentBall.Launch(-force.x, -force.y);
        hasBall = false;
    }

    private void Aiming()
    {
        if (endPosition.distance(startPosition) > clampDragRadius) {
            Vector2f dir = new Vector2f(endPosition.x - startPosition.x, endPosition.y - startPosition.y);
            dir.normalize();
            endPosition.x = startPosition.x + dir.x * clampDragRadius;
            endPosition.y = startPosition.y + dir.y * clampDragRadius;
        }
        Vector3f ballPos = new Vector3f(endPosition.x - offset.x, endPosition.y - offset.y, 0f);
        currentBall.SetPosition(ballPos);
    }
}
