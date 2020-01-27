package cmb.soft.cgui.style;

import processing.core.PGraphics;

/**
 * If you need to define your own style, you can create a class that implements this interface.
 *
 * @author Florian
 * Created on 28/02/2019
 */
public interface Style
{
    /**
     * Draw a GUI element
     * @param g graphics buffer to draw on - this PGraphics will be automatically displayed at the correct location. Its width and height correspond to the width and height of the element.
     * @param title text to be displayed on the graphics element
     * @param active true if the element is active
     * @param mouseOver true if the mouse is over the element
     * @param type
     */
    void drawElement(PGraphics g, String title, boolean active, boolean mouseOver, int type);

    void drawBackground(PGraphics g);
}
