package it.gestionRisque.app.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import it.gestionRisque.app.Repporting.KeyColumns;
import it.gestionRisque.app.Repporting.Periodes;
import it.gestionRisque.app.Repporting.Reporting;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@Service
public class RepportServices {

	
	
	public JasperPrint createJP(List<Object> listData,List <String> periode,List<String> keyColumnNames,Style dataStyle,String title ) throws StreamReadException
	, DatabindException, MalformedURLException, IOException, JRException, ParseException {

		FastReportBuilder fastReportBuilder = new FastReportBuilder();
		for (int i = 0; i <  periode.size(); i++) {
			fastReportBuilder.addColumn(periode.get(i),keyColumnNames.get(i) , String.class.getName(), 150, dataStyle);
		}
	   
		fastReportBuilder.addGroups(0).setTitle(title)
				.setSubtitle("This report was generated at " + new Date()).setPrintBackgroundOnOddRows(true);
		// .setUseFullPageWidth(true);
		DynamicReport dynamicReport = fastReportBuilder.build();

		JRDataSource dataSource = new JRBeanCollectionDataSource(listData);
		// PRIVIDING JAVA MODEL AS DATA SOURCE
	     JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), dataSource);
		


	return jasperPrint;
		

		
		
	}





	public ResponseEntity<byte[]> createReporting(String json) throws StreamReadException, DatabindException, MalformedURLException, IOException, JRException, ParseException {
		ObjectMapper mapper = new ObjectMapper();

		JSONObject jsonObject = new JSONObject(json);		

		System.out.println(" json   " +json);
		//get periode Object
	    Periodes periode = mapper.readValue(jsonObject.get("periode").toString(),  Periodes.class);
	    //get Key column Object
	    KeyColumns keyColumns = mapper.readValue(jsonObject.get("keyColumns").toString(),  KeyColumns.class);
	    //get data array of objects
	    List<Reporting> dataReporting = mapper.readValue(jsonObject.get("data").toString(), mapper.getTypeFactory().constructCollectionType(List.class, Reporting.class));
	    
		List<Object> entrepriseData,retailData,totalsData = new ArrayList<>();

		//construire List of object entreprise, retail , totals
		entrepriseData = dataReporting.stream().map(object->object.getEntreprise()).collect(Collectors.toList());
	    retailData = dataReporting.stream().map(object->object.getRetail()).collect(Collectors.toList());
		totalsData = dataReporting.stream().map(object->object.getTotals()).collect(Collectors.toList());
		
		//style of columns
		Style dataStyle = new Style();
		dataStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		dataStyle.setFont(Font.ARIAL_SMALL);
		dataStyle.setBlankWhenNull(true);
		dataStyle.setOverridesExistingStyle(false);
		dataStyle.setBorder(Border.PEN_2_POINT());
		dataStyle.setBorderTop(Border.PEN_2_POINT());

		//get list periodes of entreprise(titles fo columns) 
	    List<String> periodesEntreprise = periode.getPeriodeEntrperise();
	    //get list columnName Property name of entreprise
	    List <String> columnNamesEntreprise = keyColumns.getEntrepriseKey();
	    //create jasper print
	  JasperPrint jpEntreprise=  this.createJP(entrepriseData, periodesEntreprise, columnNamesEntreprise, dataStyle,"Crédit Entreprise");
	  
	  List<String> periodesRetails = periode.getPeriodeRetails();
	  List <String> columnNamesRetails = keyColumns.getRetailKey();
	  JasperPrint jpRetail=  this.createJP(retailData,periodesRetails, columnNamesRetails, dataStyle,"Crédit Retail");
	  
	  List<String> periodesTotal = periode.getPeriodeTotal();
	  List <String> columnNames3 = keyColumns.getTotalKey();
	  JasperPrint jpTotal=  this.createJP(totalsData,periodesTotal, columnNames3, dataStyle,"Crédit Total");
	  
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		list.add(jpEntreprise);
		list.add(jpRetail);
		list.add(jpTotal);
		
		
		
	   
	    SimpleOutputStreamExporterOutput outputStream = new SimpleOutputStreamExporterOutput("reporting2.pdf");

		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(list));
		SimplePdfReportConfiguration configuration = new SimplePdfReportConfiguration();
		configuration.setSizePageToContent(true);
		//configuration.setForceLineBreakPolicy(false);
		configuration.getEndPageIndex();
		configuration.setCollapseMissingBookmarkLevels(true);
		configuration.setEndPageIndex(0);
		configuration.setIgnoreHyperlink(true);
		configuration.setForceSvgShapes(true);
	    exporter.setConfiguration(configuration);
	    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfOutputStream));
	   
	    
        // exporter.setExporterOutput(outputStream);
	    exporter.exportReport();
	    byte[] data = pdfOutputStream.toByteArray();
		
	    pdfOutputStream.close();
	    System.out.println("data<<<<<<<<<<"+data);
		 HttpHeaders headers = new HttpHeaders();
		  headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=reporting.pdf");
		return   ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
}
