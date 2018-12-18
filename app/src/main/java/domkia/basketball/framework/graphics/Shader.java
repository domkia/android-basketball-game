package domkia.basketball.framework.graphics;

import android.opengl.GLES30;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;

import domkia.basketball.GameActivity;

public class Shader
{
    private int program;

    private String LoadFromFile(String path)
    {
        BufferedReader br = null;
        StringBuilder sb = null;
        try
        {
            InputStream is = GameActivity.ctx.getAssets().open(path);
            br = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }
            sb.deleteCharAt(sb.length() - 1);
            br.close();
            return sb.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Shader(String vertexPath, String fragmentPath)//, ArrayList<String> attributes)
    {
        //load shader sources from files
        String vertexSource = LoadFromFile(vertexPath);
        String fragmentSource = LoadFromFile(fragmentPath);

        IntBuffer compiled = IntBuffer.allocate(3);
        int vertex, fragment;

        //create and compile vertex shader
        vertex = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
        GLES30.glShaderSource(vertex, vertexSource);
        GLES30.glCompileShader(vertex);
        GLES30.glGetShaderiv(vertex, GLES30.GL_COMPILE_STATUS, compiled);
        String vertexLog = GLES30.glGetShaderInfoLog(vertex);
        if(!vertexLog.isEmpty())
            Log.wtf("SHADER", String.format("There is a problem with vertex shader: %s", vertexLog));

        //fragment shader
        fragment = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(fragment, fragmentSource);
        GLES30.glCompileShader(fragment);
        GLES30.glGetShaderiv(fragment, GLES30.GL_COMPILE_STATUS, compiled);
        String fragmentLog = GLES30.glGetShaderInfoLog(fragment);
        if(!fragmentLog.isEmpty())
            Log.wtf("SHADER", String.format("There is a problem with fragment shader: %s", fragmentLog));

        //create program
        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertex);
        GLES30.glAttachShader(program, fragment);

        GLES30.glLinkProgram(program);
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, compiled);

        //delete unused shaders
        GLES30.glDeleteShader(vertex);
        GLES30.glDeleteShader(fragment);
    }

    public void Use()
    {
        GLES30.glUseProgram(program);
    }

    public int GetUniformLocation(String uniformName)
    {
        int loc = -1;
            try
            {
                loc = GLES30.glGetUniformLocation(program, uniformName);
                if(loc < 0)
                    Log.wtf("SHADER", String.format("glGenUniformLocation of name: %s", uniformName));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        return loc;
    }
}
