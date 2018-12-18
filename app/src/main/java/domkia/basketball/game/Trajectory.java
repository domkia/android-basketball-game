package domkia.basketball.game;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import domkia.basketball.framework.core.Rect;
import domkia.basketball.framework.core.sprite.Sprite;
import domkia.basketball.framework.core.sprite.SpriteBatch;
import domkia.basketball.framework.graphics.IRenderable;

public class Trajectory implements IRenderable
{
    public final int segments = 10;
    public final int spacing = 3;
    public final float size = 0.1f;

    private SpriteBatch sprites;
    private boolean show = false;

    public Trajectory() {
        sprites = new SpriteBatch("textures/pentagon.png");
        Rect rect = new Rect(0.0f, 0.0f, 1.0f, 1.0f);
        for(int i = 0; i < segments; i++) {
            Sprite spr = sprites.AddSprite(rect);
            spr.Scale(new Vector3f(size, size, size));
        }
    }

    public void DisplayPositions(Vector2f startPosition, Vector2f endPosition)
    {
        show = true;
        final float g = -12f;
        final float f = 6f;
        Vector2f force = new Vector2f(startPosition.x - endPosition.x, startPosition.y - endPosition.y);
        force.x *= f;
        force.y *= f;
        float deltaTime = Game.deltaTime();
        for(int i = 0; i < segments * spacing; i++)
        {
            force.y += deltaTime * g;
            startPosition.y += force.y * deltaTime;
            startPosition.x += force.x * deltaTime;
            if(i % spacing == 0) {
                Vector3f newPosition = new Vector3f(startPosition.x, startPosition.y, 0.0f);
                Sprite spr = sprites.Get(i / spacing);
                spr.SetPosition(newPosition);
            }
        }
    }

    public void Hide()
    {
        show = false;
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        if(show)
            sprites.Render(viewMatrix, projectionMatrix);
    }
}
