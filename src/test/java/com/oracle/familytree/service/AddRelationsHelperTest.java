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

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddRelationsHelperTest {

	@Autowired
	AddRelationsHelper addRelationsHelper;
	
	@Test
	public void test() {
		Map<RelationType, List<Person>> relations = addRelationsHelper.getRelatives(1L);
		System.out.println(relations);
		assertTrue(!relations.isEmpty());
	}
}
