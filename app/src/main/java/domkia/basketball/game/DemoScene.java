package domkia.basketball.game;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import domkia.basketball.framework.core.Scene;
import domkia.basketball.framework.core.Skybox;
import domkia.basketball.framework.core.sprite.AnimatedSprite;
import domkia.basketball.framework.core.sprite.SpriteAnimation;
import domkia.basketball.framework.core.sprite.SpriteBatch;
import domkia.basketball.framework.debug.Grid;

public class DemoScene extends Scene
{
    private SpriteBatch spriteBatch;
    private CubePlayer cube;
    private Skybox skybox;
    private Grid grid;
    private Arena arena;

    public DemoScene()
    {
        super();

        //ui = new UICanvas();
        //skybox = new Skybox();
        grid = new Grid();

        arena = new Arena();
        AddNode(arena);

        //cube = new CubePlayer();
        //cube.Translate(new Vector3f(0f, 0.1f, 0f));
        //AddNode(cube);



        activeCamera.LookAt(new Vector3f(0f, 10f, -20f), new Vector3f(0f, 0f, 0f));

        /*
        spriteBatch = new SpriteBatch("textures/sprite_sheet_alpha.png", 5);
        AnimatedSprite animated = new AnimatedSprite(new SpriteAnimation(0, 11, 12f));
        animated.Translate(new Vector3f(-2f, 0.0f, 0.0f));
        //AddNode(animated);
        spriteBatch.Add(animated);

        animated.PlayAnimation(0);
        */

        //Vector3f cubePosition = animated.GetPosition();
        //org.joml.Vector2f screenPos = Camera.World2Screen(cubePosition);
        //System.out.println(String.format("world pos: %f %f %f is screen pos: %f %f", cubePosition.x, cubePosition.y, cubePosition.z, screenPos.x, screenPos.y));
    }

    @Override
    public final void Update(float dt)
    {
        super.Update(dt);
        //arena.Rotate(new Vector3f(1f, 1f, 0f), (float)Math.toRadians(dt * 720.0));
    }

    @Override
    public void Render() {
        super.Render();

        Matrix4f view = activeCamera.ViewMatrix();
        Matrix4f proj = activeCamera.ProjectionMatrix();

        grid.Render(view, proj);
        arena.Render(view, proj);
        //cube.Render(view, proj);

    }

    /*
    @Override
    protected void Background(Matrix4f view, Matrix4f proj)
    {
        skybox.Render(view, proj);
    }

    @Override
    protected void Geometry(Matrix4f view, Matrix4f proj)
    {
        grid.Render(view, proj);
        cube.Render(view, proj);
    }

    @Override
    protected void AlphaTest(Matrix4f view, Matrix4f proj)
    {
        spriteBatch.Render(view, proj);
    }
    */
}
