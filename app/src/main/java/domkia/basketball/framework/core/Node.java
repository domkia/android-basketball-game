package domkia.basketball.framework.core;

import java.util.ArrayList;

public abstract class Node extends Transform
{
    public final String name;
    public boolean enabled;

    //todo: implement this
    private Node parent;
    private ArrayList<Node> childNodes;

    public Node(String name)
    {
        this.name = name;
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
        this.parent = parent;
    }

    public final Node GetChild(int index)
    {
        return childNodes.get(index);
    }

    public final int childCount()
    {
        return childNodes.size();
    }
}
