package cmb.soft.cgui.style;

import cmb.soft.cgui.CElement;
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
     *
     * @param g       graphics buffer to draw on - this PGraphics will be automatically displayed at the correct location. Its width and height correspond to the width and height of the element.
     * @param element the element being drawn on
     */
    void drawElement(PGraphics g, CElement element);

    void drawBackground(PGraphics g);
}
