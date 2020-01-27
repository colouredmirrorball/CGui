package cmb.soft.cgui;

import processing.core.PApplet;

/**
 * @author Florian
 * Created on 1/03/2019
 */
public class CLauncher extends PApplet
{
    Processor processor;

    public void settings()
    {
        size(300, 300);

    }




    /**
     * initialisation
     */

    public void setup()
    {
        processor = new Processor(this);
        surface.setAlwaysOnTop(true);
        surface.setResizable(false);
        new Thread(processor).start();


    }

    //Quit without ending the JVM
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
