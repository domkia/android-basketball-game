package domkia.basketball.framework.core.sprite;

import java.util.ArrayList;

public class AnimatedSprite extends Sprite
{
    public ArrayList<SpriteAnimation> animations;
    private int currentAnimation;

    public AnimatedSprite(SpriteAnimation animation)
    {
        super(animation.startFrame);
        animations = new ArrayList<SpriteAnimation>();
        animations.add(animation);
        currentAnimation = 0;
    }

    public void PlayAnimation(int index)
    {
        if(animations.size() == 0)
            return;
        if(animations.get(index).IsPlaying())
            return;
        currentAnimation = index;
        animations.get(index).Play(true);
    }

    public void AddAnimation(SpriteAnimation newAnimation)
    {
        animations.add(newAnimation);
    }

    @Override
    public void Update(float dt)
    {
        super.Update(dt);
        if(animations.size() == 0)
            return;
        SpriteAnimation anim = animations.get(currentAnimation);
        anim.Update(dt);
        SetSpriteIndex(anim.GetCurrentFrame());
    }
}

