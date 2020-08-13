package resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Row {

    private String name;
    private Map<String, Object> fields;
    private HashMap<String, String> dataTypes;
    
    public Row() {
        fields = new HashMap<>();
    }

    public void addField(String fieldName, Object value) {
        fields.put(fieldName, value);
    }

    public void removeField(String fieldName) {
        fields.remove(fieldName);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public String keysToString() {					//vraca imena kolona formatirana za upite
		StringBuilder sb = new StringBuilder("(");
		for (String key : fields.keySet()) {
			sb.append(key + ", ");
		}
		sb.delete(sb.length()-2, sb.length()).append(")");
		return sb.toString();
	}
    
	public String valuesToString() {				//vraca vrednosti u kolonama formatirane za upit
		StringBuilder sb = new StringBuilder("(");
		for (Object value : fields.values()) {
			sb.append("\'" + value + "\'" + ", ");
		}
		sb.delete(sb.length()-2, sb.length()).append(")");
		return sb.toString();
	}

	public HashMap<String, String> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(HashMap<String, String> dataTypes) {
		this.dataTypes = dataTypes;
	}
	

}
