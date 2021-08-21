package cmb.soft.cgui;

import java.util.ArrayList;
import java.util.List;

import cmb.soft.cgui.control.CAction;
import processing.core.PGraphics;

/**
 * Created by florian on 28/11/2014.
 */
public class CElement {
    private final String name;
    private final PGraphics graphics;

    private CRectangle rect;
    private final String displayName;
    private final boolean active = false;
    private final boolean collapsed = false;
    private boolean mouseover;
    protected List<CAction> actions = new ArrayList<>();
    private boolean visible = true;

    public CElement(CGui gui, String name)
    {
        this(gui, name, 5, 5);
    }

    public CElement(CGui gui, String name, int x, int y)
    {
        this.name = name;
        this.displayName = name;
//        rect = new CRectangle( CRectangle.RELATIVE_TO_BOUNDS, CRectangle.LOWER_LEFT, 5, 5, 200, 100);
//        rect.addSizeChangeListener(this::updateSize);
        graphics = gui.defaultWindow.createGraphics(200, 100);
    }

    private void updateSize()
    {
        graphics.setSize((int) rect.neww, (int) rect.newh);
    }

    public void draw(PGraphics parentGraphics)
    {
        if (!visible) return;
        graphics.beginDraw();
        CGui.getInstance().getStyle().drawElement(graphics, this);
        graphics.endDraw();
        parentGraphics.image(graphics, rect.getX(), rect.getY());
    }

    public void update(CSurface surface)
    {
        if (!visible) return;
        rect.update();
        mouseover = surface.mouseOver(rect);
    }

    public CElement addAction(CAction action)
    {
        actions.add(action);
        return this;
    }

    public String getTitle()
    {
        return displayName;
    }

    public CRectangle getRectangle()
    {
        return rect;
    }

    public boolean isMouseOver()
    {
        return mouseover;
    }

    public boolean isActive()
    {
        return active;
    }

    public CElement toggleVisibility()
    {
        this.visible = !visible;
        return this;
    }


    public void setRectangle(CRectangle rectangle)
    {
        this.rect = rectangle;
    }
}
