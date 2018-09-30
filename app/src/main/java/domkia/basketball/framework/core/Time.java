package domkia.basketball.framework.core;

public abstract class Time
{
    private static float timeSinceGameStarted;
    private static long frameStart, frameEnd, dt;

    public static void Reset()
    {
        timeSinceGameStarted = 0;
        frameStart = frameEnd = dt = 0;
    }

    public static float deltaTime()
    {
        //time in seconds
        return dt / 1000f;
    }

    public static float getTimeSinceGameStarted()
    {
        return System.currentTimeMillis() - timeSinceGameStarted;
    }

    public static void Update()
    {
        frameEnd = System.currentTimeMillis();
        dt = frameEnd - frameStart;
        frameStart = System.currentTimeMillis();
    }
}
