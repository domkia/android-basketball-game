package domkia.basketball.framework.ui;

import domkia.basketball.framework.core.Input;
import domkia.basketball.game.Game;

import org.joml.Vector2f;

public class UICanvas
{
    CircleButton button;

    public UICanvas()
    {
        button = new CircleButton(new Vector2f(0.5f, 0.5f), 0.1f);
    }

    public void OnClick(Input.TouchInfo touch)
    {
        Vector2f pos = touch.GetPosition();
        pos.x = pos.x / Game.WIDTH;
        pos.y = pos.y / Game.HEIGHT;
        if(button.IsInside(pos))
            System.out.println("Clicked on a button");
        else
            System.out.println("OUTSIDE");
    }

    public void Update()
    {
        if(Input.touchCount() == 1)
        {
            if(Input.GetTouch(0).getState() == Input.TouchState.Down)
                OnClick(Input.GetTouch(0));
        }
    }
}
