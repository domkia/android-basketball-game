package domkia.basketball.game;

import org.joml.Vector3f;

import domkia.basketball.framework.core.Camera;
import domkia.basketball.framework.core.Scene;

public class IntroCamera extends Camera
{
    private Vector3f startPosition;
    private Vector3f endPosition;
    private float time;

    public IntroCamera(Camera camera)
    {
        super(camera.GetFieldOfView(), camera.GetNearPlane(), camera.GetFarPlane());
        time = 0f;
        this.startPosition = new Vector3f(4f, 8f, 12);
        this.endPosition = camera.GetPosition();
        Scene.activeCamera = this;
    }

    @Override
    public void Update(float dt)
    {
        if(Game.state == Game.GameState.INTRO)
        {
            time += dt / 3f;
            if (time > 1f)
                time = 1f;

            Vector3f eye = startPosition.add(endPosition.sub(startPosition).mul(time));

            LookAt(eye, new Vector3f(6f, 1f, 0f));
        }
        else if(Scene.activeCamera == this)
            Scene.activeCamera = mainCamera;
    }
}
