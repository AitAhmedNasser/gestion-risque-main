package it.gestionRisque.app.portefeuil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.Repositories.ClientRepository;
import it.gestionRisque.app.Repositories.EngagementRepository;

@Service
public class PortefeuilService {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	EngagementRepository engagementRepository;

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();

	// **** testing ****
	public List<ISum> testing(String date1, String date2) throws ParseException {
		Date d1 = formatter.parse(date1);
		Date d2 = formatter.parse(date2);
		return clientRepository.sumSolde_balanceByReporting_date(d1, d2);
	}

	// **** TOTAL PORTEFEUILLE DIRECT BETWEEN TWO DATES ****
	public Map<String, Double> portefeuilleDirect(String date1, String date2) throws Exception {
		try {
			Date d1 = formatter.parse(date1);
			Date d2 = formatter.parse(date2);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> response = clientRepository.soldeBalanceBetweenTwoDate(d1, d2);

			return Utils.toStringDoubleMap(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** CREANCE DOUTEUSE BETWEEN TWO DATES ****
	public Map<String, Double> creanceDouteuseBetweenTwoDates(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> response = engagementRepository.creanceDouteuseBetweenTwoDate(d1, d2);

			return Utils.toStringDoubleMap(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** CREANCE COURANTES TWO DATES ****
	public Map<String, Double> creanceCouranteBetweenTwoDates(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));

			List<ISum> totalCreditDirect = clientRepository.soldeBalanceBetweenTwoDate(d1, d2);
			List<ISum> creanceDouteuse = engagementRepository.creanceDouteuseBetweenTwoDate(d1, d2);

			// create creance courante map
			Map<String, Double> response = new HashMap<String, Double>();
			for (ISum map1 : totalCreditDirect) {
				for (ISum map2 : creanceDouteuse) {
					if (map1.getReportingDate().compareTo(map2.getReportingDate()) == 0 && map1.getSum() != null
							&& map2.getSum() != null) {
						response.put(formatter.format(map1.getReportingDate()), map1.getSum() - map2.getSum());
					}
				}
				;
				if (response.get(formatter.format(map1.getReportingDate())) == null) {
					response.put(formatter.format(map1.getReportingDate()), map1.getSum());
				}
			}
			;

			return response;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** INTERETS RESERVE TWO DATES*****
	public Map<String, Double> interetsReserve(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> response = engagementRepository.interetsReservesTwoDates(d1, d2);

			return Utils.toStringDoubleMap(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** PROVISIONS TAWO DATES ****
	public Map<String, Double> provisions(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> response = engagementRepository.provisions(d1, d2);

			return Utils.toStringDoubleMap(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** INTERETS RESERVES CREANCE DOUTEUSES TWO DATES ****
	public Map<String, Double> interetReservesCreancesDouteuse(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> response = engagementRepository.interetesReservesCreancesDouteuses(d1, d2);

			return Utils.toStringDoubleMap(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** TAUX DE DEFAUT ****
	public Map<String, Double> tauxDefaut(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));

			List<ISum> creanceDouteuse = engagementRepository.creanceDouteuseBetweenTwoDate(d1, d2);
			List<ISum> interetsReservesCreanceDouteuse = engagementRepository.interetesReservesCreancesDouteuses(d1,
					d2);
			List<ISum> totalCreditDirect = clientRepository.soldeBalanceBetweenTwoDate(d1, d2);
			List<ISum> interetsReserves = engagementRepository.interetsReservesTwoDates(d1, d2);

			// 2-5 Map
			Map<Date, Double> response_2_5 = new HashMap<Date, Double>();
			for (ISum map1 : creanceDouteuse) {
				for (ISum map2 : interetsReservesCreanceDouteuse) {
					if (map1.getReportingDate().compareTo(map2.getReportingDate()) == 0 && map1.getSum() != null
							&& map2.getSum() != null) {
						response_2_5.put(map1.getReportingDate(), map1.getSum() - map2.getSum());
					}
				}
				;
				if (response_2_5.get(map1.getReportingDate()) == null) {
					response_2_5.put(map1.getReportingDate(), map1.getSum());
				}
			}
			;

			// 1-3 Map
			Map<Date, Double> response_1_3 = new HashMap<Date, Double>();
			for (ISum map1 : totalCreditDirect) {
				for (ISum map2 : interetsReserves) {
					if (map1.getReportingDate().compareTo(map2.getReportingDate()) == 0 && map1.getSum() != null
							&& map2.getSum() != null) {
						response_1_3.put(map1.getReportingDate(), map1.getSum() - map2.getSum());
					}
				}
				;
				if (response_1_3.get(map1.getReportingDate()) == null) {
					response_1_3.put(map1.getReportingDate(), map1.getSum());
				}
			}
			;

			Map<String, Double> response = new HashMap<String, Double>();

			response_2_5.forEach((key, value) -> {
				response_1_3.forEach((key2, value2) -> {
					if (key.compareTo(key2) == 0 && value != null && value2 != null && value2 != 0) {
						response.put(formatter.format(key), value / value2);
					}
				});
				if (response.get(formatter.format(key)) == null) {
					response.put(formatter.format(key), value);
				}
			});

			return response;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** TAUX DE COUVERTURE ****
	public Map<String, Double> tauxCouverture(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));

			List<ISum> provisions = engagementRepository.provisions(d1, d2);
			List<ISum> creanceDouteuse = engagementRepository.creanceDouteuseBetweenTwoDate(d1, d2);
			List<ISum> interetsReservesCreanceDouteuse = engagementRepository.interetesReservesCreancesDouteuses(d1,
					d2);

			// 2-5 Map
			Map<Date, Double> response_2_5 = new HashMap<Date, Double>();
			for (ISum map1 : creanceDouteuse) {
				for (ISum map2 : interetsReservesCreanceDouteuse) {
					if (map1.getReportingDate().compareTo(map2.getReportingDate()) == 0 && map1.getSum() != null
							&& map2.getSum() != null) {
						response_2_5.put(map1.getReportingDate(), map1.getSum() - map2.getSum());
					}
				}
				;
				if (response_2_5.get(map1.getReportingDate()) == null) {
					response_2_5.put(map1.getReportingDate(), map1.getSum());
				}
			}
			;

			Map<String, Double> response = new HashMap<String, Double>();
			for (ISum provision : provisions) {
				response_2_5.forEach((key2, value2) -> {
					if (provision.getReportingDate().compareTo(key2) == 0 && provision.getSum() != null
							&& value2 != null) {
						response.put(formatter.format(provision.getReportingDate()), provision.getSum() / value2);
					}
				});
				if (response.get(formatter.format(provision.getReportingDate())) == null) {
					response.put(formatter.format(provision.getReportingDate()), provision.getSum());

				}
			}
			;

			return response;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** CREANCE DOUTEUSE NET D'INTERETS RESERVES ****
	public Map<String, Double> creanceDouteusesNetInteretsReserves(String date_debut, String date_fin)
			throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));

			List<ISum> creanceDouteuse = engagementRepository.creanceDouteuseBetweenTwoDate(d1, d2);
			List<ISum> interetsReservesCreanceDouteuse = engagementRepository.interetesReservesCreancesDouteuses(d1,
					d2);

			// 2-5 Map
			Map<String, Double> response_2_5 = new HashMap<String, Double>();
			for (ISum map1 : creanceDouteuse) {
				for (ISum map2 : interetsReservesCreanceDouteuse) {
					if (map1.getReportingDate().compareTo(map2.getReportingDate()) == 0 && map1.getSum() != null
							&& map2.getSum() != null) {
						response_2_5.put(formatter.format(map1.getReportingDate()), map1.getSum() - map2.getSum());
					}
				}
				;
				if (response_2_5.get(formatter.format(map1.getReportingDate())) == null) {
					response_2_5.put(formatter.format(map1.getReportingDate()), map1.getSum());
				}
			}
			;

			return response_2_5;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// **** CREDIT DIRECT NET D'INTERETS RESERVE
	public Map<String, Double> creditNetInteretsReserves(String date_debut, String date_fin) throws Exception {
		try {
			Date d1 = formatter.parse(date_debut);
			Date d2 = formatter.parse(date_fin);
			calendar.setTime(d2);
			d1 = DateUtils.setDays(d1, 1);
			d2 = DateUtils.setDays(d2,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> totalCreditDirect = clientRepository.soldeBalanceBetweenTwoDate(d1, d2);
			List<ISum> interetsReserves = engagementRepository.interetsReservesTwoDates(d1, d2);

			Date totalCreditDirect_d1 = d1;
			Date interetsReserves_d1 = d1;

//			Map<Date, Double> totalCreditDirectMap = Utils.toDateDoubleMap(totalCreditDirect, totalCreditDirect_d1);
//			Map<Date, Double> interetsReservesMap = Utils.toDateDoubleMap(interetsReserves, interetsReserves_d1);

			// 1-3 Map
			Map<String, Double> response_1_3 = new HashMap<String, Double>();
			for (ISum map1 : totalCreditDirect) {
				for (ISum map2 : interetsReserves) {
					if (map1.getReportingDate().compareTo(map2.getReportingDate()) == 0 && map1.getSum() != null
							&& map2.getSum() != null) {
						response_1_3.put(formatter.format(map1.getReportingDate()), map1.getSum() - map2.getSum());
					}
				}
				;
				if (response_1_3.get(formatter.format(map1.getReportingDate())) == null) {
					response_1_3.put(formatter.format(map1.getReportingDate()), map1.getSum());
				}
			}
			;

			return response_1_3;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
			// TODO: handle exception
		}
	}

	// -------------------------------- SPECIFICS DATES --------------------------

	// **** TOTAL PORTEFEUILLE DIRECT ON SPECIFIC DATE ****
	public Double portefeuilleDirectOnSpesificDate(String date) throws Exception {
		try {
			Date specificDate = formatter.parse(date);
			calendar.setTime(specificDate);
			Date date_debut = DateUtils.setDays(specificDate, 1);
			Date date_fin = DateUtils.setDays(specificDate,
					Utils.getNumberOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
			List<ISum> soldeBalances = clientRepository.soldeBalanceBetweenTwoDate(date_debut, date_fin);
			if (soldeBalances.size() == 0)
				return null;
			return soldeBalances.get(0).getSum();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}

	}

	// **** CREANCE DOUTEUSE ON SPECIFIC DATE ****
	public Double creanceDouteuseOnSpecificDate(String date) throws Exception {
		try {
			Date specificDate = formatter.parse(date);
			Double soldeBalances = engagementRepository.creanceDouteuseOnSpecificDate(specificDate);
			return soldeBalances;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}

	}

}
