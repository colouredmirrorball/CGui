package cmb.soft.cgui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CMB on 27/06/2015.
 */
public class CRectangle
{

    private final List<ChangeListener> listeners = new ArrayList<>();

    protected float w;
    protected float h;
    protected float neww;
    protected float newh;
    protected float speed = 0.5f;
    private boolean updating = false;


    public CRectangle(int width, int height)
    {
        w = width;
        h = height;
        neww = width;
        newh = height;
    }

    /**
     * Optional but required for animating
     */

    public void update()
    {
        if (updating)
        {
            if (w != neww || h != newh)
            {
                w = w - (w - neww) * speed;
                h = h - (h - newh) * speed;
            } else
            {
                updating = false;
            }
        }
    }

    public void setNewRectangle(int width, int height)
    {
        neww = width;
        neww = height;
        if (w != neww || h != newh)
        {
            updating = true;
            listeners.forEach(ChangeListener::notifyChanged);
        }
    }

    public float getWidth()
    {
        return w;
    }

    public float getHeight()
    {
        return h;
    }

    public void addSizeChangeListener(ChangeListener listener)
    {
        listeners.add(listener);
    }
}
