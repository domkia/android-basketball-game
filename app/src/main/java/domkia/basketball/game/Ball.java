package domkia.basketball.game;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import domkia.basketball.framework.core.Bounds;
import domkia.basketball.framework.core.Node;
import domkia.basketball.framework.core.Rect;
import domkia.basketball.framework.core.sprite.Sprite;
import domkia.basketball.framework.core.sprite.SpriteBatch;
import domkia.basketball.framework.graphics.IRenderable;

public class Ball extends Node implements IRenderable
{
    public BallState state = BallState.IDLE;
    private SpriteBatch batch;
    private Sprite ballSprite;

    private float angle = 0.1f;
    private Vector2f velocity;

    private final float radius = 0.3f;
    private final float bounciness = 0.75f;
    private final float forceMultiplier = 8f;
    private final float gravity = -12f;

    public Ball() {

        batch = new SpriteBatch("textures/ball_alpha.png");
        ballSprite = batch.AddSprite(new Rect(0.0f, 0.0f, 0.5f, 0.5f));
        ballSprite.SetParent(this);
        Scale(new Vector3f(radius * 2f, radius * 2f, radius * 2f));

        Reset();
    }

    @Override
    public void Update(float dt) {
        super.Update(dt);
        switch(state)
        {
            case FLYING:
                Movement(dt);
                break;
            case AIMING:
                //Aiming animation
                velocity.x = 0f;
                velocity.y = 0f;
                break;
            case IDLE:
                Idle(dt);
                break;
        }
    }

    private void Movement(float dt)
    {
        batch.Get(0).SetRotation(new Vector3f(0f, 0f, -angle));
        Vector3f finalPosition = GetPosition();
        Bounds bounds = Arena.bounds;

        //apply gravity
        velocity.y += gravity * dt;

        //If ball hits the ground
        finalPosition.y += velocity.y * dt;
        if(finalPosition.y <= bounds.minY)
        {
            Game.SetState(Game.GameState.MISS);
            finalPosition.y = bounds.minY;
            velocity.y = bounciness * Math.abs(velocity.y);
        }

        //x movement
        finalPosition.x += velocity.x * dt;
        if(finalPosition.x >= bounds.maxX)
        {
            velocity.x = -velocity.x * bounciness;
            finalPosition.x = bounds.maxX;
            if(velocity.y > 0)
                angle = -angle;
        }
        else if(finalPosition.x <= bounds.minX)
        {
            velocity.x = -velocity.x * bounciness;
            finalPosition.x = bounds.minX;

            if(velocity.y < 0)
                angle = -angle;
        }

        SetPosition(finalPosition);
        if(finalPosition.distance(Arena.hoopPosition) < radius
                && Game.state != Game.GameState.GOAL
                && Game.state != Game.GameState.MISS)
        {
            velocity.x *= 0.1f;
            velocity.y *= 0.4f;
            Game.state = Game.GameState.GOAL;
        }
    }

    public void Reset()
    {
        state = BallState.IDLE;
        velocity = new Vector2f();
        batch.Get(0).SetRotation(new Vector3f(0f, 0f, 0f));
    }

    private void Idle(float dt) { }

    public void Launch(float x, float y)
    {
        state = BallState.FLYING;
        Game.state = Game.GameState.BALL_FOLLOWING;
        velocity.x = x * forceMultiplier;
        velocity.y = y * forceMultiplier;
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        if(enabled)
            batch.Render(viewMatrix, projectionMatrix);
    }

    public enum BallState{
        FLYING,
        AIMING,
        IDLE
    }
}
