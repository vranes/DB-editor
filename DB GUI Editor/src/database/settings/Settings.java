package database.settings;

import java.util.HashMap;
import java.util.Map;

public class Settings implements ISettings{

    private Map parameters = new HashMap();

    public Object getParameter(String parameter) {
        return  this.parameters.get(parameter);
    }

    public void addParameter(String parameter, Object value) {
        this.parameters.put(parameter, value);
    }

}
