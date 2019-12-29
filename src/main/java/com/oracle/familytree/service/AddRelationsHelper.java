package com.oracle.familytree.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.Relation;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.repository.RelationRepository;

@Service
public class AddRelationsHelper {
	
	@Autowired
	RelationRepository relationRepository;
	
	@Autowired
	FamilyTreeService familyTreeService;

	public void addToGrandParents(Person p1, Person p2, RelationType relationType) {
		// TODO Auto-generated method stub
		System.out.println("from addToGrandParents");
		List<Relation> relations = relationRepository.findAllByPersonAndRelationType(p1, RelationType.GRAND_PARENT);
		
		for (Relation relation : relations) {
			relationRepository.save(Relation.builder().person(relation.getRelatee()).relatee(p2).relationType(relationType).build());
			// TODO: Do we need to do recursively call this function ???
			// TODO: now we need to make this child as sibling to the grandfather's daughter for eg
			
		}
		System.out.println("after from addToGrandParents");
//		relationRepository.save(Relation.builder().person(p1).relatee(p2).relationType(RelationType.GRAND_PARENT).build());

	}

	public void addToSpouse(Person p1, Person p2, RelationType spouce) {
		// TODO Auto-generated method stub
		
	}

	public void addToSiblings(Person p1, Person p2, RelationType parent) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Get list of childs and add p2 as 
	 * @param p1
	 * @param p2
	 * @param grandParent
	 */
	public void addToChilds(Person p1, Person p2, RelationType grandParent) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Get list of parents of p1 and adds p2 as given RelationType
	 * @param p1
	 * @param p2
	 * @param relationType
	 */
	public void addToParents(Person p1, Person p2, RelationType relationType) {
		List<Person> parents = getRelatives(p1, RelationType.PARENT);
		for (Person parent : parents) {
			familyTreeService.addRelation(parent, p2, relationType);
		}
	}

	public void addToGrandChilds(Person p1, Person p2, RelationType parent) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *  Returns all relatives of person having {@code relationType}
	 * @param p1
	 * @param relationType
	 * @return
	 */
	public List<Person> getRelatives(Person p1, RelationType relationType) {
		return relationRepository.findAllByPersonAndRelationType(p1, relationType).stream().map(relation -> relation.getRelatee()).collect(Collectors.toList());
	}

	/**
	 * returns all relatives of the given person by person id group by relation type
	 * @param person_id
	 * @return
	 */
	public Map<RelationType, List<Person>> getRelatives(Long person_id) {
		List<Relation> relations = relationRepository.findAllByPerson_id(person_id);
		
		 return relations.stream().collect(Collectors.groupingBy(Relation::getRelationType, Collectors.mapping(Relation::getRelatee, Collectors.toList())));
		
	}

	/**
	 * adds the given relation to the person with the relatee
	 * @param p1
	 * @param p2
	 * @param relation
	 */
	public void addToPerson(Person p1, Person p2, RelationType relation) {
		//TODO: change to exists()
		
		List<Relation> relations = relationRepository.findAllByPersonAndRelateeAndRelationType(p1,p2,relation);
		
		if (relations.isEmpty()) {
			relationRepository.save(Relation.builder().person(p1).relatee(p2).relationType(relation).build());	
		}else {
			System.out.println("relation is already there not inserting again");
		}
			
	}


	/**
	 *  get all the relatives of {@code person1} of type {@code typeOfRelatives} and adds person2 as {@code newRelationType}
	 *  
	 *  <p><b>Example </b></p>
	 *  <p><b>
	 *  {@code addRelationToRelatee(p1, p2, RelationType.CHILD, RelationType.GRAND_PARENT) }</b></p>
	 *  
	 *  above call will takes all childs of p1 and adds p2 as grand parent to them all 
	 *  
	 * @param person1
	 * @param p2
	 * @param typeOfRelatives
	 * @param newRelationType
	 */
	public void addRelationToRelatee(Person p1, Person p2, RelationType typeOfRelatives, RelationType newRelationType) {
		List<Person> relatives = getRelatives(p1, typeOfRelatives);
		for (Person relative : relatives) {
			/*if(!p2.getId().equals(relative.getId()))
			familyTreeService.addRelation(relative, p2, newRelationType);*/
			addToPerson(relative, p2, newRelationType);
		}
		
	}
}
