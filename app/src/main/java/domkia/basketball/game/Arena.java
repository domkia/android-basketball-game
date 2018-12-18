package domkia.basketball.game;

import android.opengl.GLES30;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import domkia.basketball.framework.core.Bounds;
import domkia.basketball.framework.core.Node;
import domkia.basketball.framework.graphics.IRenderable;
import domkia.basketball.framework.graphics.Shader;
import domkia.basketball.framework.graphics.Texture;
import domkia.basketball.framework.graphics.mesh.Mesh;
import domkia.basketball.framework.graphics.mesh.MeshLoader;

public class Arena extends Node implements IRenderable
{
    //Collision
    public final static Bounds bounds = new Bounds(-16f, 16f, 0f, 20f, 0f, 0f);
    public final static Vector3f hoopPosition = new Vector3f(bounds.minX + 1.5f, bounds.minY + 3f, 0.0f);
    private static final float rimRadius = 0.05f;
    public final static Vector2f rimFrontPosition = new Vector2f(hoopPosition.x + 0.5f - rimRadius / 2.0f, hoopPosition.y);
    public final static Bounds backboard = new Bounds(bounds.minX, bounds.minX + 1.0f, 2.0f, 4.0f, 0f, 0f);

    private Mesh mesh;
    private Texture texture;
    private Shader shader;

    public Arena()
    {
        mesh = MeshLoader.LoadFBX("meshes/arena.dmk");
        mesh.EnableAttributeArray(0, 0, 3);
        mesh.EnableAttributeArray(2, 1, 2);

        shader = new Shader("shaders/unlit_texture.vs", "shaders/unlit_texture.fs");
        texture = new Texture("textures/arena.png", false);
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix)
    {
        mesh.Bind();
        shader.Use();
        texture.Bind();

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        GLES30.glUniform1i(shader.GetUniformLocation("tex"), GLES30.GL_TEXTURE0);
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("viewMatrix"), 1, false, viewMatrix.get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("projMatrix"), 1, false, projectionMatrix.get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("modelMatrix"), 1, false, GetModelMatrix().get(FloatBuffer.allocate(16)));

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mesh.getBuffer(3).get(0));
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mesh.indicesCount, GLES30.GL_UNSIGNED_INT, 0);
    }
}
