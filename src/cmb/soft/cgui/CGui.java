/*
 * Created by florian on 5/11/2014.
 */
package cmb.soft.cgui;

import cmb.soft.cgui.celements.CButton;
import cmb.soft.cgui.control.CAction;
import cmb.soft.cgui.control.CKeyBinding;
import cmb.soft.cgui.style.DefaultStyle;
import cmb.soft.cgui.style.Style;
import processing.core.PConstants;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static cmb.soft.cgui.control.ActionMap.findAction;

public class CGui implements PConstants {
    private static CGui instance;

    public static final String DEFAULT_RENDERER = P3D;
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 800;
    public static final float DEFAULT_ANIMATION_SPEED = 0.5f;
    public static final int DEFAULT_SPACING = 5;
    public static final int DEFAULT_ELEMENT_WIDTH = 150;
    public static final int DEFAULT_ELEMENT_HEIGHT = 50;

    CSurface defaultSurface;
    CWindow defaultWindow;

    //Stores all language files
    private static ResourceBundle resourceBundle;
    //Specifies the language
    private Locale locale;

    //Used for system parameters like theme, which language, and so on
    //this will read them from config.properties and store them in its object
    protected static final Properties guiProperties = new Properties();
    //Same but for hotkey bindings
    protected static final Properties hotkeyProperties = new Properties();
    protected static final Properties applicationProperties = new Properties();
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
            hotkeyProperties.stringPropertyNames()
                    .forEach(property -> bindHotkey(hotkeyProperties.getProperty(property), property));
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

        applicationPropertiesFile = new File(
                defaultWindow.sketchPath() + "/data/application.properties");
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

    public static CGui getInstance() {
        if (instance == null) {
            instance = new CGui();
        }
        return instance;
    }

    public static void log(String log) {
        Logger.getLogger("CGui").info(log);
    }

    public static void log(Exception exception) {
        Logger.getLogger("CGui").severe(exception.getMessage());
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

    private void bindHotkey(String hotkey, String action) {
        CKeyBinding binding = new CKeyBinding(hotkey);
        binding.registerAction(findAction(action));
        hotkeyList.add(binding);
    }

    public void launch() {
        defaultWindow.setHotkeys(hotkeyList);
        defaultWindow.boot();
    }

    /**
     * Set title of default window
     *
     * @param title some title
     */
    public void setTitle(String title)
    {
        defaultWindow.setTitle(title);
    }

    private static String getDefaultProperty(String key)
    {
        return DefaultProperties.getDefaultProperty(key);
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
        for (int i = cWindows.size() - 1; i >= 0; i--)
        {
            CWindow window = cWindows.get(i);
            if (window == cWindow)
            {
                cWindows.remove(i);
                break;
            }
        }
        if (cWindows.isEmpty())
        {
            exit();
        }
    }

    private void exit()
    {
        if (createGuiPropertiesOnExit)
        {
            createPropertiesOnExit(guiPropertiesFile, "UI");
        }
        writePropertiesFile(guiProperties, guiPropertiesFile);
        if (createHotkeyPropertiesOnExit)
        {
            createPropertiesOnExit(hotkeyPropertiesFile, "hotkey");
        }
        writePropertiesFile(hotkeyProperties, hotkeyPropertiesFile);
        if (createApplicationPropertiesOnExit)
        {
            createPropertiesOnExit(applicationPropertiesFile, "application");
        }
        writePropertiesFile(applicationProperties, applicationPropertiesFile);
        System.exit(0);
    }

    private void writePropertiesFile(Properties properties, File file)
    {
        try (FileOutputStream stream = new FileOutputStream(file))
        {
            properties.store(stream, "auto generated");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void createPropertiesOnExit(File hotkeyPropertiesFile, String key)
    {
        try
        {
            boolean success = hotkeyPropertiesFile.createNewFile();
            if (success)
                logger.info(() -> String.format("Created new %s property file: %s", key,
                        hotkeyPropertiesFile.getAbsolutePath()));
        } catch (IOException e)
        {
            e.printStackTrace();
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

    public CWindow getDefaultWindow()
    {
        return defaultWindow;
    }

    public void executeAction(CAction action)
    {
        try
        {
            action.execute();
        } catch (Exception exception)
        {
            log(exception);
        }
    }

    public float getAnimationSpeed()
    {
        //TODO make animation speed selectable
        return DEFAULT_ANIMATION_SPEED;
    }

    public int getSpacing()
    {
        //TODO make spacing configurable
        return DEFAULT_SPACING;
    }

    public int getDefaultElementWidth()
    {
        //TODO make configurable
        return DEFAULT_ELEMENT_WIDTH;
    }

    public int getDefaultElementHeight()
    {
        //TODO make configurable
        return DEFAULT_ELEMENT_HEIGHT;
    }
}
