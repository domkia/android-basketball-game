package domkia.basketball.framework.ui;

import org.joml.Math;
import org.joml.Vector2f;

public class CircleButton extends Button
{
    private float radius = 1f;

    CircleButton(Vector2f position, float radius)
    {
        super(position);
        this.radius = radius;
    }

    @Override
    public boolean IsInside(Vector2f coord)
    {
        Vector2f offset = new Vector2f(
                Math.abs(position.x - coord.x),
                Math.abs(position.y - coord.y));
        float distance = offset.length();
        if(distance <= radius)
            return true;
        return false;
    }
}
