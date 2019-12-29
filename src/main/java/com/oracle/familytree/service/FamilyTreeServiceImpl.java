package com.oracle.familytree.service;

import static com.oracle.familytree.dto.RelationType.CHILD;
import static com.oracle.familytree.dto.RelationType.GRAND_CHILD;
import static com.oracle.familytree.dto.RelationType.GRAND_PARENT;
import static com.oracle.familytree.dto.RelationType.PARENT;
import static com.oracle.familytree.dto.RelationType.SPOUSE;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.repository.PersonRepository;

@Service
public class FamilyTreeServiceImpl implements FamilyTreeService {

	@Autowired
	PersonRepository personRepo;

	@Autowired
	AddRelationsHelper addRelationsHelper;

	@Override
	public Map<RelationType, List<Person>> getFamilyTree(Person p1) {
		return getFamilyTreeByPersonId(p1.getId());
	}

	@Override
	public Map<RelationType, List<Person>> addRelation(Long personId1, Long personId2, RelationType relationType) {

		try {
			// using getOne instead of findByID as we should be having the ids in the
			// persons table.

			Person p1 = personRepo.getOne(personId1);
			Person p2 = personRepo.getOne(personId2);

			System.out.println(p1);
			System.out.println(p2);
			
			addRelation(p1, p2, relationType);
			addRelation(p2, p1, relationType.getInverseRelation());

			return getFamilyTreeByPersonId(p1.getId());
			// TODO: Handle correct type of exception
		} catch (HibernateException e) {
			System.out.println("Person is not found in the DB with given id");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method takes care of adding relationships to a person and also recursively adding relations with existing relatives of the person 
	 * @param p1
	 * @param p2
	 * @param relationType
	 * @return familyTree of the person after the adding new relationship
	 * 
	 */
	public Map<RelationType, List<Person>> addRelation(Person p1, Person p2, RelationType relationType) {
		
		System.out.println("adding "+relationType +" between " + p1.getId()+ " and "+ p2.getId());
		switch (relationType) {
		case PARENT:
			
			// add to self as parent
			addRelationsHelper.addToPerson(p1, p2,PARENT);

			//add to parent as spouse
			addRelationsHelper.addRelationToRelatee(p1, p2, PARENT, SPOUSE);
			
			// add to child as grandparent
			addRelationsHelper.addRelationToRelatee(p1, p2, CHILD, GRAND_PARENT);
			
			break;

		case CHILD:
			
			// add child to self
			addRelationsHelper.addToPerson(p1, p2, CHILD);
			
			// add grand-child to parents
			addRelationsHelper.addRelationToRelatee(p1, p2, PARENT, GRAND_CHILD);
			
			// add child to the spouse
			addRelationsHelper.addRelationToRelatee(p1, p2, SPOUSE, CHILD);
			
			// add parent to the grand-child
			addRelationsHelper.addRelationToRelatee(p1, p2, GRAND_CHILD, PARENT);
			
			break;
		
		case SPOUSE:
			
			// add spouse to the self
			addRelationsHelper.addToPerson(p1, p2, SPOUSE);
			
			// add parent to the child
			addRelationsHelper.addRelationToRelatee(p1, p2, CHILD, PARENT);
			
			// add grand-parent to all grand-child
			addRelationsHelper.addRelationToRelatee(p1, p2, GRAND_CHILD, GRAND_PARENT);
			
			break;
			
		case GRAND_CHILD:
			
			// add grand-parent to the self
			addRelationsHelper.addToPerson(p1, p2, GRAND_CHILD);
			
			// add parent to the child
			addRelationsHelper.addRelationToRelatee(p1, p2, CHILD, PARENT);
			
			break;
		
		case GRAND_PARENT:
			
			// add grand-parent to the self
			addRelationsHelper.addToPerson(p1, p2, GRAND_PARENT);
			
			break;
			
			
		default:
			break;
		}

		return getFamilyTree(p1);
	}

	@Override
	public Person addPerson(String name, String gender) {
		return personRepo.save(Person.builder().name(name).gender(gender).build());
	}
	
	@Override
	public Person addPerson(Person person) {
		return personRepo.save(person);
	}

	@Override
	public List<Person> getAllPersons() {
		return personRepo.findAll();
	}

	@Override
	public Map<RelationType, List<Person>> getFamilyTreeByPersonId(Long id) {
		return addRelationsHelper.getRelatives(id);
	}

}
