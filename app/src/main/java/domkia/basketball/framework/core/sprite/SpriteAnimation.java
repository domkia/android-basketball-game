package domkia.basketball.framework.core.sprite;

import org.joml.Math;

//Sprite animation class
public class SpriteAnimation
{
    final int startFrame;
    final int count;

    private float speed;
    private boolean loop;
    private boolean playing;
    private float animationProgress;

    public SpriteAnimation(int start, int count, float animationSpeed)
    {
        this.startFrame = start;
        this.count = count;
        this.speed = animationSpeed;
        this.playing = false;
    }

    public void Play(boolean loop)
    {
        this.playing = true;
        this.loop = loop;
        this.animationProgress = 0.0f;
    }

    public void Stop()
    {
        this.playing = false;
        this.loop = false;
        this.animationProgress = 0.0f;
    }

    void Update(float dt)
    {
        if(!loop && animationProgress >= 1.0f)
            return;

        animationProgress += speed * dt / (float)count;
        if(animationProgress > 1.0f)
        {
            animationProgress = 1.0f;
            if (loop)
                animationProgress = 0.0f;
        }
    }

    public void SetSpeed(float speed)
    {
        this.speed = speed;
    }

    public int GetCurrentFrame()
    {
        return (int) Math.floor(count * animationProgress) + startFrame;
    }

    public boolean IsPlaying()
    {
        return playing;
    }
}
