package com.oracle.familytree.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.repository.PersonRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddRelationsHelperTest {

	@Autowired
	AddRelationsHelper addRelationsHelper;
	
	@Autowired
	PersonRepository personRepository;
	
	@Test
	public void testGetRelatives() {
		Map<RelationType, List<Person>> relations = addRelationsHelper.getRelatives(1L);
		System.out.println(relations);
		assertTrue(!relations.isEmpty());
	}
	
	@Test
	public void testRelativesByRelationShip() {
		List<Person> childs = addRelationsHelper.getRelatives(personRepository.getOne(1L), RelationType.CHILD);
		System.out.println(childs);
		assertTrue(!childs.isEmpty());
	}
}
