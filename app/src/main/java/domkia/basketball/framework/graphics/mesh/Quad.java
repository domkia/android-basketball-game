package domkia.basketball.framework.graphics.mesh;

import android.opengl.GLES30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Quad extends Mesh
{
    public Quad()
    {
        super(4, 6);

        //setup vertices
        AddBuffer(GLES30.GL_ARRAY_BUFFER, Quad.Vertices(), 4 * 12);
        EnableAttributeArray(0, 0, 3);

        //setup uvs
        AddBuffer(GLES30.GL_ARRAY_BUFFER, Quad.Uvs(), 4 * 8);
        EnableAttributeArray(1, 1, 2);

        //setup indices
        AddBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, Quad.Indices(), 4 * 6);
    }

    private static final FloatBuffer Vertices()
    {
        //define vertex positions
        float[] vertices = new float[12];

        vertices[0] = -0.5f;
        vertices[1] =  0.5f;
        vertices[2] =  0f;

        vertices[3] = 0.5f;
        vertices[4] = 0.5f;
        vertices[5] = 0f;

        vertices[6] = -0.5f;
        vertices[7] = -0.5f;
        vertices[8] = 0f;

        vertices[9] = 0.5f;
        vertices[10] = -0.5f;
        vertices[11] = 0f;

        FloatBuffer fb = FloatBuffer.allocate(12);
        fb.put(vertices);
        fb.position(0);

        return fb;
    }

    private static final IntBuffer Indices()
    {
        //define indices
        IntBuffer indicesData = IntBuffer.allocate(6);
        indicesData.put(0);
        indicesData.put(1);
        indicesData.put(2);

        indicesData.put(1);
        indicesData.put(3);
        indicesData.put(2);
        indicesData.position(0);

        return indicesData;
    }

    private static final FloatBuffer Uvs()
    {
        FloatBuffer uvsData = FloatBuffer.allocate(8);
        uvsData.put(1f);
        uvsData.put(0f);

        uvsData.put(0f);
        uvsData.put(0f);

        uvsData.put(1f);
        uvsData.put(1f);

        uvsData.put(0f);
        uvsData.put(1f);

        uvsData.position(0);
        return uvsData;
    }
}
