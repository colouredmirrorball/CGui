package cmb.soft.cgui.control;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian
 * Created on 27/01/2020
 */
public abstract class CCommand
{
    private List<CAction> actions = new ArrayList<CAction>();

    public void execute()
    {
        for (CAction action : actions)
        {
            action.execute();
        }
    }

    public void registerAction(CAction action)
    {
        actions.add(action);
    }
}
