package domkia.basketball.framework.graphics.mesh;

import android.opengl.GLES30;
import android.util.Log;

import domkia.basketball.GameActivity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

//Mesh loader class using FBX SDK
public class MeshLoader
{
    public static Mesh LoadFBX(String fbx)
    {
        int vertexCount = 0;
        int indicesCount = 0;
        FloatBuffer vertices = null;
        FloatBuffer uvs = null;
        FloatBuffer normals = null;
        IntBuffer indices = null;

        try
        {
            InputStream is = GameActivity.ctx.getAssets().open(fbx);

            byte[] nameLengthBytes = new byte[4];
            is.read(nameLengthBytes);
            Integer nameLength = ByteBuffer.wrap(nameLengthBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

            byte[] meshNameBytes = new byte[nameLength];
            is.read(meshNameBytes);

            //mesh name
            String meshName = new String(meshNameBytes);

            //get vertex count
            byte[] vertexCountBytes = new byte[4];
            is.read(vertexCountBytes);
            vertexCount = ByteBuffer.wrap(vertexCountBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

            //read vertex positions
            byte[] vertexBytes = new byte[vertexCount * 4 * 3];
            is.read(vertexBytes);
            ByteBuffer vBuffer = ByteBuffer.allocateDirect(vertexCount * 4 * 3).order(ByteOrder.LITTLE_ENDIAN);
            vBuffer.put(vertexBytes);
            vBuffer.position(0);
            vertices = vBuffer.asFloatBuffer();
            /*
            for(int i = 0; i < vertexCount; i++)
            {
                Log.e("VERTEX ", String.format("%d: (%f, %f, %f)", i, vertices.get(i * 3), vertices.get(i * 3 + 1), vertices.get(i * 3 + 2)));
            }*/

            //read normals
            byte[] normalBytes = new byte[vertexCount * 4 * 3];
            is.read(normalBytes);
            ByteBuffer nBuffer = ByteBuffer.allocateDirect(vertexCount * 4 * 3).order(ByteOrder.LITTLE_ENDIAN);
            nBuffer.put(vertexBytes);
            nBuffer.position(0);
            normals = nBuffer.asFloatBuffer();

            //read uvs
            byte[] uvsBytes = new byte[vertexCount * 4 * 2];
            is.read(uvsBytes);
            ByteBuffer uBuffer = ByteBuffer.allocateDirect(vertexCount * 4 * 2).order(ByteOrder.LITTLE_ENDIAN);
            uBuffer.put(uvsBytes);
            uBuffer.position(0);
            uvs = uBuffer.asFloatBuffer();

            //read indices
            byte[] indicesCountBytes = new byte[4];
            is.read(indicesCountBytes);
            indicesCount = Integer.reverseBytes(ByteBuffer.wrap(indicesCountBytes).getInt());

            byte[] indicesBytes = new byte[indicesCount * 4];
            is.read(indicesBytes);
            ByteBuffer iBuffer = ByteBuffer.allocateDirect(indicesCount * 4).order(ByteOrder.LITTLE_ENDIAN);
            iBuffer.put(indicesBytes);
            iBuffer.position(0);
            indices = iBuffer.asIntBuffer();

            byte[] triangleCountBytes = new byte[4];
            is.read(triangleCountBytes);
            int triangleCount = Integer.reverseBytes(ByteBuffer.wrap(triangleCountBytes).getInt());

            is.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(vertices == null || normals == null || uvs == null || indices == null)
            try {
                throw new Exception("buffer null");
            } catch (Exception e) {
                e.printStackTrace();
            }

        Mesh mesh = new Mesh(vertexCount, indicesCount);
        mesh.AddBuffer(GLES30.GL_ARRAY_BUFFER, vertices, vertexCount * 3 * 4);
        mesh.AddBuffer(GLES30.GL_ARRAY_BUFFER, normals, vertexCount * 3 * 4);
        mesh.AddBuffer(GLES30.GL_ARRAY_BUFFER, uvs, vertexCount * 2 * 4);
        mesh.AddBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, indices, indicesCount * 4);

        return mesh;
    }
}
