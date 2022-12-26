package it.gestionRisque.app.Services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.gestionRisque.app.DynamicQueries.SearchCriteria;
import it.gestionRisque.app.DynamicQueries.SearchOperation;

public class GenericSpesification<T> implements Specification<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SearchCriteria> list;

	public GenericSpesification() {
		this.list = new ArrayList<SearchCriteria>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Predicate predicate = null;
		for (SearchCriteria criteria : list) {
			if (predicate == null) {
				predicate = builder.and(this.buildPredicate(criteria, builder, root));
			}
			else if (criteria.isAND()){
				predicate = builder.and(predicate, this.buildPredicate(criteria, builder, root));
			} else {
				predicate = builder.or(predicate, this.buildPredicate(criteria, builder, root));
			}
		}
		return predicate;
	}

	private Predicate buildPredicate(SearchCriteria criteria, CriteriaBuilder builder, Root<T> root) {
		if (SearchOperation.EQUAL.equals(criteria.getOperation())) {
			return builder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (SearchOperation.NOT_EQUAL.equals(criteria.getOperation())) {
			return	builder.notEqual(root.get(criteria.getKey()), criteria.getValue().toString());
		}else if (SearchOperation.GREATER_THAN.equals(criteria.getOperation())) {
			return	builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
		}else if (SearchOperation.LESS_THAN.equals(criteria.getOperation())) {
			return	builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
		}else if (SearchOperation.GREATER_THAN_EQUAL.equals(criteria.getOperation())) {
			return	builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		}else if (SearchOperation.LESS_THAN_EQUAL.equals(criteria.getOperation())) {
			builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		}else if (SearchOperation.MATCH.equals(criteria.getOperation())) {
			return	builder.like(root.get(criteria.getKey()),"%" + criteria.getValue().toString() +"%" );
			
		}else if (SearchOperation.MATCH_END.equals(criteria.getOperation())) {
			return	builder.like(root.get(criteria.getKey()), criteria.getValue().toString()+ "%");
		}
		 return builder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
	}

}
