package cmb.soft.cgui;

import processing.core.PVector;

/**
 * A position inside a CRectangle
 * The vectors v1 and v2 are either absolute coordinates,
 * relative coordinates,
 * or percentages (when they're 0 < v < 1)
 */
public class CPosition {
    protected float v1, v2;       //vectors, what exactly they are depends on the mode (eg relative or absolute position)

    //Easily generate positions relative to stuff
    protected int mode;
    public static int ABSOLUTE = 0;
    public static int RELATIVE_TO_BOUNDS = 1;
    public static int RELATIVE_TO_POS = 2;


    //Presets:
    protected int relativeMode;
    public static int UPPER_LEFT = -1;
    public static int UPPER_RIGHT = -2;
    public static int LOWER_LEFT = -3;
    public static int LOWER_RIGHT = -4;
    public static int UPPER_CENTER = -5;
    public static int LOWER_CENTER = -6;
    public static int LEFT_CENTER = -7;
    public static int RIGHT_CENTER = -8;
    public static int CENTER = -9;

    public CPosition(int x, int y) {
        mode = ABSOLUTE;
        v1 = x;
        v2 = y;
    }


    public CPosition(int preset) {
        if(preset < 0 && preset > -10)
        {
            relativeMode = preset;
            mode = RELATIVE_TO_BOUNDS;
        }
        if (preset == UPPER_LEFT) {
            v1 = 5;
            v2 = 5;

        } else if (preset == UPPER_RIGHT) {
            v1 = 5;
            v2 = 5;

        }
        else
        {

        }
    }

    /**
     * Create a CPosition relative to another one
     * @param other
     * @param x
     * @param y
     */

    public CPosition(CPosition other, int x, int y) {

    }

    /**
     *
     * @param rect the rectangle of the element directly above the current element in the hierarchy
     * @return
     */

    public PVector getPositionInRectangle(CRectangle rect) {
        return new PVector(getRelX(rect), getRelY(rect));
    }

    public float getRelX(CRectangle rectangle) {
        if (mode == ABSOLUTE) {
            return v1;
        } else if (mode == RELATIVE_TO_BOUNDS) {

        } else if (mode == 2) {
        } else {
        }
        return 0;
    }

    public float getRelY(CRectangle rectangle) {
        if (mode == ABSOLUTE) {
            return v2;
        } else if (mode == 1) {
        } else if (mode == 2) {
        } else {
        }
        return 0;
    }


}
