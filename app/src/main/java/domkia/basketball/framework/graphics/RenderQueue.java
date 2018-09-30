package domkia.basketball.framework.graphics;

import org.joml.Matrix4f;

public abstract class RenderQueue
{
    protected void Background(Matrix4f view, Matrix4f proj){}
    protected void Geometry(Matrix4f view, Matrix4f proj){}
    protected void AlphaTest(Matrix4f view, Matrix4f proj){}
    protected void Overlay(Matrix4f view, Matrix4f proj){}
}
