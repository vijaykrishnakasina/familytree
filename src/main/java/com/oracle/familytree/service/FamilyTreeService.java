package com.oracle.familytree.service;

import java.util.List;
import java.util.Map;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;

public interface FamilyTreeService {


	Map<RelationType, List<Person>> addRelation(Long personId1, Long personId2, RelationType relationType);

	//can be removed
	Person addPerson(String name, String gender);

	Person addPerson(Person person);
	
	List<Person> getAllPersons();

	Map<RelationType, List<Person>> getFamilyTree(Person p1);

	Map<RelationType, List<Person>> getFamilyTreeByPersonId(Long id);

	Map<RelationType, List<Person>> addRelation(Person person1, Person person2, RelationType relationType);

}
