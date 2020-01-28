package cmb.soft.cgui;

import processing.core.PApplet;

import static cmb.soft.cgui.CGui.DEFAULT_RENDERER;

/**
 * A CWindow is a new Processing sketch that can be launched from the first one
 * It will display a CSurface
 *
 * Created by florian on 7/11/2014.
 */
public class CWindow extends PApplet{

    String title = "";
    protected boolean update = true;


    CGui cgui;

    CSurface cSurface;
int width = CGui.DEFAULT_WIDTH;
int height = CGui.DEFAULT_HEIGHT;

    public CWindow(CGui cgui, String title)
    {
        this.cgui = cgui;
        this.title = title;
        //runSketch(new String[] {"cmb.soft.cgui.CWindow"}, this);

    }

    public void settings()
    {
        println(width, height);
        size(width, height, DEFAULT_RENDERER);
    }

    public void setup()
    {
        cSurface = new CSurface(cgui);
        surface.setTitle(title);
        surface.setResizable(true);
    }


    public void draw()
    {
        cSurface.update();
        cSurface.displayOn(this, 0, 0);
    }

public void setWidth(int width)
{
    println("newwidth", width);
    this.width = width;
}

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void moveToTop()
    {
        surface.setAlwaysOnTop(true);
        try
        {
            Thread.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        surface.setAlwaysOnTop(false);
    }

    public void exit()
    {
        cgui.removeWindow(this);
        dispose();
    }


    public void setTitle(String title)
    {
        this.title = title;
        if(surface != null) surface.setTitle(title);
    }


    public void boot()
    {
        String[] appletArgs = new String[]{"cmb.soft.cgui.CWindow"};
        runSketch(appletArgs, this);
        moveToTop();
    }
}
