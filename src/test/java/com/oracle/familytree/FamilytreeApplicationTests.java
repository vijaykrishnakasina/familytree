package com.oracle.familytree;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oracle.familytree.controller.FamilyTreeController;
import com.oracle.familytree.controller.RelationController;
import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationDTO;
import com.oracle.familytree.dto.RelationType;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class FamilytreeApplicationTests {

	@Autowired
	RelationController relationController;
	
	@Autowired
	FamilyTreeController familyTreeController;
	
	
	@Test
	void contextLoads() {
		
		// add 106 - wife  as spouse to 105 - person
		relationController.addRelation(RelationDTO.builder().personId(105L).relativeId(106L).relationType(RelationType.SPOUSE).build());
		
		//add 103- child_2 as child to 106 - wife
		relationController.addRelation(RelationDTO.builder().personId(106L).relativeId(103L).relationType(RelationType.CHILD).build());
		
		// now 103- child_2 should be a child to 105- person  also
		assertTrue(familyTreeController.getFamilyTree(105L).get(RelationType.CHILD).stream().anyMatch(child -> child.getId().equals(103L)));
		
		//add 108 - person_father as parent to 105 - person
		relationController.addRelation(RelationDTO.builder().personId(105L).relativeId(108L).relationType(RelationType.PARENT).build());
		
		//now 108 person_father should be added as grandparent to the 103-child_2
		assertTrue(familyTreeController.getFamilyTree(103L).get(RelationType.GRAND_PARENT).stream().anyMatch(gp -> gp.getId().equals(108L)));
		
		//add 104- child1 as child to 106-wife
		relationController.addRelation(RelationDTO.builder().personId(106L).relativeId(104L).relationType(RelationType.CHILD).build());
		
		//to 104- child1, 105 - person will be parent, 103- child2 will be sibling
		
		Map<RelationType, List<Person>> familyTree = familyTreeController.getFamilyTree(104L);
		assertTrue(familyTree.get(RelationType.PARENT).stream().anyMatch(p -> p.getId().equals(105L)));
		assertTrue(familyTreeController.getFamilyTree(103L).get(RelationType.SIBLING).stream().anyMatch(gp -> gp.getId().equals(104L)));
		
		// 111-parent_sibling is added as sibling to the 108-parent
		relationController.addRelation(RelationDTO.builder().personId(111L).relativeId(108L).relationType(RelationType.SIBLING).build());
		// 111-parent_sibling is added as child to the 109- grand parent
		relationController.addRelation(RelationDTO.builder().personId(111L).relativeId(109L).relationType(RelationType.PARENT).build());
		
		// now grand parent- 109 should be added as parent to the parent 108
		relationController.addRelation(RelationDTO.builder().personId(108L).relativeId(109L).relationType(RelationType.PARENT).build());
		
	}
	
	

}
