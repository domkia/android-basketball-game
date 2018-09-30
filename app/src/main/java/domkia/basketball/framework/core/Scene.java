package domkia.basketball.framework.core;

import android.opengl.GLES30;

import domkia.basketball.framework.graphics.RenderQueue;
import org.joml.Vector3f;
import java.util.ArrayList;

public abstract class Scene extends RenderQueue
{
    private Camera mainCamera;
    private ArrayList<Node> root;

    public Scene()
    {
        //scene graph
        root = new ArrayList<Node>();

        //initialize camera
        mainCamera = new Camera(60f, 0.01f, 100f);
        mainCamera.LookAt(new Vector3f(0f, 5f, -10f), new Vector3f(0f));

        //set clear color
        GLES30.glClearColor(0.2f, 0.2f, 0.5f, 1f);
    }

    public void Update(float dt)
    {
        for(int i = 0; i < root.size(); i++)
            if(root.get(i).enabled)
                root.get(i).Update(dt);
    }

    protected final void AddNode(Node newNode)
    {
        root.add(newNode);
    }

    protected final void RemoveNode(Node node)
    {
        root.remove(node);
    }

    public final void Render()
    {
        //Clear the screen
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        org.joml.Matrix4f viewMatrix = mainCamera.ViewMatrix();
        org.joml.Matrix4f projMatrix = mainCamera.ProjectionMatrix();

        //Render everything
        Background(viewMatrix, projMatrix);
        Geometry(viewMatrix, projMatrix);
        AlphaTest(viewMatrix, projMatrix);
        Overlay(viewMatrix, projMatrix);
    }
}