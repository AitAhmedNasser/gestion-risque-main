package it.gestionRisque.app.di.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {
	
	@PostMapping("/testing")
	public String upload() throws Exception{
		  Date javaDate= DateUtil.getJavaDate((double) 41275.00);
	      // System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(javaDate));
	       return new SimpleDateFormat("MM/dd/yyyy").format(javaDate);
	}

}
