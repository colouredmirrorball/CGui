package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import processing.core.PGraphics;

import java.util.ArrayList;

/**
 * This is where you store your CElements.
 * A CLayout is assigned to a Pane.
 * A CLayout has the option to have tabs.
 */
public class CLayout
{
    protected ArrayList<CTab> tabs = new ArrayList<CTab>();
    protected ArrayList<CElement> elements = new ArrayList<CElement>();

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
            element.update(surface );
            element.draw(g);

        }
    }

    public CLayout addElement(CElement element)
    {
        elements.add(element);
        return this;
    }

    public CButton addButton(CGui gui, String name)
    {
        CButton newButton = new CButton(gui, name);
        elements.add(newButton);
        return newButton;
    }

    public ArrayList<CTab> getTabs()
    {
        return tabs;
    }
}
