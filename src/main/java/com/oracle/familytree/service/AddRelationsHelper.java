package com.oracle.familytree.service;

import java.util.List;
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

	public void addToChilds(Person p1, Person p2, RelationType grandParent) {
		// TODO Auto-generated method stub
		
	}

	public void addToParents(Person p1, Person p2, RelationType grandChild) {
		// TODO Auto-generated method stub
		
	}

	public void addToGrandChilds(Person p1, Person p2, RelationType parent) {
		// TODO Auto-generated method stub
		
	}

	public List<Person> getRelatives(Person p1, RelationType relationType) {
		return relationRepository.findAllByPersonAndRelationType(p1, relationType).stream().map(relation -> relation.getRelatee()).collect(Collectors.toList());
	}

	public List<Person> getRelatives(Long person_id) {
		return relationRepository.findAllByPerson_id(person_id).stream().map(relation -> relation.getRelatee()).collect(Collectors.toList());
	}

	public void addToSelf(Person p1, Person p2, RelationType parent) {
		relationRepository.save(Relation.builder().person(p1).relatee(p2).relationType(parent).build());		
	}
}
