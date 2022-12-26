package it.gestionRisque.app.DynamicQueries;

import java.io.Serializable;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
    private Object value;
    private SearchOperation operation;
    private boolean isAnd;
    
    public SearchCriteria  (JSONObject jsonObject) {
		this.key =jsonObject.getString("column");
		this.value =jsonObject.getString("value");
		this.value = this.getValue(jsonObject);
		this.operation = this.getOperation(jsonObject);
		this.isAnd = this.getTypeOperand(jsonObject);
	}

	private boolean getTypeOperand(JSONObject jsonObject) {
		if (jsonObject.has("operand")) {
			return jsonObject.getBoolean("operand");
		}
		return true;
	}

	private Object getValue(JSONObject jsonObject) {
		String v = jsonObject.getString("value");
		if (jsonObject.has("type")) {
			String opt = jsonObject.getString("type");
			if("Number".equals(opt)){
				return Long.valueOf(v);
			} else if("Double".equals(opt)){
				return Double.valueOf(v);
			}
		}
		return v;
	}

	private SearchOperation getOperation(JSONObject jsonObject) {
		if (jsonObject.has("operation")) {
			String opt = jsonObject.getString("operation");
			if("=".equals(opt)){
				return SearchOperation.EQUAL;
			}else if("!=".equals(opt)||"<>".equals(opt)){
				return SearchOperation.NOT_EQUAL;
			}else if("<=".equals(opt)){
				return SearchOperation.LESS_THAN_EQUAL;
			}else if(">=".equals(opt)){
				return SearchOperation.GREATER_THAN_EQUAL;
			}else if("<".equals(opt)){
				return SearchOperation.LESS_THAN;
			}else if("LIKE".equals(opt)){
				return SearchOperation.MATCH;
			}
		}
		return SearchOperation.EQUAL;
	}

	public boolean isAND() {
		return isAnd;
	}
}
