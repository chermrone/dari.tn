package tn.dari.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.jhipster.service.QueryService;
import tn.dari.spring.dto.FournitureAdCriteria;
import tn.dari.spring.dto.FournitureAdDto;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.FournitureAd_;
import tn.dari.spring.repository.FournitureAdRepository;

@Service
@Transactional(readOnly = true)
public class FournitureAdQueryService extends QueryService<FournitureAd> {

	@Autowired
	FournitureAdRepository fournitureAdRepository;
	@Autowired
	private ModelMapper modelMapper;

	private final Logger log = LoggerFactory.getLogger(FournitureAdQueryService.class);

	@Transactional(readOnly = true)
	public List<FournitureAd> findByCriteria(FournitureAdCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		System.out.println("find by criteria : {}" + criteria);
		final Specification<FournitureAd> specification = createSpecification(criteria);
		System.out.println("specification :::" + specification);
		return fournitureAdRepository.findAll(specification);
	}

	/**
	 * Return a {@link Page} of {@link CommentDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	/*
	 * @Transactional(readOnly = true) public Page<FournitureAdDto>
	 * findByCriteria(CommentCriteria criteria, Pageable page) {
	 * log.debug("find by criteria : {}, page: {}", criteria, page); final
	 * Specification<Comment> specification = createSpecification(criteria); return
	 * commentRepository.findAll(specification, page).map(commentMapper::toDto); }
	 */

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(FournitureAdCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<FournitureAd> specification = createSpecification(criteria);
		return fournitureAdRepository.count(specification);
	}

	/**
	 * Function to convert {@link CommentCriteria} to a {@link Specification}
	 * 
	 * @param criteria
	 *            The object which holds all the filters, which the entities
	 *            should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<FournitureAd> createSpecification(FournitureAdCriteria criteria) {
		Specification<FournitureAd> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getFaID() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getFaID(), FournitureAd_.faID));
			}
			if (criteria.getNameFa() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getNameFa() , FournitureAd_.nameFa));
			}
			if (criteria.getAddress() != null) {
				specification = specification.and(buildStringSpecification(criteria.getAddress(), FournitureAd_.address));
			}
			if (criteria.getDescription() != null) {
				specification = specification.and(buildStringSpecification(criteria.getDescription(), FournitureAd_.description));
			}
			if (criteria.getCreated() != null) {

				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

				
				String dateInString = criteria.getCreated().toString();
				System.out.println("criteria.getCreated().toString(): "+ dateInString);
				// Date date = formatter.parse(dateInString);
				

				specification = specification
						.and(buildStringSpecification(criteria.getCreated(), FournitureAd_.created));
			}
			if (criteria.getPrice() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getPrice(), FournitureAd_.price));
			}
			if (criteria.getUserName() != null) {
				specification = specification.and(buildStringSpecification(criteria.getUserName(), FournitureAd_.userName));
			}
		}
		return specification;
	}

}
