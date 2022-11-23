package it.gestionRisque.app.di.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.gestionRisque.app.di.Services.ExcelToJsonService;


@RestController
@CrossOrigin("*")
public class ExcelToJsonControler {
private final ExcelToJsonService uploadService;
	
	public ExcelToJsonControler(ExcelToJsonService uploadService) {
		this.uploadService = uploadService;
	}
	
	@PostMapping("/upload")
	public List<Map<String, String>> upload(@RequestBody String fileName) throws Exception{
		return uploadService.upload(fileName);
	}
	@GetMapping("/")
	public String welcome() {
		return "Welcome from Risk Management project ! ";
	}
}
