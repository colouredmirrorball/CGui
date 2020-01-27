package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PSurface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * A CPane is an element of a CWindow (or PApplet) and draws onto a CSurface
 * It can assume any of the loaded CLayouts
 * A CLayout defines the locations of the GUI elements, content, and more
 * To a CLayout is a number of CTabs assigned, with different content but the same GUI layout
 * The CPane should display the CLayout and the choice of CTabs
 * A CPane can also switch between layouts (and should also have a menu element that allows the user to pick one)
 *
 *
 * Created by florian on 7/11/2014.
 */
public class CPane {


    //which buttons should it display?
    CLayout layout;

    //Panel dependent properties of a layout (eg. specific zoom, specific scroll offset, ...)
    CPaneLayoutProperties paneLayoutProperties;

    //When another layout is activated and later the user returns to the original layout, he'll expect his states to be restored
    //so we gotta save it somehow and link it to the layout
    LinkedHashMap<CTab, CPaneLayoutProperties> propertiesMemory = new LinkedHashMap<CTab, CPaneLayoutProperties>();

    //How flexible is the user interface?
    protected int mode;
    public static int STATICMODE = 0;           //All elements have a fixed position
    public static int RELATIVESTATICMODE = 1;   //All elements are fixed relative to the screen's size (resizable screen)
    public static int FREEMODE = 2;             //Full freedom for the user to replace elements

    protected boolean update = true;
    protected boolean floating = false;     //opposite of fixed, if floating, a fitting position should be found for this pane
    public String title = "";
    protected int w, h, x, y;
    protected boolean multiTab = false;
    protected CRectangle rect = new CRectangle(50, 50);
    protected CPosition pos = new CPosition(CPosition.UPPER_LEFT);
    protected int activeTab = 0;

    public CPane(CLayout layout)
    {
        this.layout = layout;
        this.title = layout.name;

    }

    public void draw(CSurface surface, PGraphics pg) {
        ArrayList<CTab> tabs = layout.getTabs();
        if(tabs.size() < 1) return;
        if (tabs.size() == 1) {
            //tabs.get(activeTab).draw(g);
        }
        else {
            for (CTab t : tabs) {
                //draw tab name on top of pane
            }
        }

        rect.update();
        layout.draw(surface, pg, activeTab);
    }

    public void update()
    {
    }

    public CPane setMultiTab(boolean multipleTabsPossible) {
        multiTab = multipleTabsPossible;
        return this;
    }

    public CPane setRectangle(CRectangle rectangle) {
        rect = rectangle;
        return this;
    }

    public CPane setSize(int width, int height) {
        rect = new CRectangle(width, height);
        return this;
    }

    public void reachSize(int newWidth, int newHeight) {
        rect.setNewRectangle(newWidth, newHeight);
    }

    public CPane setPosition(CPosition pos) {
        this.pos = pos;
        return this;
    }

    public CPane setLayout(CLayout layout)
    {
        this.layout = layout;
        return this;
    }

    public CLayout getLayout()
    {
        return layout;
    }


    public CButton addButton(CGui gui, String name)
    {
        return layout.addButton(gui, name);
    }
}
