package domkia.basketball.framework.core;

import android.opengl.GLES30;
import org.joml.Matrix4f;
import java.nio.FloatBuffer;

import domkia.basketball.framework.graphics.IRenderable;
import domkia.basketball.framework.graphics.Shader;
import domkia.basketball.framework.graphics.mesh.Quad;

public class Skybox implements IRenderable
{
    private Quad mesh;
    private Shader shader;

    public Skybox()
    {
        mesh = new Quad();
        shader = new Shader("shaders/skybox_gradient.vs", "shaders/skybox_gradient.fs");
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix)
    {
        GLES30.glDisable(GLES30.GL_DEPTH_TEST);

        shader.Use();
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("viewMatrix"), 1, false, viewMatrix.get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("projMatrix"), 1, false, projectionMatrix.get(FloatBuffer.allocate(16)));

        mesh.Bind();
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_INT, 0);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    }

    @Override
    public boolean IsVisible() {
        return true;
    }
}
