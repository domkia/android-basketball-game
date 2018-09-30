package domkia.basketball.game;

import domkia.basketball.framework.core.Scene;
import domkia.basketball.framework.debug.Grid;
import domkia.basketball.framework.core.sprite.AnimatedSprite;
import domkia.basketball.framework.core.Skybox;
import domkia.basketball.framework.core.sprite.SpriteAnimation;
import domkia.basketball.framework.core.sprite.SpriteBatch;
import domkia.basketball.framework.ui.UICanvas;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DemoScene extends Scene
{
    //private UICanvas ui;
    private SpriteBatch spriteBatch;
    private CubePlayer cube;
    private Skybox skybox;
    private Grid grid;

    public DemoScene()
    {
        super();

        //ui = new UICanvas();
        skybox = new Skybox();
        grid = new Grid();

        cube = new CubePlayer("cube");
        cube.Translate(new Vector3f(0f, 0.1f, 0f));
        AddNode(cube);

        spriteBatch = new SpriteBatch("textures/sprite_sheet_alpha.png", 5);
        AnimatedSprite animated = new AnimatedSprite(new SpriteAnimation(0, 11, 12f));
        animated.Translate(new Vector3f(-2f, 0.0f, 0.0f));
        AddNode(animated);
        spriteBatch.Add(animated);

        animated.PlayAnimation(0);

        //Vector3f cubePosition = animated.GetPosition();
        //org.joml.Vector2f screenPos = Camera.World2Screen(cubePosition);
        //System.out.println(String.format("world pos: %f %f %f is screen pos: %f %f", cubePosition.x, cubePosition.y, cubePosition.z, screenPos.x, screenPos.y));
    }

    @Override
    public final void Update(float dt)
    {
        super.Update(dt);
        //ui.Update();
        LateUpdate(dt);
    }

    private void LateUpdate(float dt)
    {
        cube.Rotate(new Vector3f(0f, 1f, 0f), (float)Math.toRadians(dt * 360.0));
    }

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
}
