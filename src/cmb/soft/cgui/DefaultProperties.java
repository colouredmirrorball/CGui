package cmb.soft.cgui;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Florian
 * Created on 28/01/2020
 */
public class DefaultProperties
{
    private static Map<String, String> propertyMap = setDefaultProperties();

    private static Map<String, String> setDefaultProperties()
    {
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("width", String.valueOf(1200));
        propertyMap.put("height", String.valueOf(800));
        return propertyMap;
    }

    public static String getDefaultProperty(String key)
    {
        return propertyMap.get(key);
    }
}
