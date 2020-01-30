package cmb.soft.cgui;

import cmb.soft.cgui.control.CKeyBinding;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

Map<String,CKeyBinding> hotkeys;

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

    public void setHotkeys(List<CKeyBinding> hotkeyList)
    {
        this.hotkeys = new HashMap<>();
        for (CKeyBinding keyBinding : hotkeyList)
        {
            hotkeys.put(keyBinding.getKeyString(), keyBinding);
        }
    }

    public void keyPressed(KeyEvent event)
    {
        String hotkey = parseEvent(event);
        try
        {
            CKeyBinding binding = hotkeys.get(hotkey);
            if(binding != null) {
                binding.execute();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private String parseEvent(KeyEvent event)
    {
         return (event.isControlDown() ? "ctrl+" : "") + (event.isAltDown() ? "alt+" : "") + (event.isShiftDown() ? "shift+" : "") + event.getKeyCode();
    }
}
