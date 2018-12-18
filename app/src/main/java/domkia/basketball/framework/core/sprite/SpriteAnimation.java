package domkia.basketball.framework.core.sprite;

import org.joml.Math;

public class SpriteAnimation
{
    private final int[] frames;     //sprite indexes
    private final int count;

    private float speed;
    private boolean loop;
    private boolean playing;
    private float animationProgress;

    public SpriteAnimation(int[] frames, float speed, boolean loop)
    {
        this.frames = frames;
        this.count = frames.length;
        this.speed = speed;
        this.loop = loop;
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
            Stop();

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
        int index = (int) Math.floor(count * animationProgress);
        return frames[index];
    }

    public boolean IsPlaying()
    {
        return playing;
    }
}
