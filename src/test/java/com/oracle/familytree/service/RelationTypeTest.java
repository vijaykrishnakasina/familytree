package com.oracle.familytree.service;

import org.junit.Assert;
import org.junit.Test;

import com.oracle.familytree.dto.RelationType;

public class RelationTypeTest {

	@Test
	public void testGetInverseRelation() {
		Assert.assertTrue(RelationType.CHILD.getInverseRelation().equals(RelationType.PARENT));
		Assert.assertTrue(RelationType.PARENT.getInverseRelation().equals(RelationType.CHILD));
		Assert.assertTrue(RelationType.GRAND_CHILD.getInverseRelation().equals(RelationType.GRAND_PARENT));
		Assert.assertTrue(RelationType.SPOUSE.getInverseRelation().equals(RelationType.SPOUSE));
		Assert.assertTrue(RelationType.SIBLING.getInverseRelation().equals(RelationType.SIBLING));
		
		
		Assert.assertFalse(RelationType.CHILD.getInverseRelation().equals(RelationType.CHILD));
		Assert.assertFalse(RelationType.CHILD.getInverseRelation().equals(RelationType.SPOUSE));
		Assert.assertFalse(RelationType.CHILD.getInverseRelation().equals(RelationType.GRAND_PARENT));
		Assert.assertFalse(RelationType.CHILD.getInverseRelation().equals(RelationType.GRAND_CHILD));
		
	}
}
