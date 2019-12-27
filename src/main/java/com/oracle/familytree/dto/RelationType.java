package com.oracle.familytree.dto;

public enum RelationType {
	
	
	GRAND_PARENT(2),
	PARENT(1),
	SPOUSE(0),
	SIBLING(0),
	CHILD(-1),
	GRAND_CHILD(-2);
	
	
	private int value;

	private RelationType(int val) {
		this.setValue(val);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
