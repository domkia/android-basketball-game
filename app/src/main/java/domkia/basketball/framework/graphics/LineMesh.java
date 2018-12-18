package domkia.basketball.framework.graphics;

import android.opengl.GLES30;
import java.nio.FloatBuffer;
import domkia.basketball.framework.graphics.mesh.Mesh;

public class LineMesh extends Mesh
{
    FloatBuffer vertexData;

    public LineMesh(int segments)
    {
        super(2 + segments * 2, segments * 6);
        //GLES30.glMapBufferRange(GLES30.GL_ARRAY_BUFFER, 0, )
    }
}
