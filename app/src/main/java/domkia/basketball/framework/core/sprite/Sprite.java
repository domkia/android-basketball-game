package domkia.basketball.framework.core.sprite;

import domkia.basketball.framework.core.Node;

public class Sprite extends Node
{
    private int spriteIndex;

    public Sprite(int spriteIndex)
    {
        super("sprite");
        this.spriteIndex = spriteIndex;
    }

    public void SetSpriteIndex(int index)
    {
        spriteIndex = index;
    }

    public int GetSpriteIndex()
    {
        return spriteIndex;
    }
}
