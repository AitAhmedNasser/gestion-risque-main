//package Auth;
package it.gestionRisque.app;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import it.gestionRisque.app.auth.service.AccountService;
import it.gestionRisque.app.auth.service.RessourceService;
import it.gestionRisque.app.auth.entities.Agence;
import it.gestionRisque.app.auth.entities.Niveau;
import it.gestionRisque.app.auth.entities.Permissions;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Ressource;
import it.gestionRisque.app.auth.entities.Role;
import it.gestionRisque.app.auth.entities.User;

@SpringBootApplication
public class AuthentificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthentificationApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//Commande line runner est creer pour tester les apis de l'application 
	
	

	@Bean
	CommandLineRunner start(AccountService accountService, RessourceService ressourceService, RepositoryRestConfiguration restConfiguration) {
		restConfiguration.exposeIdsFor(AuthentificationApplication.class);
		return args->{
			
	// creer un niveau 
			
			Niveau Niv_1 = accountService.AddNiveau(new Niveau(null, "niveau1",1,new ArrayList<>()));
			Niveau Niv_2 = accountService.AddNiveau(new Niveau(null, "niveau2",2,new ArrayList<>()));
			
			
			
			//	Create Role 
			
			Role roleAdmin=accountService.addNewRolle(new Role(null, "Admin",  Niv_1, new ArrayList<>(),new HashSet()));
			Role roleUtilisateur=accountService.addNewRolle(new Role(null, "Utilisateur", Niv_1,new ArrayList<>(),new HashSet()));
			Role roleRisqueManager=accountService.addNewRolle(new Role(null, "Manager Risque", Niv_1,new ArrayList<>(),new HashSet()));
			Role roleAnalysteRisque=accountService.addNewRolle(new Role(null, "Analyste Risque", Niv_1,new ArrayList<>(),new HashSet()));
			Role roleControleur=accountService.addNewRolle(new Role(null, "Contrôleur", Niv_1,new ArrayList<>(),new HashSet()));
			Role roleVisualiseur=accountService.addNewRolle(new Role(null, "Visualiseur", Niv_1,new ArrayList<>(),new HashSet()));
			

			//Create agence
			//Agence agence1 =  accountService.AddAgence(new Agence(null, "BNA", "l'agence de BNA", new ArrayList<>()));
			//Agence agence2 =  accountService.AddAgence(new Agence(null, "BA", "l'agence de BA", new ArrayList<>()));
			//Agence agence3 =  accountService.AddAgence(new Agence(null, "Housing", "l'agence 3", new ArrayList<>()));
			
			//	Create User
			//User user1=	accountService.addNewUser(new User(null,"Admin" ,"Admin" ,"Admin","admin@Risque.com","Admin1234",role1,agence1));
			
			//User user2 =accountService.addNewUser(new User(null,"Lyes","Lehara","LyesLehara","ManagerRisque@Risque.com","User4",role2,agence3));
						
            User user1=	accountService.addNewUser(new User(null,"Admin" ,"Admin" ,"Admin","admin@Risque.com","Admin1234",roleAdmin,null));
			
			User user2 =accountService.addNewUser(new User(null,"User1","User1","User1","ManagerRisque@Risque.com","User1",roleUtilisateur,null));
			
			
			//creer agence 
			
		
			
			//	Create Privileges 
			
			Privilege p1 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Saisir des données", "Saisir des données", new ArrayList()));
			Privilege p2 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Consulter des données/états/reportings/graphes", "Consulter des données/états/reportings/graphes",new ArrayList()));
			Privilege p3 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Saisir des commentaires", "Saisir des commentaires", new ArrayList()));
			Privilege p4 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Rechercher des données/états/reportings/graphes", "Rechercher des données/états/reportings/graphes",new ArrayList()));
			Privilege p5 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Générer les reportings", "Générer les reportings",new ArrayList()));
			Privilege p6 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Imprimer les états et reportings ", "Imprimer les états et reportings",new ArrayList()));
			Privilege p7 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Valider les données", "Valider les données",new ArrayList()));
			Privilege p8 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Consulter les alertes", "Consulter les alertes",new ArrayList()));
			Privilege p9 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Lancer les alertes", "Lancer les alertes",new ArrayList()));
			Privilege p10 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Editer les états et reportings", "Editer les états et reportings",new ArrayList()));
			Privilege p11 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Configuration des rôles et tâches", "Configuration des rôles et tâches",new ArrayList()));
			Privilege p12 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Paramétrage de la solution", "Paramétrage de la solution",new ArrayList()));
			Privilege p13 = ressourceService.createPrivilegeIfNotFound(new Privilege(null, "Supprimer  les données", "Supprimer  les données",new ArrayList()));
			//			ressource 
			
			Ressource r1 = ressourceService.CreateRessource(new Ressource(null, "  <Tout>", "Tout",new ArrayList()));
			
			
			//			Permissions
			
			Permissions per1 = ressourceService.createPermissions(new Permissions(p1,r1,p1.getNameP()+r1.getName()));
			Permissions per2 = ressourceService.createPermissions(new Permissions(p2,r1,p2.getNameP()+r1.getName()));
			Permissions per3 = ressourceService.createPermissions(new Permissions(p3,r1,p3.getNameP()+r1.getName()));
			Permissions per4 = ressourceService.createPermissions(new Permissions(p4,r1,p4.getNameP()+r1.getName()));
			
			Permissions per5 = ressourceService.createPermissions(new Permissions(p5,r1,p5.getNameP()+r1.getName()));
			Permissions per6 = ressourceService.createPermissions(new Permissions(p6,r1,p6.getNameP()+r1.getName()));
			Permissions per7 = ressourceService.createPermissions(new Permissions(p7,r1,p7.getNameP()+r1.getName()));
			Permissions per8 = ressourceService.createPermissions(new Permissions(p8,r1,p8.getNameP()+r1.getName()));
			Permissions per9 = ressourceService.createPermissions(new Permissions(p9,r1,p9.getNameP()+r1.getName()));
			
			Permissions per10 = ressourceService.createPermissions(new Permissions(p10,r1,p10.getNameP()+r1.getName()));
			Permissions per11 = ressourceService.createPermissions(new Permissions(p11,r1,p11.getNameP()+r1.getName()));
			Permissions per12 = ressourceService.createPermissions(new Permissions(p12,r1,p12.getNameP()+r1.getName()));
			Permissions per13 = ressourceService.createPermissions(new Permissions(p13,r1,p13.getNameP()+r1.getName()));
			
			//Add Permission to roles 
			
			// Administrateur
			
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per2.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per4.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per3.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per5.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per6.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per8.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per9.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per11.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per12.getId());
			ressourceService.PrermissionsToRoles(roleAdmin.getId(), per13.getId());
			
			
		   // Utilisateur
			
			ressourceService.PrermissionsToRoles(roleUtilisateur.getId(), per2.getId());
			ressourceService.PrermissionsToRoles(roleUtilisateur.getId(), per4.getId());
			ressourceService.PrermissionsToRoles(roleUtilisateur.getId(), per3.getId());
			ressourceService.PrermissionsToRoles(roleUtilisateur.getId(), per5.getId());
			ressourceService.PrermissionsToRoles(roleUtilisateur.getId(), per6.getId());
			ressourceService.PrermissionsToRoles(roleUtilisateur.getId(), per8.getId());
			
			//  Risque Manager			
				
				
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per2.getId());
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per4.getId());
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per5.getId());
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per6.getId());
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per3.getId());	
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per7.getId());	
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per8.getId());	
			ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per9.getId());	
			
			
			
		// Analyste Risque 			
			
			
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per2.getId());
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per4.getId());
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per5.getId());
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per6.getId());
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per3.getId());	
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per1.getId());	
		ressourceService.PrermissionsToRoles(roleRisqueManager.getId(), per8.getId());	
		ressourceService.PrermissionsToRoles(roleAnalysteRisque.getId(), per9.getId());	
		
		
		
		// Controleur	
		
		
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per2.getId());
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per4.getId());
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per5.getId());
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per6.getId());
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per3.getId());	
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per7.getId());	
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per8.getId());	
		ressourceService.PrermissionsToRoles(roleControleur.getId(), per9.getId());	
		
		
		// Visualiseur	
		
		
		ressourceService.PrermissionsToRoles(roleVisualiseur.getId(), per2.getId());
		ressourceService.PrermissionsToRoles(roleVisualiseur.getId(), per4.getId());
		
		ressourceService.PrermissionsToRoles(roleVisualiseur.getId(), per6.getId());		
		ressourceService.PrermissionsToRoles(roleVisualiseur.getId(), per8.getId());	
		


			
			
	
		};
	}

}
