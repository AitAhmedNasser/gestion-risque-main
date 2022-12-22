package it.gestionRisque.app.di.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.gestionRisque.app.di.Services.SeedTablesService;



@RestController
@CrossOrigin("*")
public class SeedTablesController {
	
private final SeedTablesService seedTables;
	
	public SeedTablesController(SeedTablesService seedTables) {
		this.seedTables = seedTables;
	}
	
	@PostMapping("/seed")
	public void upload(@RequestParam("file") MultipartFile file) throws Exception{
		 seedTables.addToTables(file);
	}

}
