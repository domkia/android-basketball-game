package domkia.basketball.framework.core;

import org.joml.AxisAngle4f;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

//Simple transform class handles stuff like
//position, rotation, scale as well as
//transforming from local space to world space
public abstract class Transform
{
    private Matrix4f modelMatrix;

    protected Transform()
    {
        modelMatrix = new Matrix4f().identity();
    }

    public void Translate(Vector3f offset)
    {
        modelMatrix = modelMatrix.translate(offset);
    }

    public void SetPosition(Vector3f newPos)
    {
        modelMatrix = modelMatrix.setTranslation(newPos);
    }

    public void Rotate(Vector3f axis, float angleDegrees)
    {
        modelMatrix = modelMatrix.rotate((float)Math.toRadians(angleDegrees), axis);
    }

    public void SetRotation(Vector3f eulerAngles)
    {
        modelMatrix = modelMatrix.setRotationXYZ(eulerAngles.x, eulerAngles.y, eulerAngles.z);
    }

    public Vector3f GetEulerAngles()
    {
        AxisAngle4f eulerAngles = new AxisAngle4f();
        modelMatrix.getRotation(eulerAngles);
        return new Vector3f(eulerAngles.x, eulerAngles.y, eulerAngles.z);
    }

    public void Scale(Vector3f newScale)
    {
        modelMatrix = modelMatrix.scale(newScale);
    }

    public Vector3f Forward()
    {
        Vector3f forwardVector = new Vector3f();
        modelMatrix.getColumn(2, forwardVector);
        return forwardVector;
    }

    public Vector3f Up()
    {
        Vector3f upVector = new Vector3f();
        modelMatrix.getColumn(1, upVector);
        return upVector;
    }

    public Vector3f Right()
    {
        Vector3f rightVector = new Vector3f();
        modelMatrix.getColumn(0, rightVector);
        return rightVector;
    }

    public Matrix4f GetModelMatrix()
    {
        return modelMatrix;
    }

    public Vector3f GetPosition()
    {
        Vector3f translation = new Vector3f();
        modelMatrix.getColumn(3, translation);
        return translation;
    }

    public Quaternionf GetRotation()
    {
        Quaternionf rotation = new Quaternionf();
        modelMatrix.getNormalizedRotation(rotation);
        return rotation;
    }

    public Vector3f GetScale()
    {
        Vector3f scale = new Vector3f();
        modelMatrix.getScale(scale);
        return scale;
    }
}
