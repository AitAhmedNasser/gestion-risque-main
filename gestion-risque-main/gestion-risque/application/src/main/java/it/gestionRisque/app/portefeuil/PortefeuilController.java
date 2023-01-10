package it.gestionRisque.app.portefeuil;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portefeuille")
@CrossOrigin("*")
public class PortefeuilController {
	@Autowired
	PortefeuilService portefeuilleService;
	
    // ****	for testing 
	@PostMapping("/testing")
	public List<ISum> testing(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {

		try {
			return portefeuilleService.testing(portefeuille.getDate_debut(), portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// **** TOTAL PORTEFEUILLE DIRECT BETWEEN TWO DATES ****
	@PostMapping("/directtwodates")
	public Map<String, Double> porteFeuilleDirect(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {

		try {
			return portefeuilleService.portefeuilleDirect(portefeuille.getDate_debut(), portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// **** TOTAL PORTEFEUILLE DIRECT ON SPECIFIC DATE ****
	@PostMapping("directspecificdate")
	public Double porteFeuilleDirectSpecificDate(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectOnSpecificDate portefeuille) throws Exception {

		try {
			return portefeuilleService.portefeuilleDirectOnSpesificDate(portefeuille.getDate_reporting());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// **** CREANCE DOUTEUSE BETWEEN TWO DATES ****
	@PostMapping("/creancedouteusetwodates")
	public Map<String, Double> creanceDouteuseBetweenTwoDates(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {

		try {
			return portefeuilleService.creanceDouteuseBetweenTwoDates(portefeuille.getDate_debut(),
					portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// **** CREANCE COURANTES BETWEEN TWO DATES ****
	@PostMapping("/creancecourantestwodates")
	public Map<String, Double> creanceCouranteBetweenTwoDates(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {

		try {
			return portefeuilleService.creanceCouranteBetweenTwoDates(portefeuille.getDate_debut(),
					portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// **** INTERETS RESERVES BETWEEN TWO DATES ****
	@PostMapping("/interets_reserves")
	public Map<String, Double> interetsReservesBetweenTwoDates(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {

		try {
			return portefeuilleService.interetsReserve(portefeuille.getDate_debut(), portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// ***** PROVISIONS TWO DATES ****
	@PostMapping("/provisions")
	public Map<String, Double> provions(@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille)
			throws Exception {

		try {
			return portefeuilleService.provisions(portefeuille.getDate_debut(), portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// ***** INTERETS RESERVES DES CREANCE DOUTEUSES ****
	@PostMapping("/interets_reserves_creances_douteuses")
	public Map<String, Double> interetsReservesCreanceDouteuse(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {

		try {
			return portefeuilleService.interetReservesCreancesDouteuse(portefeuille.getDate_debut(),
					portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// **** TAUX DE DEFAUT ****
	@PostMapping("/taux_defaut")
	public Map<String, Double> tauxDefaut(@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille)
			throws Exception {
		try {
			return portefeuilleService.tauxDefaut(portefeuille.getDate_debut(), portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	// **** TAUX DE COUVERTURE ****
	@PostMapping("/taux_couverture")
	public Map<String, Double> tauxCouverture(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {
		try {
			return portefeuilleService.tauxCouverture(portefeuille.getDate_debut(), portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	// **** CREANCE DOUTEUSE NET D'INTERETS RESERVES ****
	@PostMapping("/creance_douteuse_net")
	public Map<String, Double> creanceDouteuseNetInteretsReserve(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {
		try {
			return portefeuilleService.creanceDouteusesNetInteretsReserves(portefeuille.getDate_debut(),
					portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	// **** CREDIT TOTAL NET D'INTERETS RESERVES
	@PostMapping("/credit_net")
	public Map<String, Double> creditTotalNetInteretsReserve(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectBetweenTwoDate portefeuille) throws Exception {
		try {
			return portefeuilleService.creditNetInteretsReserves(portefeuille.getDate_debut(),
					portefeuille.getDate_fin());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	// ------------------------------------------------------------------------------------------
	// **** CREANCE DOUTEUSE ON SPECIFIC DATE ****
	@PostMapping("/creancedouteusespecificdate")
	public Double creanceDouteuseSpecificDate(
			@RequestBody PortefeuilDirectDTO.PortfeuilleDirectOnSpecificDate portefeuille) throws Exception {

		try {
			return portefeuilleService.creanceDouteuseOnSpecificDate(portefeuille.getDate_reporting());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw (e);
		}
	}
}
