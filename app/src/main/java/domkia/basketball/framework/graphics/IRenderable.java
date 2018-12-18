package domkia.basketball.framework.graphics;

import org.joml.Matrix4f;

public interface IRenderable
{
    void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix);
}