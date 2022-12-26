package it.gestionRisque.app.Repositories;


import java.text.ParseException;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Services.GenericSpesification;

public interface EngagementRepositoryCustom {
	
	<S> List<Double> sumEngag(GenericSpesification<Engagement> spec, Class<S> resultType, String fieldName);


	<S extends Number> S sum(Specification<Engagement> spec, Class<S> resultType, String fieldName , String dateReport) ;
}
