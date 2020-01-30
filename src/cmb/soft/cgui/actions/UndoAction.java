package cmb.soft.cgui.actions;

import cmb.soft.cgui.control.CAction;

/**
 * @author Florian
 * Created on 30/01/2020
 */
public class UndoAction implements CAction
{
    @Override

    public void execute()
    {
        System.out.println("pretending to undo");
    }

    @Override
    public void undo()
    {

    }
}
