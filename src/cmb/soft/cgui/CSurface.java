package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

import static cmb.soft.cgui.CGui.DEFAULT_RENDERER;

/**
 * A CSurface is what is drawn in a CWindow, or in the main PApplet
 * Its main reason of existence is to abstract the drawing layer so the same GUI can easily be drawn on both
 * separate CWindows and the parent PApplet
 * it is the rendering canvas for all the GUI elements
 * It contains a list of CPanes
 * A CPane has a CLayout and a collection of CTabs
 * Multiple panels can be displayed on the same surface
 * for example a "timeline" panel can be placed alongside a "3D drawing" panel
 * so the user can edit a drawing while seeing the processed effect on the timeline
 */

public class CSurface {
    CGui gui;
    PGraphics pg;

    List<CPane> panels = new ArrayList<>();

    CPane defaultPane;
    CLayout defaultLayout;

    public CSurface(CGui gui) {
        defaultLayout = new CLayout("Default");
        defaultPane = new CPane(defaultLayout);
        panels.add(defaultPane);
    }

    public void displayOn(CWindow window, int x, int y) {
        if (pg == null) {
            pg = window.createGraphics(window.width, window.height, DEFAULT_RENDERER);
        }
        PGraphics g = window.g;
        pg.beginDraw();
        if (panels.size() > 1) {
            g.noFill();
            //draw a rectangle around every panel
            for (CPane pane : panels) {

            }
        }
        for (CPane panel : panels) {
            panel.draw(this, pg);
        }
        pg.endDraw();
        g.image(pg, x, y);
    }

    public CButton addButton(CGui gui, String name) {
        return defaultPane.addButton(gui, name);
    }

    public void update() {
        for (CPane panel : panels) {
            panel.update();
        }
    }

    public boolean mouseOver(CPosition pos, CRectangle rect)
    {
        return false;
    }

    public void addPane(CPane pane) {
        panels.add(pane);
    }
}
