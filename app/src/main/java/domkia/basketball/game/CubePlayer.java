package domkia.basketball.game;

import android.opengl.GLES30;

import domkia.basketball.framework.core.Input;
import domkia.basketball.framework.core.Node;
import domkia.basketball.framework.graphics.IRenderable;
import domkia.basketball.framework.graphics.mesh.Mesh;
import domkia.basketball.framework.graphics.mesh.MeshLoader;
import domkia.basketball.framework.graphics.Shader;
import domkia.basketball.framework.graphics.Texture;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

public class CubePlayer extends Node implements IRenderable
{
    private Mesh mesh;
    private Shader shader;
    private Texture texture;

    public CubePlayer(String name)
    {
        super(name);
        mesh = MeshLoader.LoadFBX("meshes/cube.dmk");
        mesh.EnableAttributeArray(0, 0, 3);
        mesh.EnableAttributeArray(2,1,2);

        shader = new Shader("shaders/unlit_texture.vs", "shaders/unlit_texture.fs");
        texture = new Texture("textures/box.png", false);
    }

    boolean grounded = true;
    float velocity = 0;

    @Override
    public void Update(float dt)
    {
        if(Input.touchCount() > 0)
        {
            Input.TouchInfo touch = Input.GetTouch(0);
            if (touch.getState() == Input.TouchState.Down) {
                velocity = 2;
                grounded = false;
            }
        }
        if(!grounded)
        {
            velocity -= 9.81 * dt;
            Translate(new Vector3f(0f, velocity, 0f));
        }
        if(GetPosition().y < 0.0f) {
            grounded = true;
            velocity = 0;
            SetPosition(new Vector3f(0f));
        }
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix)
    {
        mesh.Bind();
        shader.Use();
        texture.Bind();

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        //send texture
        GLES30.glUniform1i(shader.GetUniformLocation("tex"), GLES30.GL_TEXTURE0);

        //send matrices
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("modelMatrix"), 1, false, GetModelMatrix().get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("viewMatrix"), 1, false, viewMatrix.get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("projMatrix"), 1, false, projectionMatrix.get(FloatBuffer.allocate(16)));

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mesh.indicesCount, GLES30.GL_UNSIGNED_INT, 0);
    }

    @Override
    public boolean IsVisible() {
        return true;
    }
}
