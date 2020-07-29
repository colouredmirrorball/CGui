package cmb.soft.cgui.style;

import cmb.soft.cgui.CElement;
import processing.core.PConstants;
import processing.core.PGraphics;

import static cmb.soft.cgui.CGui.colour;

/**
 * @author Florian
 * Created on 28/02/2019
 */
public class DefaultStyle implements Style
{
    int fillColour;
    int strokeColour;
    int activeFillColour;
    int activeStrokeColour;
    int mouseOverFillColour;
    int mouseOverStrokeColour;

    public DefaultStyle()
    {
        strokeColour = colour(20, 20, 20);
        fillColour = colour(127, 127, 127);
        activeFillColour = colour(100, 100, 100);
        mouseOverFillColour = colour(95, 91, 120);
    }

    @Override
    public void drawElement(PGraphics g, CElement element)
    {
        float width = element.getRectangle().getWidth();
        float height = element.getRectangle().getHeight();
        if (element.isMouseOver()) g.fill(mouseOverFillColour);
        else if (element.isActive()) g.fill(activeFillColour);
        else g.fill(fillColour);
        g.rect(0, 0, width, height);
        g.fill(strokeColour);
        g.textAlign(PConstants.CENTER);
        g.textSize(height / 4);
        if (g.width > 50 && g.height > 15) g.text(element.getTitle(), 0, 0, width, height);
    }

    @Override
    public void drawBackground(PGraphics g)
    {
        g.background(0);
    }
}
