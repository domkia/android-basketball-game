package domkia.basketball.framework.core.sprite;

import java.util.ArrayList;

public class AnimatedSprite extends Sprite
{
    public ArrayList<SpriteAnimation> animations;
    private int currentAnimation;

    public AnimatedSprite(SpriteBatch batch, SpriteAnimation... animation)
    {
        super(batch.Get(animation[0].GetCurrentFrame()).rect);
        animations = new ArrayList<SpriteAnimation>();
        for(int i = 0; i < animation.length; i++)
            animations.add(animation[i]);
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

    public void UpdateAnimation(float dt)
    {
        if(animations.size() == 0)
            return;
        SpriteAnimation anim = animations.get(currentAnimation);
        anim.Update(dt);
        //index = anim.GetCurrentFrame();
    }
}

