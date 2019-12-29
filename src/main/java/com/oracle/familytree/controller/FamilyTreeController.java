package com.oracle.familytree.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.service.FamilyTreeService;

@RestController(value = "/api")
public class FamilyTreeController {

	@Autowired
	FamilyTreeService familyTreeService;

	@RequestMapping("/hello")
	public String hello() {
		return "ello";
	}

	@RequestMapping("/getFamilyTree")
	public Map<RelationType, List<Person>> getFamilyTree(@RequestParam Long id) {
		return familyTreeService.getFamilyTreeByPersonId(id);
	}

	@RequestMapping("/addRelation")
	public Map<RelationType, List<Person>> addRelation(@RequestParam Long personId1, @RequestParam Long personId2,@RequestParam  RelationType relationTyle){
		System.out.println(personId1 +" "+personId2+" "+relationTyle  );
		
		// if person id is not found we are currently returning null. Handle with the corresponding error code 404 
		return familyTreeService.addRelation(personId1, personId2, relationTyle);
	}
	
	@RequestMapping(value = "/addPerson", method = RequestMethod.POST )
	public Person addPerson(@RequestBody Person person) {
		return familyTreeService.addPerson(person);
	}
	
	@RequestMapping("/getAllPersons")
	public List<Person> addPerson() {
		return familyTreeService.getAllPersons();
	}
}
