package com.oracle.familytree.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;

@Service
public class FamilyTreeServiceImpl {
	
	@Autowired
	RelationService relationService;

	public Map<RelationType, List<Person>> getRelatives(Long id) {
		return relationService.getRelatives(id);
	}



}
