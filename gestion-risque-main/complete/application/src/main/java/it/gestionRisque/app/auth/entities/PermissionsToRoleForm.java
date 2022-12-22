//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class PermissionsToRoleForm {
	
	private String name;
	private String nameP;
}
