package it.gestionRisque.app.Entities;

import java.util.Map;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.UniqueElements;
import org.json.JSONArray;
import org.json.JSONObject;

import it.gestionRisque.app.DynamicQueries.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity (name="parametrageRapport")
public class RapportType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private  String types;
	 @Column(unique=true)
	private  String code;
	private  String description;
	private String selectClause;
	private String keyValue;
	
	public RapportType(Long id, String code, String description, String selectClause, Map<String, String> keys) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.selectClause = selectClause;
		JSONObject jsonObject =new JSONObject(keys);
		this.keyValue= new JSONObject(keys).toString();
	}
	
	public RapportType(Long id, String code, String description, String selectClause, String keyValue) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.selectClause = selectClause;
		this.keyValue= keyValue;
	}

	public JSONArray getKeyValueMap() {
		
		return new JSONArray(keyValue);
	}
	 
}
