package cmb.soft.cgui.control;

import static processing.core.PApplet.parseInt;
import static processing.core.PApplet.split;

/**
 * @author Florian
 * Created on 27/01/2020
 */
public class CKeyBinding extends CCommand
{
    private int keyCode;
    private boolean control;
    private boolean alt;
    private boolean shift;
    private String hotkey;

    public CKeyBinding(char keyCode, boolean control, boolean alt, boolean shift)
    {
        this.keyCode = keyCode;
        this.control = control;
        this.alt = alt;
        this.shift = shift;
        hotkey = getKeyString();
    }

    public CKeyBinding(String hotkey)
    {
        this.hotkey = hotkey;
        String[] split = split(hotkey, '+');
        for (String s : split)
        {
            switch (s)
            {
                case "ctrl":
                case "control":
                    control = true;
                    break;
                case "shift":
                    shift = true;
                    break;
                case "alt":
                    alt = true;
                    break;
                case "delete":
                    keyCode = 127;  //127+32
                    break;
                default:
                    keyCode = parseInt(s.charAt(0))-32;
                    break;
            }
        }
    }


    public String getKeyString()
    {
        return (control ? "ctrl+" : "") + (alt ? "alt+" : "") + (shift ? "shift+" : "") + keyCode;
    }



}
