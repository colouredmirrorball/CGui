package cmb.soft.cgui.celements;

import cmb.soft.cgui.CElement;
import cmb.soft.cgui.CGui;

public class CRadioButtons extends CElement {
    public CRadioButtons(CGui gui, String name) {
        super(gui, name);
    }

    public CRadioButtons(CGui gui, String name, int x, int y) {
        super(gui, name, x, y);
    }

    public CRadioButtons addRadioButtons(String procedures) {
        return this;
    }

    public CRadioButtons withHeader() {
        return this;
    }
}
