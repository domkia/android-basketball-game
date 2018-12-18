package domkia.basketball.game;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import domkia.basketball.framework.core.Rect;
import domkia.basketball.framework.core.Scene;
import domkia.basketball.framework.core.sprite.SpriteBatch;
import domkia.basketball.framework.debug.Grid;

public class PracticeScene extends Scene {

    private BallFollowCamera followCamera;
    private Ball ball;
    private BallLauncher launcher;
    private Arena arena;
    private Grid grid;
    private SpriteBatch shadow;
    private Trajectory trajectory;

    public PracticeScene() {
        super();
        activeCamera.LookAt(new Vector3f(-2f, 3f, 7f), new Vector3f(-4, 2f, 0f));
        ball = (Ball)AddNode(new Ball());
        followCamera = (BallFollowCamera)AddNode(new BallFollowCamera(ball));
        trajectory = new Trajectory();
        launcher = (BallLauncher)AddNode(new BallLauncher(ball, trajectory));
        arena = (Arena)AddNode(new Arena());
        grid = new Grid();

        shadow = new SpriteBatch("textures/dot.png");
        shadow.AddSprite(new Rect(0.0f, 0.0f, 1.0f, 1.0f));
        shadow.Get(0).SetRotation(new Vector3f((float)Math.toRadians(90f), 0f, 0f));
    }

    @Override
    public void Update(float dt) {
        super.Update(dt);

        Vector3f ballPos = ball.GetPosition();
        ballPos.y = 0.05f;          //Some margin off the ground to avoid clipping
        shadow.Get(0).SetPosition(ballPos);
    }

    @Override
    public void Render() {
        super.Render();

        Matrix4f view = activeCamera.ViewMatrix();
        Matrix4f proj = activeCamera.ProjectionMatrix();

        //grid.Render(view, proj);
        arena.Render(view, proj);
        ball.Render(view, proj);
        shadow.Render(view, proj);
        trajectory.Render(view, proj);
    }
}
