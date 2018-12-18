package domkia.basketball.game;

import org.joml.Vector3f;
import domkia.basketball.framework.core.Camera;
import domkia.basketball.framework.core.Scene;

public class BallFollowCamera extends Camera
{
    private Ball target;
    private Vector3f offset;

    public BallFollowCamera(Ball ball) {
        super(40f, 0.01f, 100f);
        this.target = ball;
        this.offset = new Vector3f(2f, 1f, 5f);
    }

    @Override
    public void Update(float dt)
    {
        if(Game.state == Game.GameState.BALL_FOLLOWING)
        {
            Scene.activeCamera = this;

            Vector3f position = target.GetPosition();
            position.x += offset.x;
            position.y += offset.y;
            position.z += offset.z;
            SetPosition(position);

            LookAt(position, target.GetPosition());
        }
        else if(Game.state == Game.GameState.GOAL)
        {
            //
        }
        else
        {
            if(Scene.activeCamera == this)
                Scene.activeCamera = Camera.mainCamera;
        }
    }
}
