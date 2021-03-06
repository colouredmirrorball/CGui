package cmb.soft.cgui;

import cmb.soft.cgui.control.CKeyBinding;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cmb.soft.cgui.CGui.DEFAULT_RENDERER;

/**
 * A CWindow is a new Processing sketch that can be launched from the first one
 * It will display a CSurface
 * <p>
 * Created by florian on 7/11/2014.
 */
public class CWindow extends PApplet {

    String title;

    CGui cgui;
    CSurface cSurface;
    Map<String, CKeyBinding> hotkeys;

    public CWindow(CGui cgui, String title) {
        this.cgui = cgui;
        this.title = title;
        cSurface = new CSurface(cgui);
        width = CGui.DEFAULT_WIDTH;
        height = CGui.DEFAULT_HEIGHT;
    }

    @Override
    public void settings() {
        size(width, height, DEFAULT_RENDERER);
    }

    @Override
    public void setup() {
        surface.setTitle(title);
        surface.setResizable(true);
        println("Launched new screen " + title);
    }

    @Override
    public void draw() {
        CGui.getInstance().getStyle().drawBackground(g);
        cSurface.update();
        cSurface.displayOn(this, 0, 0);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void moveToTop() {
        surface.setAlwaysOnTop(true);
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        surface.setAlwaysOnTop(false);
    }

    @Override
    public void exit() {
        cgui.removeWindow(this);
        dispose();
    }

    public void setTitle(String title) {
        this.title = title;
        if (surface != null)
            surface.setTitle(title);
    }

    public void boot() {
        String[] appletArgs = new String[] { "cmb.soft.cgui.CWindow" };
        runSketch(appletArgs, this);
        moveToTop();
    }

    public void setHotkeys(List<CKeyBinding> hotkeyList) {
        this.hotkeys = new HashMap<>();
        for (CKeyBinding keyBinding : hotkeyList) {
            hotkeys.put(keyBinding.getKeyString(), keyBinding);
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        String hotkey = parseEvent(event);
        try {
            CKeyBinding binding = hotkeys.get(hotkey);
            if (binding != null) {
                binding.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String parseEvent(KeyEvent event) {
        return (event.isControlDown() ? "ctrl+" : "") + (event.isAltDown() ? "alt+" : "") + (
            event.isShiftDown() ? "shift+" : "") + event.getKeyCode();
    }

    public void addPane(CPane pane) {
        cSurface.addPane(pane);
    }
}
