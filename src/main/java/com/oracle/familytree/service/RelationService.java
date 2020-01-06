package com.oracle.familytree.service;

import static com.oracle.familytree.dto.RelationType.CHILD;
import static com.oracle.familytree.dto.RelationType.GRAND_CHILD;
import static com.oracle.familytree.dto.RelationType.GRAND_PARENT;
import static com.oracle.familytree.dto.RelationType.PARENT;
import static com.oracle.familytree.dto.RelationType.SIBLING;
import static com.oracle.familytree.dto.RelationType.SPOUSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.Relation;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.repository.PersonRepository;
import com.oracle.familytree.repository.RelationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RelationService {

	@Autowired
	PersonRepository personRepo;

	@Autowired
	RelationRepository relationRepository;

	
	/**
	 * returns all relatives of the given person by person id group by relation type
	 * @param person_id
	 * @return
	 */
	public Map<RelationType, List<Person>> getRelatives(Long person_id) {
		log.info("finding relatives for "+person_id);
		List<Relation> relations = relationRepository.findAllByPerson_id(person_id);
		 return relations.stream().collect(Collectors.groupingBy(Relation::getRelationType, Collectors.mapping(Relation::getRelatee, Collectors.toList())));
		
	}

	/**
	 * Responsible for creating relations between person and relative
	 * 
	 * @param personId1
	 * @param personId2
	 * @param relationType
	 * @return
	 */
	public Map<RelationType, List<Person>> addRelation(Long personId1, Long personId2, RelationType relationType) {

		try {
			// using getOne instead of findByID as we should be having the ids in the
			// persons table.

			Person person = personRepo.getOne(personId1);
			Person relative = personRepo.getOne(personId2);

			Map<RelationType, List<Person>> personsFamily = getRelatives(person.getId());
			Map<RelationType, List<Person>> relativesFamily = getRelatives(relative.getId());

			addRelationV2(person, personsFamily, relative, relationType);
			addRelationV2(relative, relativesFamily, person, relationType.getInverseRelation());

			return getRelatives(person.getId());
			// TODO: Handle correct type of exception
		} catch (HibernateException e) {
			log.error("Person is not found in the DB with given id");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * for every one in person's family we need to add the relation with relative
	 * 
	 * @param person
	 * @param personsFamily
	 * @param relative
	 * @param relationType
	 */
	private void addRelationV2(Person person, Map<RelationType, List<Person>> personsFamily, Person newRelative,
			RelationType newRelationType) {
		Map<RelationType, List<Person>> personRelatives = getRelatives(person.getId());

		List<Relation> relations = new ArrayList<>();
		relations.add(Relation.builder().person(person).relatee(newRelative).relationType(newRelationType).build());

		for (RelationType existingRelationWithRelative : personRelatives.keySet()) {
			List<Person> relatives = personRelatives.get(existingRelationWithRelative);

			RelationType relativeRelationWithNewMember = getRelation(existingRelationWithRelative, newRelationType);
			if (relativeRelationWithNewMember != null) {
				for (Person existingRelative : relatives) {
					relations.add(Relation.builder().person(existingRelative).relatee(newRelative)
							.relationType(relativeRelationWithNewMember).build());
					relations.add(Relation.builder().person(newRelative).relatee(existingRelative)
							.relationType(relativeRelationWithNewMember.getInverseRelation()).build());
				}
			}
			

		}

		relationRepository.saveAll(relations);

	}

	/**
	 * finds the relation between existingRelative and  newRelative with relationType
	 * 
	 * getRelation(SPOUSE, CHILD) would return CHILD
	 * 
	 * which means what would a person's SPOUSE call person's CHILD == CHILD
	 * 
	 * @param existingRelative
	 * @param newRelative
	 * @return
	 */
	private RelationType getRelation(RelationType existingRelative, RelationType newRelative) {

		if (SPOUSE.equals(existingRelative)) {
			switch (newRelative) {

			case CHILD:
				return CHILD;
			case GRAND_CHILD:
				return GRAND_CHILD;
			default:
				return null;
			}
		}

		if (SIBLING.equals(existingRelative)) {
			switch (newRelative) {
			case GRAND_PARENT:
				return GRAND_PARENT;
			case PARENT:
				return PARENT;
			case SIBLING:
				return SIBLING;
			default:
				return null;
			}

		}

		if (CHILD.equals(existingRelative)) {
			switch (newRelative) {
			case PARENT:
				return GRAND_PARENT;
			case SPOUSE:
				return PARENT;
			case CHILD:
				return SIBLING;
			default:
				return null;
			}

		}

		if (PARENT.equals(existingRelative)) {
			switch (newRelative) {
			case PARENT:
				return SPOUSE;
			case CHILD:
				return GRAND_CHILD;
			case SIBLING:
				return CHILD;
			default:
				return null;
			}

		}

		if (GRAND_PARENT.equals(existingRelative)) {
			switch (newRelative) {
			case SIBLING:
				return GRAND_CHILD;
			default:
				return null;
			}

		}

		if (GRAND_CHILD.equals(existingRelative)) {
			switch (newRelative) {
			case SPOUSE:
				return GRAND_PARENT;
			default:
				return null;
			}

		}

		return null;
	}

	

}
