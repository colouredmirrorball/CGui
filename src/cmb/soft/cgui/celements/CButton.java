package cmb.soft.cgui.celements;

import cmb.soft.cgui.CElement;
import cmb.soft.cgui.CGui;

import static cmb.soft.cgui.celements.ElementConstants.BUTTON;

/**
 * Created by CMB on 27/06/2015.
 */
public class CButton extends CElement
{
    public CButton(CGui gui, String name)
    {
        super(gui, name);
        element = BUTTON;
    }


}
