package domkia.basketball.framework.core.sprite;

import android.opengl.GLES30;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import domkia.basketball.framework.graphics.IRenderable;
import domkia.basketball.framework.graphics.Shader;
import domkia.basketball.framework.graphics.Texture;
import domkia.basketball.framework.graphics.mesh.Quad;

//TODO: implement non square texture atlas
public class SpriteBatch implements IRenderable
{
    private final int spritesPerLength;
    private final Texture texture;
    private final Shader shader;
    private final Quad mesh;

    private ArrayList<Sprite> sprites;

    public SpriteBatch(String texturePath, int spritesPerLength)
    {
        this.spritesPerLength = spritesPerLength;
        texture = new Texture(texturePath, true);
        sprites = new ArrayList<Sprite>();
        mesh = new Quad();
        shader = new Shader("shaders/sprite.vs", "shaders/sprite.fs");
    }

    public void Add(Sprite sprite)
    {
        if(sprite != null)
            sprites.add(sprite);
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
        FloatBuffer buffer = FloatBuffer.allocate(sprites.size() * 2);
        for(int i = 0; i < sprites.size(); i++)
        {
            Vector2f uv = GetSpriteUVOffset(sprites.get(i).GetSpriteIndex());
            buffer.put(uv.get(FloatBuffer.allocate(2)));
        }
        buffer.position(0);
        return buffer;
    }

    private Vector2f GetSpriteUVOffset(int spriteIndex)
    {
        float factor = (float)spriteIndex / (float)spritesPerLength;
        float x = factor % 1.0f;
        int row = (int)Math.floor(factor);
        float y = row * (1.0f / spritesPerLength);
        return new Vector2f(x, y);
    }

    @Override
    public void Render(Matrix4f viewMatrix, Matrix4f projectionMatrix)
    {
        mesh.Bind();
        texture.Bind();
        shader.Use();

        //send sprite uvs
        int uvLoc = shader.GetUniformLocation("uvOffset");
        GLES30.glUniform2fv(uvLoc, sprites.size(), GetSpriteUvs());

        //TODO: does this really need to be a whole uniform???
        GLES30.glUniform1f(shader.GetUniformLocation("spriteWidth"), 1.0f / spritesPerLength);

        //send bunch of matrices representing positions of every instance
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("modelMatrix"), sprites.size(), false, GetSpriteTransformations());

        //view and projection matrices
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("viewMatrix"), 1, false, viewMatrix.get(FloatBuffer.allocate(16)));
        GLES30.glUniformMatrix4fv(shader.GetUniformLocation("projMatrix"), 1, false, projectionMatrix.get(FloatBuffer.allocate(16)));

        //texture sampler
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glUniform1i(shader.GetUniformLocation("tex"), 0);

        //render sprites
        //TODO: move glDepthMash to renderqueue section
        GLES30.glDepthMask(false);
        GLES30.glDrawElementsInstanced(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_INT, 0, sprites.size());
        GLES30.glDepthMask(true);
    }

    @Override
    public boolean IsVisible() {
        return true;
    }
}