package cmb.soft.cgui;

import processing.core.PApplet;

/**
 * @author Florian
 * Created on 1/03/2019
 */
public class CLauncher extends PApplet {

    @Override
    public void settings() {
        size(300, 300);

    }

    /**
     * initialisation
     */

    @Override
    public void setup()
    {
        surface.setAlwaysOnTop(true);
        surface.setResizable(false);
    }

    //Quit without ending the JVM
    @Override
    public void exit()
    {
        dispose();
    }

    public void hide()
    {
        surface.setVisible(false);
    }

    /**
     * The Source! This is where it all begins.
     * @param args as of now no custom input args are supported
     */
    public static void main(String[] args)
    {
        String[] appletArgs = new String[]{"cmb.soft.cgui.CLauncher"};
        if (args != null)
        {
            //PApplet will call settings() and setup() for us
            PApplet.main(concat(appletArgs, args));
        } else
        {
            PApplet.main(appletArgs);
        }

    }
}
