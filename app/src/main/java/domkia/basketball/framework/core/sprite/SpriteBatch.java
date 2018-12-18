package domkia.basketball.framework.core.sprite;

import android.opengl.GLES30;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import domkia.basketball.framework.core.Rect;
import domkia.basketball.framework.graphics.IRenderable;
import domkia.basketball.framework.graphics.Shader;
import domkia.basketball.framework.graphics.Texture;
import domkia.basketball.framework.graphics.mesh.Quad;

public class SpriteBatch implements IRenderable
{
    private Texture texture;
    private Shader shader;
    private final Quad mesh;

    private ArrayList<Sprite> sprites;

    public SpriteBatch(String texturePath)
    {
        texture = new Texture(texturePath, true);
        texture.SetFiltering(GLES30.GL_NEAREST);

        sprites = new ArrayList<>();
        mesh = new Quad();
        shader = new Shader("shaders/sprite.vs", "shaders/sprite.fs");
    }

    private Sprite[] Slice(int rows, int columns)
    {
        Sprite[] sliced = new Sprite[rows * columns];
        int c = 0;
        float w = 1f / columns;
        float h = 1f / rows;
        for(int i = 0;  i < rows; i++)
            for(int j = 0; j < columns; j++) {
                Sprite s = new Sprite(new Rect((float) j / (float) columns, 1.0f - h * (i + 1f), w, h));
                sliced[c] = s;
                c++;
            }
        return sliced;
    }

    public Sprite AddSprite(Rect rect)
    {
        Sprite spr = new Sprite(rect);
        sprites.add(spr);
        return spr;
    }

    public Sprite Get(int index)
    {
        return sprites.get(index);
    }

    public void Remove(Sprite sprite)
    {
        sprites.remove(sprite);
    }

    private FloatBuffer GetSpriteTransformations()
    {
        FloatBuffer buffer = FloatBuffer.allocate(sprites.size() * 16);
        for(int i = 0; i < sprites.size(); i++)
            buffer.put(sprites.get(i).GetModelMatrix().get(FloatBuffer.allocate(16)));
        buffer.position(0);
        return buffer;
    }

    private FloatBuffer GetSpriteUvs()
    {
        FloatBuffer buffer = FloatBuffer.allocate(sprites.size() * 4);
        for(int i = 0; i < sprites.size(); i++)
        {
            Rect r = sprites.get(i).rect;
            Vector4f uvData = new Vector4f(r.Position().x, r.Position().y, r.width, r.height);
            buffer.put(uvData.get(FloatBuffer.allocate(4)));
        }
        buffer.position(0);
        return buffer;
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix)
    {
        mesh.Bind();
        texture.Bind();
        shader.Use();

        //send sprite uvs
        int uvLoc = shader.GetUniformLocation("uvOffset");
        GLES30.glUniform4fv(uvLoc, sprites.size(), GetSpriteUvs());

        //send bunch of matrices representing positions of every instance
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("modelMatrix"), sprites.size(), false, GetSpriteTransformations());

        //view and projection matrices
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("viewMatrix"), 1, false, viewMatrix.get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("projMatrix"), 1, false, projectionMatrix.get(FloatBuffer.allocate(16)));

        //texture sampler
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glUniform1i(shader.GetUniformLocation("tex"), 0);

        //render sprites
        GLES30.glDepthMask(false);
        GLES30.glDrawElementsInstanced(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_INT, 0, sprites.size());
        GLES30.glDepthMask(true);
    }

    public Texture GetTexture()
    {
        return texture;
    }
}