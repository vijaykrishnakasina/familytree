package com.oracle.familytree.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationDTO;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.service.RelationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RelationController {

	@Autowired
	RelationService relationService;
	
	@RequestMapping(value = "/relation", method = RequestMethod.POST)
	public Map<RelationType, List<Person>> addRelation(@RequestBody RelationDTO relationDTO){
		log.debug(relationDTO.toString());
		// if person id is not found we are currently returning null. Handle with the corresponding error code 404 
		return relationService.addRelation(relationDTO.getPersonId(), relationDTO.getRelativeId(), relationDTO.getRelationType());
	}
}
