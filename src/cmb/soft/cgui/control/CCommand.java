package cmb.soft.cgui.control;

import cmb.soft.cgui.CGui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian
 * Created on 27/01/2020
 */
public abstract class CCommand
{
    private final List<Class<? extends CAction>> actions = new ArrayList<>();

    public void execute() throws IllegalAccessException, InstantiationException
    {
        for (Class<? extends CAction> ActionClass : actions)
        {
            CAction action = ActionClass.newInstance();
            CGui.getInstance().executeAction(action);
        }
    }

    public void registerAction(Class<? extends CAction> action)
    {
        actions.add(action);
    }
}
