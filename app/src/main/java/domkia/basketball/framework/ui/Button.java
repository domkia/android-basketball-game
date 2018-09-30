package domkia.basketball.framework.ui;

import org.joml.Vector2f;

//TODO: Add callbacks
//TODO: Add textures and rendering code
public abstract class Button
{
    protected Vector2f position;

    Button(Vector2f position)
    {
        this.position = position;
    }

    public abstract boolean IsInside(Vector2f coord);
}
