package com.oracle.familytree.service;

import java.util.List;
import java.util.Map;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;

public interface FamilyTreeService {


	Map<RelationType, List<Person>> addRelation(Long personId1, Long personId2, RelationType relationType);

	Person addPerson(String name, String gender);

	List<Person> getAllPersons();

	Map<RelationType, List<Person>> getFamilyTree(Person p1);

	Map<RelationType, List<Person>> getFamilyTreeByPersonId(Long id);

}
