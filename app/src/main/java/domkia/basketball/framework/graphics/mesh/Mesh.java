package domkia.basketball.framework.graphics.mesh;

import android.opengl.GLES30;
import android.util.Pair;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

//Mesh class
//Buffer allocation, attribute binding etc.
public class Mesh
{
    private IntBuffer vao;                                              //vertex array object
    private ArrayList<Pair<IntBuffer, Integer>> buffers;                //other buffers

    public final int vertexCount;
    public final int indicesCount;

    public Mesh(int vertexCount, int indicesCount)
    {
        CreateVAO();
        this.vertexCount = vertexCount;
        this.indicesCount = indicesCount;
        buffers = new ArrayList<Pair<IntBuffer, Integer>>();
    }

    private void CreateVAO()
    {
        vao = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays(1, vao);
        Bind();
    }

    public void AddBuffer(int bufferType, Buffer data, int sizeInBytes)
    {
        Bind();
        IntBuffer id = IntBuffer.allocate(1);
        GLES30.glGenBuffers(1, id);
        GLES30.glBindBuffer(bufferType, id.get(0));
        GLES30.glBufferData(bufferType, sizeInBytes, data, GLES30.GL_STATIC_DRAW);
        buffers.add(new Pair<IntBuffer, Integer>(id, bufferType));
    }

    public void EnableAttributeArray(int bufferIndex, int locationIndex, int dimensions)
    {
        Bind();
        Pair<IntBuffer, Integer> buffer = buffers.get(bufferIndex);
        GLES30.glBindBuffer(buffer.second, buffer.first.get(0));
        GLES30.glEnableVertexAttribArray(locationIndex);
        GLES30.glVertexAttribPointer(locationIndex, dimensions, GLES30.GL_FLOAT, false, 0, 0);
    }

    public void Bind()
    {
        GLES30.glBindVertexArray(vao.get(0));
    }
}
