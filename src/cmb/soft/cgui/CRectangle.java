package cmb.soft.cgui;

/**
 * Created by CMB on 27/06/2015.
 */
public class CRectangle {
    protected float w, h, neww, newh;
    protected float speed = 0.5f;

    public CRectangle(int width, int height) {
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
        if(w != neww || h != newh) {
            w = w - (w - neww) * speed;
            h = h - (h - newh) * speed;
        }
    }

    public void setNewRectangle(int width, int height) {
        neww = width;
        neww = height;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }
}
