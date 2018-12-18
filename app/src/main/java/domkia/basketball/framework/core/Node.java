package domkia.basketball.framework.core;

import org.joml.Matrix4f;

import java.util.ArrayList;

public abstract class Node extends Transform
{
    public boolean enabled;

    private Node parent;
    private ArrayList<Node> childNodes;

    public Node()
    {
        parent = null;
        childNodes = new ArrayList<>();
        enabled = true;
    }

    public void Update(float dt)
    {
        for(int i = 0; i< childNodes.size(); i++)
            if(childNodes.get(i).enabled)
                childNodes.get(i).Update(dt);
    }

    public final void SetParent(Node parent)
    {
        if(parent == null) {
            parent.childNodes.remove(this);
            this.parent = null;
        }
        else{
            this.parent = parent;
            parent.childNodes.add(this);
        }
    }

    public final Node GetChild(int index)
    {
        return childNodes.get(index);
    }

    public final int childCount()
    {
        return childNodes.size();
    }

    @Override
    public Matrix4f GetModelMatrix() {
        if(parent != null)
        {
            Matrix4f mat = parent.modelMatrix.mul(this.modelMatrix);
            return mat;
        }
        return this.modelMatrix;
    }
}
