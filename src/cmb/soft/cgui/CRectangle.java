package cmb.soft.cgui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CMB on 27/06/2015.
 * <p>
 * The CRectangle deals with everything related to position and size. In it simplest form, it merely contains x, y,
 * width and height coordinates. But it can also define a position relative to another rectangle, and recalculate its
 * position when it determines that the parent component has changed. It can also snap to specific locations of a
 * variable parent CRectangle. Smooth animations are built in.
 */
public class CRectangle
{

    public final static int ABSOLUTE = 0;
    public final static int RELATIVE_TO_BOUNDS = 1;

    public final static int UPPER_LEFT = -1;
    public final static int UPPER_RIGHT = -2;
    public final static int LOWER_LEFT = -3;
    public final static int LOWER_RIGHT = -4;
    public final static int UPPER_CENTER = -5;
    public final static int LOWER_CENTER = -6;
    public final static int LEFT_CENTER = -7;
    public final static int RIGHT_CENTER = -8;
    public final static int CENTER = -9;
    //These get notified once when the rectangle receives new coordinates (targeted at expensive graphic buffer updates)
    private final List<ChangeListener> rectangleUpdatedListeners = new ArrayList<>();
    //These get notified any time new coordinates are calculated (usually many in a row)
    private final List<ChangeListener> animationListeners = new ArrayList<>();
    //Easily generate positions relative to stuff
    protected int mode;
    //Presets:
    protected int relativeAnchorPoint;
    /*
    v1 and v2 form a vector (v1, v2) that define a distance to an anchor point defined by mode. If mode is absolute,
    they are the absolute x and y coordinates.
     */
    protected float v1;
    protected float v2;
    protected float w;
    protected float h;
    protected float neww;
    protected float newh;
    private CRectangle enclosingRectangle;
    private CRectangle parentRectangle;
    private float x;
    private float y;
    private boolean updating = false;


    public CRectangle(CRectangle enclosingRectangle, int x, int y, int width, int height)
    {
        this(enclosingRectangle, ABSOLUTE, UPPER_LEFT, x, y, width, height);
    }

    public CRectangle(CRectangle enclosingRectangle, int mode, int relativeAnchorPointMode, int v1, int v2, int width, int height)
    {
        this(enclosingRectangle, null, mode, relativeAnchorPointMode, v1, v2, width, height);
    }


    public CRectangle(int x, int y, int width, int height)
    {
        this.v1 = x;
        this.v2 = y;
        this.mode = ABSOLUTE;
        w = width;
        h = height;
        neww = width;
        newh = height;
        recalculatePositions();
    }

    public CRectangle(CRectangle enclosingRectangle, CRectangle parentRectangle, int mode, int relativeAnchorPointMode, int v1, int v2, int width, int height)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.w = width;
        this.h = height;
        this.mode = mode;
        this.enclosingRectangle = enclosingRectangle;
        this.parentRectangle = parentRectangle;
        this.relativeAnchorPoint = relativeAnchorPointMode;
        enclosingRectangle.addSizeChangeListener(this::recalculatePositions);
        enclosingRectangle.addAnimationEventListener(this::recalculatePositions);
        recalculatePositions();
    }


    private void recalculatePositions()
    {
        float parentX = enclosingRectangle == null ? 0 : enclosingRectangle.getX();
        float parentY = enclosingRectangle == null ? 0 : enclosingRectangle.getY();
        float parentW = enclosingRectangle == null ? 0 : enclosingRectangle.getWidth();
        float parentH = enclosingRectangle == null ? 0 : enclosingRectangle.getHeight();
        if (mode == ABSOLUTE)
        {
            x = parentX + v1;
            y = parentY + v2;
        } else if (mode == RELATIVE_TO_BOUNDS)
        {
            switch (relativeAnchorPoint)
            {
                case UPPER_LEFT:
                    x = parentX + v1;
                    y = parentY + v2;
                    break;
                case UPPER_RIGHT:
                    x = parentX + parentW - v1;
                    y = parentY + v2;
                    break;
                case LOWER_LEFT:
                    x = parentX + v1;
                    y = parentY + parentH - v2;
                    break;
                //TODO implement other anchor points
                default:
                    throw new IllegalStateException("Unexpected value for anchor point: " + relativeAnchorPoint);
            }
        }
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
                float speed = CGui.getInstance().getAnimationSpeed();
                w = w - (w - neww) * speed;
                h = h - (h - newh) * speed;
                animationListeners.forEach(ChangeListener::notifyChanged);
            } else
            {
                updating = false;
            }
        }
    }

    public void setNewSize(int width, int height)
    {
        neww = width;
        neww = height;
        if (w != neww || h != newh)
        {
            updating = true;
            rectangleUpdatedListeners.forEach(ChangeListener::notifyChanged);
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

    /**
     * Add a listener that gets notified once when new coordinates are given to the rectangle. The listener will not be
     * notified of the resulting updates in position/size as a result of the animation
     *
     * @param listener the listener to be notified once
     */

    public void addSizeChangeListener(ChangeListener listener)
    {
        rectangleUpdatedListeners.add(listener);
    }

    /**
     * Add a listener that gets notified whenever any coordinate (x, y, width, height) gets updated. In general, this
     * listener will receive a lot of notifications in a row.
     *
     * @param listener the listener to be notified a lot
     */

    public void addAnimationEventListener(ChangeListener listener)
    {
        animationListeners.add(listener);
    }

    public CRectangle getEnclosingRectangle()
    {
        return enclosingRectangle;
    }

    public void setEnclosingRectangle(CRectangle enclosingRectangle)
    {
        this.enclosingRectangle = enclosingRectangle;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

}
