package domkia.basketball.framework.core;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera extends Node
{
    public static Camera activeCamera;

    private Matrix4f view;
    private Matrix4f projection;

    public Camera(float fovDegrees, float near, float far)
    {
        super("Camera");
        view = new Matrix4f().lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        projection = new Matrix4f().perspective((float) Math.toRadians(fovDegrees), 720f/1280f, near, far);
        Camera.activeCamera = this;
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

    public Matrix4f ViewMatrix()
    {
        return view;
    }

    public Matrix4f ProjectionMatrix()
    {
        return projection;
    }

    public static Vector2f World2Screen(Vector3f position)
    {
        Vector4f pos = new Vector4f(position.x, position.y, position.z, 1.0f);
        Vector4f clipSpace = activeCamera.projection.transform(activeCamera.view.transform(pos));
        Vector3f ndcPos = new Vector3f(clipSpace.x / clipSpace.w, clipSpace.y / clipSpace.w, clipSpace.z / clipSpace.w);
        Vector2f screenCoords = new Vector2f(ndcPos.x * 2.0f + 1.0f, ndcPos.y * 2.0f + 1.0f);
        return screenCoords;
    }

    /*
    public static Vector3f Screen2World(Vector2f screenPos)
    {

        return new Vector3f(0f);
    }
    */
}
