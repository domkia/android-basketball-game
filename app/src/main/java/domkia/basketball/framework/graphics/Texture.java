package domkia.basketball.framework.graphics;

import android.content.res.AssetManager;
import android.opengl.GLES30;

import domkia.basketball.MainActivity;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture
{
    private IntBuffer id;
    private boolean hasAlpha;

    private class TextureData {
        ByteBuffer bytes;
        int width;
        int height;

        TextureData(ByteBuffer buffer, int w, int h) {
            this.bytes = buffer;
            this.width = w;
            this.height = h;
        }
    }

    public Texture(String path, boolean hasAlpha)
    {
        this.hasAlpha = hasAlpha;

        //load PNG
        TextureData texture = LoadPNG(path);

        //generate GL texture
        CreateGLTexture(texture);
    }

    private void CreateGLTexture(TextureData textureData)
    {
        int format = hasAlpha? GLES30.GL_RGBA : GLES30.GL_RGB;

        id = IntBuffer.allocate(1);
        GLES30.glGenTextures(1, id);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, id.get(0));

        //setup texture parameters
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        //set texture data
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, format, textureData.width, textureData.height, 0,
                format, GLES30.GL_UNSIGNED_BYTE, textureData.bytes);

        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
    }

    private TextureData LoadPNG(String path)
    {
        //load png image from assets folder
        AssetManager am = MainActivity.ctx.getAssets();
        ByteBuffer pngData = null;
        PNGDecoder decoder = null;
        try
        {
            PNGDecoder.Format format = hasAlpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB;
            int bytesPerPixel = hasAlpha? 4 : 3;

            InputStream is = am.open(path);
            decoder = new PNGDecoder(is);
            decoder.decideTextureFormat(format);
            pngData = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * bytesPerPixel);
            decoder.decode(pngData, decoder.getWidth() * bytesPerPixel, format);
            pngData.position(0);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new TextureData(pngData, decoder.getWidth(), decoder.getHeight());
    }

    public void SetFiltering(int filtering)
    {
        Bind();
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, filtering);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, filtering);
    }

    public void SetWrapMode(int wrapMode)
    {
        Bind();
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, wrapMode);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, wrapMode);
    }

    public void Bind()
    {
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, getID());
    }

    public int getID()
    {
        return id.get(0);
    }
}