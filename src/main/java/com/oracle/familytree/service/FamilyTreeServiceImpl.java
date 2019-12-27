package com.oracle.familytree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
		Map<RelationType, List<Person>> result = new LinkedHashMap<>();
		List<Person> persons = new ArrayList<>();
	// TODO : get all relatives and use streams.collect(groupby relation)
		result.put(RelationType.GRAND_PARENT, addRelationsHelper.getRelatives(p1, RelationType.GRAND_PARENT));
		result.put(RelationType.PARENT, addRelationsHelper.getRelatives(p1, RelationType.PARENT));
		result.put(RelationType.SPOUSE, addRelationsHelper.getRelatives(p1, RelationType.SPOUSE));
		return result;
	}

	@Override
	public Map<RelationType, List<Person>> addRelation(Long personId1, Long personId2, RelationType relationType) {

		try {
			// using getOne instead of findByID as we should be having the ids in the
			// persons table.

			Person p1 = personRepo.getOne(personId1);
			Person p2 = personRepo.getOne(personId2);

			return addRelation(p1, p2, relationType);

			// TODO: Handle correct type of exception
		} catch (HibernateException e) {
			System.out.println("Person is not found in the DB with given id");
			e.printStackTrace();
		}
		return null;
	}

	private Map<RelationType, List<Person>> addRelation(Person p1, Person p2, RelationType relationType) {
		switch (relationType) {
		case PARENT:
			// if a parent is added to a person we need to link the parent - p2 to GP, P, Sib, Child of that person p1
			addRelationsHelper.addToSelf(p1, p2,RelationType.PARENT);
			addRelationsHelper.addToGrandParents(p1, p2, RelationType.CHILD);
			addRelationsHelper.addToParents(p1, p2, RelationType.SPOUSE);
			addRelationsHelper.addToSiblings(p1, p2, RelationType.PARENT);
			addRelationsHelper.addToChilds(p1, p2, RelationType.GRAND_PARENT);
			break;

		case CHILD:
			addRelationsHelper.addToParents(p1, p2, RelationType.GRAND_CHILD);
			addRelationsHelper.addToSpouse(p1, p2, RelationType.CHILD);
			addRelationsHelper.addToChilds(p1, p2, RelationType.SIBLING);
			
			// TODO: how to know if there are many childs and many grand childs ..... we need to restrict this one.. 
			addRelationsHelper.addToGrandChilds(p1, p2, RelationType.PARENT);
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
	public List<Person> getAllPersons() {
		return personRepo.findAll();
	}

	@Override
	public Map<RelationType, List<Person>> getFamilyTreeByPersonId(Long id) {
		return addRelationsHelper.getRelatives(id);
	}

}
