package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

/**
 * A CLayout is a template for the UI. It defines the (initial) UI elements' positions and sizes. A CPane contains the
 * actual UI elements as defined by a CLayout. The CLayout dictates how the contents of the CPane
 * A CLayout has the option to have tabs.
 */
public class CLayout
{
    private final List<CTab> tabs = new ArrayList<>();
    private final List<CElement> elements = new ArrayList<>();

    String name = "Empty Layout";

    public CLayout(String name)
    {
        this.name = name;
        tabs.add(new CTab());
    }

    public void draw(CSurface surface, PGraphics g, int activeTab)
    {
        for (CElement element : elements)
        {
            element.update(surface);
            element.draw(g);

        }
    }

    /**
     * Adds an element and makes a guess at the best possible location for the element
     *
     * @param element an element to be added
     * @return the element
     */

    public CElement addElementWithPosition(CElement element)
    {
        CRectangle rectangle = findBestPossiblePosition(elements);
        element.setRectangle(rectangle);
        elements.add(element);
        return element;
    }

    private CRectangle findBestPossiblePosition(List<CElement> elements)
    {
        if (elements.isEmpty())
        {
            return new CRectangle(5, 5, 150, 50);
        } else
        {
            //just place them in a vertical list for now
            CElement lastElement = elements.get(elements.size() - 1);
            CRectangle parentRectangle = lastElement.getRectangle();
            return new CRectangle(parentRectangle, CRectangle.Mode.RELATIVE_TO_BOUNDS, CRectangle.RelativeAnchorPoint.LOWER_LEFT, 0, 5,
                    CGui.getInstance().getDefaultElementWidth(), CGui.getInstance().getDefaultElementHeight());
        }
    }

    public CButton addButton(CGui gui, String name)
    {
        CButton newButton = new CButton(gui, name);
        addElementWithPosition(newButton);
        return newButton;
    }

    public List<CTab> getTabs()
    {
        return tabs;
    }
}
