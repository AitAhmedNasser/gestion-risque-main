package it.gestionRisque.app.portefeuil;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

public class Utils {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

	// Method to get number of days in month
		public static int getNumberOfDaysInMonth(int year, int month) {

			YearMonth yearMonthObject = YearMonth.of(year, month + 1);
			int daysInMonth = yearMonthObject.lengthOfMonth();
			return daysInMonth;
		}
		
		// Convert List response to String Double Map
		public static Map<String, Double> toStringDoubleMap(List<ISum> list) {
			
			Map<String, Double> mapResponse = new HashMap<String, Double>();
			for (ISum map : list) {
				
				// d1 = DateUtils.setDays(d1, 30);
				mapResponse.put(formatter.format(map.getReportingDate()), map.getSum());
			}

			return mapResponse;
		}

		// Convert List response to Date Double Map
			public static Map<Date, Double> toDateDoubleMap(List<?> list, Date d1) {
				Map<Date, Double> mapResponse = new HashMap<Date, Double>();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Double t = (Double) iterator.next();
					mapResponse.put(d1, t);
					d1 = DateUtils.addMonths(d1, 1);
				}

				return mapResponse;
			}
}
