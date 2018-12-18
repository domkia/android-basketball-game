package domkia.basketball.framework.debug;

import android.opengl.GLES30;

import domkia.basketball.framework.core.Transform;
import domkia.basketball.framework.graphics.IRenderable;
import domkia.basketball.framework.graphics.mesh.Mesh;
import domkia.basketball.framework.graphics.Shader;

import org.joml.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Grid extends Transform implements IRenderable
{
    public static final int LINE_COUNT = 20 + 1;
    private static final int sizeInBytes = LINE_COUNT * 2 * 6 * 4;

    private Mesh mesh;
    private Shader shader;

    public Grid()
    {
        //load vertex data
        mesh = new Mesh(4 * LINE_COUNT, 0);
        mesh.AddBuffer(GLES30.GL_ARRAY_BUFFER, SetupVertexPositions(), sizeInBytes);
        mesh.EnableAttributeArray(0, 0, 3);

        //create shader
        shader = new Shader("shaders/grid.vs", "shaders/grid.fs");
    }

    private final FloatBuffer SetupVertexPositions()
    {
        ByteBuffer bytes = ByteBuffer.allocateDirect(sizeInBytes).order(ByteOrder.nativeOrder());
        FloatBuffer vertices = bytes.asFloatBuffer();
        for(int i = 0; i < LINE_COUNT; i++)
        {
            //horizontal line
            vertices.put(-LINE_COUNT / 2);
            vertices.put(0f);
            vertices.put(LINE_COUNT / 2 - i);

            vertices.put(LINE_COUNT / 2);
            vertices.put(0f);
            vertices.put(LINE_COUNT / 2 - i);

            //vertical line
            vertices.put(-LINE_COUNT / 2 + i);
            vertices.put(0f);
            vertices.put(LINE_COUNT / 2);

            vertices.put(-LINE_COUNT / 2 + i);
            vertices.put(0f);
            vertices.put(-LINE_COUNT / 2);
        }
        vertices.position(0);
        return vertices;
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix)
    {
        shader.Use();

        //transormation matrices
        int viewLoc = shader.GetUniformLocation("viewMatrix");
        GLES30.glUniformMatrix4fv(viewLoc, 1, false, viewMatrix.get(FloatBuffer.allocate(16)));
        int projLoc = shader.GetUniformLocation("projMatrix");
        GLES30.glUniformMatrix4fv(projLoc, 1, false, projectionMatrix.get(FloatBuffer.allocate(16)));

        mesh.Bind();
        GLES30.glLineWidth(2f);
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, LINE_COUNT * 2 * 2);
    }
}
