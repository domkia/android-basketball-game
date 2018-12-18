package domkia.basketball.framework.core.sprite;

import domkia.basketball.framework.core.Node;
import domkia.basketball.framework.core.Rect;

public class Sprite extends Node
{
    public Rect rect;

    public Sprite(Rect rect)
    {
        this.rect = rect;
    }
}
