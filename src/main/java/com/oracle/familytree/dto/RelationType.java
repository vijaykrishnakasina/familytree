package com.oracle.familytree.dto;

public enum RelationType {
	
	
	GRAND_PARENT(2, "GRAND_CHILD"),
	PARENT(1, "CHILD"),
	SPOUSE(0, "SPOUSE"),
	SIBLING(0, "SIBLING"),
	CHILD(-1, "PARENT"),
	GRAND_CHILD(-2, "GRAND_PARENT");
	
	
	private int value;
	private String inverseRelation;

	private RelationType(int val, String invRelation) {
		this.setValue(val);
		this.setInverseRelation(invRelation);
	}

	private void setInverseRelation(String invRelation) {
		this.inverseRelation = invRelation;
	}
	

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public RelationType getInverseRelation() {
		return valueOf(inverseRelation);
	}
	
}
