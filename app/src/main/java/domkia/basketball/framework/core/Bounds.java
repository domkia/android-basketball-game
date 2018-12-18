package domkia.basketball.framework.core;

public class Bounds
{
    public float minX;
    public float maxX;
    public float minY;
    public float maxY;
    public float minZ;
    public float maxZ;

    public Bounds(float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public boolean Contains(float x, float y, float z)
    {
        return Contains(x, y) &&
                z > minZ && z < maxZ;
    }

    public boolean Contains(float x, float y)
    {
        return x > minX && x < maxX &&
                y > minY && y < maxY;
    }
}
