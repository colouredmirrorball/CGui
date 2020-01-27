package cmb.soft.cgui.style;

import processing.core.PApplet;
import processing.core.PGraphics;

import static cmb.soft.cgui.CGui.colour;

/**
 * @author Florian
 * Created on 28/02/2019
 */
public class DefaultStyle implements Style
{
    int fillColour, strokeColour, activeFillColour, activeStrokeColour, mouseOverFillColour, mouseOverStrokeColour;

    public DefaultStyle()
    {
        strokeColour = colour(20, 20, 20);
        fillColour = colour(127,127,127);
    }

    @Override
    public void drawElement(PGraphics g, String title, boolean active, boolean mouseOver, int type)
    {
        g.fill(fillColour);
        g.rect(0,0, g.width, g.height);
        g.fill(strokeColour);
        if(g.width > 50 && g.height > 15) g.text(title, 5, 5);
    }

    @Override
    public void drawBackground(PGraphics g)
    {

    }
}
