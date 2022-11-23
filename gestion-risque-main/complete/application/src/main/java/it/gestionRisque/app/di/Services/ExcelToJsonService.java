package it.gestionRisque.app.di.Services;



import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.di.Utils.UploadUtil;


@Service
public class ExcelToJsonService {
	
private final UploadUtil uploadUtil;
	
	public ExcelToJsonService(UploadUtil uploadUtil) {
		this.uploadUtil = uploadUtil;
	}

	
	int k=0;
	
	public List<Map<String, String>> upload(String fileName) throws Exception {


		Workbook workbook = new  XSSFWorkbook(fileName);
		

		Sheet sheet = workbook.getSheetAt(0);
		
		Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);
		
		Row headerRow = rowStreamSupplier.get().findFirst().get();
		
		List<String> headerCells = uploadUtil.getStream(headerRow)
				.map(Cell::getStringCellValue)
				.collect(Collectors.toList());
		
		
	   int colCount=headerCells.size();	
	   
	   return rowStreamSupplier.get().skip(1).map(row -> {
		    
			List<Map<Integer,String>> cellList = uploadUtil.getStream(row)
					.map(cell ->{	
						
					
						try {
							System.out.println(cell.getArrayFormulaRange());
							
						} catch (Exception e) {
							// TODO: handle exception
							
						}
						
//						cell.setCellType(CellType.STRING);
//						System.out.println(cell.getStringCellValue()+ " -> " + cell.getCellComment());
//						return Collections.singletonMap(cell.getColumnIndex(), cell.getStringCellValue());
						if(cell.getCellType()==CellType.STRING) {
							return Collections.singletonMap(cell.getColumnIndex(), cell.getStringCellValue());
						}else if(cell.getCellType()==CellType.NUMERIC) {
							
							Double cellVal = cell.getNumericCellValue();
							return Collections.singletonMap(cell.getColumnIndex(), cellVal.toString());
						}else {
							cell.setCellType(CellType.STRING);
							return Collections.singletonMap(cell.getColumnIndex(), cell.getStringCellValue());
						}
						
					}).collect(Collectors.toList());
			
			
			Map<String, String> map = new HashMap<String, String>() ;
			
			for(int i = 0; i < cellList.size(); i++) {
				k=0;
				cellList.get(i).forEach((key, value) ->  k = key );
				map.put(headerCells.get(k), cellList.get(i).get(k));
			}
			
			
			return map;
			
		}).collect(Collectors.toList());
	}
}
