package domkia.basketball.framework.core;

import org.joml.Vector2f;

public class Rect
{
    public float x;
    public float y;
    public float width;
    public float height;

    public Rect(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public boolean Contains(Vector2f point)
    {
        return point.x > this.x
                && point.x < this.x + width
                && point.y > this.y
                && point.y < this.y + height;
    }

    public Vector2f Position()
    {
        return new Vector2f(x, y);
    }
}
