/**
 * Created by florian on 5/11/2014.
 */
package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import cmb.soft.cgui.style.DefaultStyle;
import cmb.soft.cgui.style.Style;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

import static processing.core.PApplet.println;

public class CGui implements PConstants {

    public final static String DEFAULT_RENDERER = P3D;
    public final static int DEFAULT_WIDTH = 1200, DEFAULT_HEIGHT = 800;

    CSurface defaultSurface;
CWindow defaultWindow;

    Style style = new DefaultStyle();

    protected ArrayList<CSurface> surfaces = new ArrayList<CSurface>();
    ArrayList<CWindow> windows = new ArrayList<CWindow>();

    public CGui()
    {

        defaultSurface = new CSurface(this);
        surfaces.add(defaultSurface);
        defaultWindow = new CWindow(this, "CGUI");
        windows.add(defaultWindow);

    }

    public void launch()
    {
        defaultWindow.boot();
    }

    public void setTitle(String title)
    {
        defaultWindow.setTitle(title);
    }


    public void draw()
    {
        defaultSurface.update();
        defaultSurface.displayOn(defaultWindow, 0, 0);

    }

    public CWindow addWindow(String name) {
        CWindow newWindow = new CWindow(this, name);
        synchronized (windows)
        {
            windows.add(newWindow);
        }
        return newWindow;
    }

    public CPane addPane(CPane pane) {

        return pane;
    }

    public void addPane(CPane pane, CWindow window) {


    }

    public void addElement(CElement element, CLayout layout) {

    }

    public void addElement(CElement element) {

    }

    public CButton addButton(String name)
    {
        return defaultSurface.addButton(this, name);

    }



    public void removeWindow(CWindow cWindow)
    {
        for (int i = windows.size()-1; i >= 0; i--)
        {
            CWindow window = windows.get(i);
            if (window == cWindow)
            {
                window = null;
                windows.remove(i);
                return;
            }
        }
        if (windows.size() == 0)
        {
            System.exit(0);
        }
    }

    public Style getStyle()
    {
        return style;
    }

    public void setStyle(Style style)
    {
        this.style = style;
    }

    /**
     * Returns a Processing-compliant "color" variable
     * @param r red (0-255)
     * @param g green (0-255)
     * @param b blue (0-255)
     * @return the colour
     */

    public static int colour(int r, int g, int b)
    {
        return colour(255, r, g, b);
    }

    public static int colour(int alpha, int r, int g, int b)
    {
        return ((alpha & 0xff) << 24) | (( r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
    }

    public CWindow getDefaultWindow()
    {
        return defaultWindow;
    }
}
