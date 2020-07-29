package cmb.soft.cgui;

import cmb.soft.cgui.control.CAction;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 28/11/2014.
 */
public class CElement
{
    private final String name;
    private final CRectangle rect;
    private final PGraphics graphics;
    private final String displayName;
    private final boolean active = false;
    private final boolean collapsed = false;
    private CPosition pos;
    private boolean mouseover;


    protected List<CAction> actions = new ArrayList<>();
    private boolean visible = true;
    protected int element;

    public CElement(CGui gui, String name)
    {
        this(gui, name, 5, 5);
    }

    public CElement(CGui gui, String name, int x, int y)
    {
        this.name = name;
        this.displayName = name;
        rect = new CRectangle(200, 100);
        rect.addSizeChangeListener(this::updateSize);
        pos = new CPosition(x, y);
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
        parentGraphics.image(graphics, pos.v1, pos.v2);
    }

    public CElement setPosition(CPosition pos)
    {
        this.pos = pos;
        return this;
    }

    public void update(CSurface surface)
    {
        if (!visible) return;
        rect.update();
        mouseover = surface.mouseOver(pos, rect);
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


}
