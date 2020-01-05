package com.oracle.familytree.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.service.FamilyTreeServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FamilyTreeController {

	@Autowired
	FamilyTreeServiceImpl familyTreeService;

	
	@RequestMapping(value = "/familyTree/{id}", method = RequestMethod.GET)
	public Map<RelationType, List<Person>> getFamilyTree(@PathVariable(value = "id") Long id) {
		log.info(String.valueOf(id));
		return familyTreeService.getRelatives(id);
	}

}
