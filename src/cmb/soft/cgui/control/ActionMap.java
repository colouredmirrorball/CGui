package cmb.soft.cgui.control;

import cmb.soft.cgui.actions.UndoAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Florian
 * Created on 30/01/2020
 */
public class ActionMap
{
    public static Map<String, Class<? extends CAction>> map = new HashMap<>();
    {
        map.put("undo", UndoAction.class);
    }


    public static Class<? extends CAction> findAction(String action)
    {
        return map.get(action);
    }
}
