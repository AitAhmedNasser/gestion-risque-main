package it.gestionRisque.app.servicesRepoImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Repositories.EngagementRepositoryCustom;
import it.gestionRisque.app.Services.GenericSpesification;

@Repository
public class EngaggementRepositoryImpl implements EngagementRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	protected <S> Root<Engagement> applySpecificationToCriteria(Specification<Engagement> spec,
			CriteriaQuery<S> query) {
		Root<Engagement> root = query.from(Engagement.class);
	
		if (spec == null) {
			return root;
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);
	
		if (predicate != null) {
			query.where(predicate);
		}
		return root;
	}

	
	@Override
	public <S> List<Double> sumEngag(GenericSpesification<Engagement> spec, Class<S> resultType, String fieldName) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<S> query = builder.createQuery(resultType);
		Root<Engagement> root = applySpecificationToCriteria(spec, query);
		
        query.select(root.get(fieldName).as(resultType));
		TypedQuery<S> typedQuery = entityManager.createQuery(query);
		return (List<Double>) typedQuery.getResultList();

	}


	@Override
	public <S extends Number> S sum(Specification<Engagement> spec, Class<S> resultType, String fieldName , String dateReport)  {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<S> query = builder.createQuery(resultType);
		Root<Engagement> root = applySpecificationToCriteria(spec, query);
		Join<Engagement, Client> engagementClient = root.join("client");
        query.select(builder.sum(root.get(fieldName).as(resultType)));
        Expression<String> dateStringExpr = builder.function("TO_CHAR", String.class, engagementClient.get("reportingDate"), builder.literal("yyyy-MM-dd"));
		engagementClient.on(builder.equal(dateStringExpr,dateReport));
		TypedQuery<S> typedQuery = entityManager.createQuery(query);
		return typedQuery.getSingleResult();
	}
}
