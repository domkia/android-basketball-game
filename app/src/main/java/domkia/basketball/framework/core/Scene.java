package domkia.basketball.framework.core;

import android.opengl.GLES30;
import org.joml.Vector3f;
import java.util.ArrayList;

public abstract class Scene
{
    public static Camera activeCamera = null;

    private ArrayList<Node> root;

    public Scene()
    {
        //scene graph
        root = new ArrayList<Node>();

        //initialize camera
        Camera camera = new Camera(60f, 0.01f, 100f);
        camera.LookAt(new Vector3f(0f, 4f, 8f), new Vector3f(-2f, 1f, 0f));
        Camera.mainCamera = camera;
        activeCamera = Camera.mainCamera;

        //set clear color
        GLES30.glClearColor(0.0f, 0.1f, 0.3f, 1f);
    }

    public void Update(float dt)
    {
        for(int i = 0; i < root.size(); i++)
            if(root.get(i).enabled)
                root.get(i).Update(dt);
    }

    protected Node AddNode(Node newNode)
    {
        root.add(newNode);
        return newNode;
    }

    protected final void RemoveNode(Node node)
    {
        root.remove(node);
    }

    public void Render() {

        //Clear the screen
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    }
}