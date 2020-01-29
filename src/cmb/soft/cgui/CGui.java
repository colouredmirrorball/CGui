/**
 * Created by florian on 5/11/2014.
 */
package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import cmb.soft.cgui.control.ActionMap;
import cmb.soft.cgui.control.CAction;
import cmb.soft.cgui.control.CKeyBinding;
import cmb.soft.cgui.style.DefaultStyle;
import cmb.soft.cgui.style.Style;
import processing.core.PConstants;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static processing.core.PApplet.println;

public class CGui implements PConstants
{

    public final static String DEFAULT_RENDERER = P3D;
    public final static int DEFAULT_WIDTH = 1200, DEFAULT_HEIGHT = 800;

    CSurface defaultSurface;
    CWindow defaultWindow;

    //Stores all language files
    public static ResourceBundle resourceBundle;
    //Specifies the language
    private Locale locale;

    //Used for system parameters like theme, which language, and so on
    //this will read them from config.properties and store them in its object
    public static Properties guiProperties = new Properties();
    //Same but for hotkey bindings
    public static Properties hotkeyProperties = new Properties();
    public static Properties applicationProperties = new Properties();
    private boolean createGuiPropertiesOnExit = false;
    private File guiPropertiesFile;
    private final File hotkeyPropertiesFile;
    private final File applicationPropertiesFile;
    private boolean createApplicationPropertiesOnExit;
    private boolean createHotkeyPropertiesOnExit;

    Style style = new DefaultStyle();

    protected ArrayList<CSurface> surfaces = new ArrayList<CSurface>();
    ArrayList<CWindow> windows = new ArrayList<CWindow>();

    Logger logger = Logger.getLogger(getClass().getName());
    private List<CKeyBinding> hotkeyList;

    public CGui()
    {

        defaultSurface = new CSurface(this);
        surfaces.add(defaultSurface);
        defaultWindow = new CWindow(this, "CGUI");
        windows.add(defaultWindow);

        guiPropertiesFile = new File(defaultWindow.sketchPath() + "/data/ui.properties");
        try (InputStream input = new FileInputStream(guiPropertiesFile))
        {
            guiProperties.load(input);
            int width = Integer.parseInt(getGuiPropertyOrDefault("width"));
            int height = Integer.parseInt(getGuiPropertyOrDefault("height"));
            defaultWindow.setWidth(width);
            defaultWindow.setHeight(height);
        } catch (FileNotFoundException e)
        {
            logger.warning("No ui property file found. Using default values...");
            createGuiPropertiesOnExit = true;
            e.printStackTrace();
        } catch (IOException e)
        {
            logger.warning("Could not read ui property file. Using default values...");
            e.printStackTrace();
        }

        hotkeyPropertiesFile = new File(defaultWindow.sketchPath() + "/data/hotkeys.properties");
        try (InputStream input = new FileInputStream(hotkeyPropertiesFile))
        {
            hotkeyProperties.load(input);
           hotkeyProperties.stringPropertyNames().forEach(property -> bindHotkey(property,hotkeyProperties.getProperty(property)));
        } catch (FileNotFoundException e)
        {
            logger.warning("No hotkey property file found. Using default values...");
            createHotkeyPropertiesOnExit = true;
            e.printStackTrace();
        } catch (IOException e)
        {
            logger.warning("Could not read hotkey property file. Using default values...");
            e.printStackTrace();
        }

        applicationPropertiesFile = new File(defaultWindow.sketchPath() + "/data/application.properties");
        try (InputStream input = new FileInputStream(applicationPropertiesFile))
        {
            applicationProperties.load(input);
        } catch (FileNotFoundException e)
        {
            logger.warning("No application property file found. Using default values...");
            createApplicationPropertiesOnExit = true;
            e.printStackTrace();
        } catch (IOException e)
        {
            logger.warning("Could not read application property file. Using default values...");
            e.printStackTrace();
        }
    }

    private void bindHotkey(String hotkey, String action)
    {
        hotkeyList = new ArrayList<>();
        CKeyBinding binding = new CKeyBinding(hotkey);
        binding.registerAction(findAction(action));
        hotkeyList.add(binding);
    }

    private CAction findAction(String action)
    {
        return ActionMap.findAction(action);
    }

    public void launch()
    {
        defaultWindow.setHotkeys(hotkeyList);
        defaultWindow.boot();

    }

    /**
     * Set title of default window
     * @param title some title
     */
    public void setTitle(String title)
    {
        defaultWindow.setTitle(title);
    }


    public void draw()
    {
        defaultSurface.update();
        defaultSurface.displayOn(defaultWindow, 0, 0);

    }

    public static String getGuiPropertyOrDefault(String key)
    {
        Objects.requireNonNull(key);
        String returnValue = guiProperties.getProperty(key);
        if (returnValue == null)
        {
            returnValue = getDefaultProperty(key);
            if(returnValue != null) guiProperties.setProperty(key, returnValue);
        }
        return returnValue;
    }

    private static String getDefaultProperty(String key)
    {
        return DefaultProperties.getDefaultProperty(key);
    }

    public CWindow addWindow(String name)
    {
        CWindow newWindow = new CWindow(this, name);

            windows.add(newWindow);

        return newWindow;
    }

    public CPane addPane(CPane pane)
    {

        return pane;
    }

    public void addPane(CPane pane, CWindow window)
    {


    }

    public void addElement(CElement element, CLayout layout)
    {

    }

    public void addElement(CElement element)
    {

    }

    public CButton addButton(String name)
    {
        return defaultSurface.addButton(this, name);

    }


    public void removeWindow(CWindow cWindow)
    {
        for (int i = windows.size() - 1; i >= 0; i--)
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
            exit();
        }
    }

    private void exit()
    {
        if (createGuiPropertiesOnExit)
        {
            try
            {
                boolean success = guiPropertiesFile.createNewFile();
                if(success) logger.info("Created new UI property file: " + guiPropertiesFile.getAbsolutePath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            guiProperties.store(new FileOutputStream(guiPropertiesFile), "auto generated");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (createHotkeyPropertiesOnExit)
        {
            try
            {
                boolean success = hotkeyPropertiesFile.createNewFile();
                if(success) logger.info("Created new hotkey property file: " + hotkeyPropertiesFile.getAbsolutePath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            hotkeyProperties.store(new FileOutputStream(hotkeyPropertiesFile), "auto generated");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (createApplicationPropertiesOnExit)
        {
            try
            {
                boolean success = applicationPropertiesFile.createNewFile();
                if(success) logger.info("Created new application property file: " + applicationPropertiesFile.getAbsolutePath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            applicationProperties.store(new FileOutputStream(applicationPropertiesFile), "auto generated");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.exit(0);
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
     *
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
        return ((alpha & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
    }

    public CWindow getDefaultWindow()
    {
        return defaultWindow;
    }
}
