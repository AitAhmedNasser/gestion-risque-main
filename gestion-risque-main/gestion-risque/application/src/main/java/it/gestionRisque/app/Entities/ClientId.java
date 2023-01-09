package it.gestionRisque.app.Entities;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientId implements Serializable {
	private String obligoreId;
	private Date reportingDate;
	
}
