package domkia.basketball.framework.core;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import domkia.basketball.game.Game;

public class Camera extends Node
{
    public static Camera mainCamera;

    private Matrix4f view;
    private Matrix4f projection;
    private float near;
    private float far;
    private float fov;

    public Camera(float fovDegrees, float near, float far)
    {
        this.near = near;
        this.far = far;
        this.fov = fovDegrees;
        view = new Matrix4f().lookAt(new Vector3f(0f, 1f, -10f), new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        projection = new Matrix4f().perspective((float)Math.toRadians(fovDegrees), (float)Game.WIDTH/(float)Game.HEIGHT, near, far);
        //projection = new Matrix4f().ortho(-1, 1, -1, 1, near, far);
    }

    public void LookAt(Vector3f from, Vector3f to)
    {
        view = new Matrix4f().lookAt(from, to, new Vector3f(0f, 1f, 0f));
    }

    public Vector3f GetViewDirection()
    {
        Vector3f direction = new Vector3f();
        view.getRow(2, direction);
        return direction;
    }

    @Override
    public void Translate(Vector3f offset)
    {
        view = view.translate(offset);
    }

    @Override
    public Vector3f GetPosition()
    {
        Vector3f eyePosition = new Vector3f();
        view.getColumn(3, eyePosition);
        return eyePosition;
    }

    public Matrix4f ViewMatrix()
    {
        return view;
    }

    public Matrix4f ProjectionMatrix()
    {
        return projection;
    }

    public Vector2f World2Screen(Vector3f position)
    {
        Vector4f pos = new Vector4f(position.x, position.y, position.z, 1.0f);
        Vector4f clipSpace = projection.transform(view.transform(pos));
        Vector3f ndcPos = new Vector3f(clipSpace.x / clipSpace.w, clipSpace.y / clipSpace.w, clipSpace.z / clipSpace.w);
        Vector2f screenCoords = new Vector2f(ndcPos.x * 2.0f + 1.0f, ndcPos.y * 2.0f + 1.0f);
        return screenCoords;
    }

    public Vector3f Screen2World(Vector2f pixelPosition, float z)
    {
        Vector2f screenPos = new Vector2f(
                (pixelPosition.x / Game.WIDTH),
                1.0f - (pixelPosition.y / Game.HEIGHT));
        screenPos.x = screenPos.x * 2f - 1f;
        screenPos.y = screenPos.y * 2f - 1f;

        Matrix4f p = projection;
        Matrix4f v = view;
        Matrix4f inv = new Matrix4f();
        p.invertPerspectiveView(v, inv);

        //Transform point on near plane
        Vector4f n = new Vector4f(screenPos.x, screenPos.y, -1f, 1f);
        n = inv.transform(n);
        n = n.div(n.w);

        //Transform point ont far plane
        Vector4f f = new Vector4f(screenPos.x, screenPos.y, 1f, 1f);
        f = inv.transform(f);
        f = f.div(f.w);

        Vector3f ray = new Vector3f(f.x - n.x, f.y - n.y, f.z - n.z);
        float factor = z / (far - near);
        ray = ray.mul(factor);

        Vector3f pos = new Vector3f(n.x + ray.x, n.y + ray.y, n.z + ray.z);
        //System.out.println(String.format("screenPos %.2f %.2f \nnear: %.4f %.4f %.4f \n",
        //        screenPos.x, screenPos.y, f.x, f.y, f.z));//, far.x, far.y, far.z));
        return pos;
    }

    public float GetFieldOfView(){return fov;}
    public float GetNearPlane(){return near;}
    public float GetFarPlane(){return far;}
}
