/*
 * Created by florian on 5/11/2014.
 */
package cmb.soft.cgui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import cmb.soft.cgui.celements.CButton;
import cmb.soft.cgui.control.CAction;
import cmb.soft.cgui.control.CKeyBinding;
import cmb.soft.cgui.style.DefaultStyle;
import cmb.soft.cgui.style.Style;
import processing.core.PConstants;

import static cmb.soft.cgui.control.ActionMap.findAction;

public class CGui implements PConstants {
    private static CGui instance;

    public final static String DEFAULT_RENDERER = P3D;
    public final static int DEFAULT_WIDTH = 1200;
    public final static int DEFAULT_HEIGHT = 800;
    //    public static Accessible.EventHandler getInstance;

    CSurface defaultSurface;
    CWindow defaultWindow;

    //Stores all language files
    public static ResourceBundle resourceBundle;
    //Specifies the language
    private Locale locale;

    //Used for system parameters like theme, which language, and so on
    //this will read them from config.properties and store them in its object
    public static final Properties guiProperties = new Properties();
    //Same but for hotkey bindings
    public static final Properties hotkeyProperties = new Properties();
    public static final Properties applicationProperties = new Properties();
    private boolean createGuiPropertiesOnExit = false;
    private final File guiPropertiesFile;
    private final File hotkeyPropertiesFile;
    private final File applicationPropertiesFile;
    private boolean createApplicationPropertiesOnExit;
    private boolean createHotkeyPropertiesOnExit;

    Style style = new DefaultStyle();
    private final List<CKeyBinding> hotkeyList = new ArrayList<>();
    protected List<CSurface> surfaces = new ArrayList<>();

    Logger logger = Logger.getLogger(getClass().getName());
    List<CWindow> cWindows = new ArrayList<>();

    private CGui() {

        defaultSurface = new CSurface(this);
        surfaces.add(defaultSurface);
        defaultWindow = new CWindow(this, "CGUI");
        cWindows.add(defaultWindow);

        guiPropertiesFile = new File(defaultWindow.sketchPath() + "/data/ui.properties");
        try (InputStream input = new FileInputStream(guiPropertiesFile)) {
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
            hotkeyProperties.stringPropertyNames().forEach(property -> bindHotkey(hotkeyProperties.getProperty(property), property));
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
        } catch (IOException e) {
            logger.warning("Could not read application property file. Using default values...");
            e.printStackTrace();
        }
    }

    public static CGui getInstance() {
        if (instance == null) {
            instance = new CGui();
        }
        return instance;
    }

    public static void log(String log) {
        Logger.getLogger("CGui").info(log);
    }

    public static String getGuiPropertyOrDefault(String key) {
        Objects.requireNonNull(key);
        String returnValue = guiProperties.getProperty(key);
        if (returnValue == null) {
            returnValue = getDefaultProperty(key);
            if (returnValue != null)
                guiProperties.setProperty(key, returnValue);
        }
        return returnValue;
    }

    //    private Class<? extends CAction> findActionClass(String action)
    //    {
    //        return findAction(action);
    //    }

    private void bindHotkey(String hotkey, String action) {
        CKeyBinding binding = new CKeyBinding(hotkey);
        binding.registerAction(findAction(action));
        hotkeyList.add(binding);
    }

    public void launch() {
        defaultWindow.setHotkeys(hotkeyList);
        defaultWindow.boot();
    }

    //    public void draw()
    //    {
    //        defaultSurface.update();
    //        defaultSurface.displayOn(defaultWindow, 0, 0);
    //    }

    /**
     * Set title of default window
     *
     * @param title some title
     */
    public void setTitle(String title) {
        defaultWindow.setTitle(title);
    }

    private static String getDefaultProperty(String key)
    {
        return DefaultProperties.getDefaultProperty(key);
    }

    public CWindow addWindow(String name)
    {
        CWindow newWindow = new CWindow(this, name);
        cWindows.add(newWindow);
        return newWindow;
    }

    public CPane addPane(CPane pane)
    {
        addPane(pane, getDefaultWindow());
        return pane;
    }

    public void addPane(CPane pane, CWindow window)
    {
        window.addPane(pane);

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

    public CPane setPane(CPane pane)
    {
        defaultSurface.panels.add(pane);
        return pane;
    }


    public void removeWindow(CWindow cWindow)
    {
        for (int i = cWindows.size() - 1; i >= 0; i--) {
            CWindow window = cWindows.get(i);
            if (window == cWindow) {
                cWindows.remove(i);
                return;
            }
        }
        if (cWindows.isEmpty()) {
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

    public void executeAction(CAction action)
    {
        System.out.println(action.getClass().getName());
    }
}
